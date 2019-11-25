from rest_framework import serializers
from .models import (StoreProfile, Menu)
import json


# parses input json option to string, and reverse.
class OptionField(serializers.Field):
    def get_attribute(self, instance):
        return instance.option

    def to_representation(self, value):
        return json.loads(value)

    def to_internal_value(self, data):
        return json.dumps(data)


class MenuSerializer(serializers.ModelSerializer):
    # FIXME: 영 못미더움... 나중에 꼭 테스트해보자
    option = OptionField()
    picture = serializers.ImageField(required=False)

    class Meta:
        model = Menu
        fields = ('id', 'store', 'picture', 'price', 'expected_time', 'take_out_available', 'option')

    def create(self, validated_data):
        return Menu.objects.create(picture=validated_data.get('picture', None),
                                   store=validated_data['store'],
                                   price=validated_data['price'],
                                   expected_time=validated_data['expected_time'],
                                   take_out_available=validated_data['take_out_available'],
                                   option=validated_data.get('option', None))

    def update(self, instance, validated_data):
        instance.picture = validated_data.get('picture', instance.picture)
        instance.price = validated_data.get('price', instance.price)
        instance.expected_time = validated_data.get('expected_time', instance.expected_time)
        instance.take_out_available = validated_data.get('take_out_available', instance.take_out_available)
        instance.option = validated_data.get('option', instance.option)
        return instance.save()


class StoreListSerializer(serializers.ModelSerializer):
    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information', 'latitude', 'longitude')


# User 입장에서 보는 Store
class StoreSerializer(serializers.ModelSerializer):
    contact = serializers.ReadOnlyField(source='user.phone')
    menus = MenuSerializer(many=True, read_only=True, allow_null=True)

    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information', 'contact', 'menus', 'latitude', 'longitude')


# Store 주인장 입장에서 보는 Store
class StoreProfileSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username')
    email = serializers.EmailField(source='user.email')
    isStore = serializers.BooleanField(source='user.isStore')
    phone = serializers.CharField(source='user.phone')
    menus = MenuSerializer(many=True)

    class Meta:
        model = StoreProfile
        fields = ('id', 'username', 'email', 'isStore', 'phone', 'name', 'information', 'menus', 'latitude', 'longitude')