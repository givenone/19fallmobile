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

    class Meta:
        model = Menu
        fields = ('id', 'picture', 'price', 'expected_time', 'take_out_available', 'option')


class StoreListSerializer(serializers.ModelSerializer):
    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information')


# User 입장에서 보는 Store
class StoreSerializer(serializers.ModelSerializer):
    contact = serializers.ReadOnlyField(source='user.phone')
    menus = MenuSerializer(many=True, read_only=True, allow_null=True)

    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information', 'contact', 'menus')


# Store 주인장 입장에서 보는 Store
class StoreProfileSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username')
    email = serializers.EmailField(source='user.email')
    isStore = serializers.BooleanField(source='user.isStore')
    phone = serializers.CharField(source='user.phone')
    menus = MenuSerializer(many=True)

    class Meta:
        model = StoreProfile
        fields = ('id', 'username', 'email', 'isStore', 'phone', 'name', 'information', 'menus')