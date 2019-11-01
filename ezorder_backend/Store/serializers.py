from rest_framework import serializers
from .models import (StoreProfile, Menu)


class MenuSerializer(serializers.ModelSerializer):
    # FIXME: 영 못미더움... 나중에 꼭 테스트해보자
    option = serializers.JSONField()

    class Meta:
        model = Menu
        fields = ('id', 'picture', 'price', 'expected_time', 'take_out_available', 'option')


# User입장에서 보는 Store
class StoreSerializer(serializers.ModelSerializer):
    contact = serializers.ReadOnlyField(source='user.phone')
    menus = MenuSerializer(many=True, read_only=True)

    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information', 'contact', 'menus')


# Store 주인장 입장에서 보는 Store
class StoreProfileSerializer(serializers.ModelSerializer):
    email = serializers.ReadOnlyField(source='user.email')
    phone = serializers.ReadOnlyField(source='user.phone')

    class Meta:
        model = StoreProfile
        fields = ('id', 'name', 'information', 'phone', 'email')

