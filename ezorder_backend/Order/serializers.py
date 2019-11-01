from rest_framework import serializers
from .models import Order


# User 입장에서 보는 Order
class UserOrderSerializer(serializers.ModelSerializer):
    store = serializers.ReadOnlyField(source='store.name')
    menu_name = serializers.ReadOnlyField(source='menu.name')
    menu_picture = serializers.ImageField(use_url=True, source='menu.picture', allow_null=True)
    expected_time = serializers.ReadOnlyField(source='menu.expected_time')
    # FIXME: JSONField 좀 못미덥다.
    option = serializers.JSONField()

    class Meta:
        model = Order
        fields = ('id', 'store', 'menu_picture', 'created', 'expected_time', 'option')


# Store 입장에서 보는 Order
class StoreOrderSerializer(serializers.ModelSerializer):
    user_nickname = serializers.ReadOnlyField(source='user.nickname')
    menu_name = serializers.ReadOnlyField(source='menu.name')
    expected_time = serializers.ReadOnlyField(source='menu.expected_time')

    class Meta:
        model = Order
        fields = ('id', 'user_nickname', 'menu_name', 'created', 'expected_time')
