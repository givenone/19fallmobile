from rest_framework import serializers
from .models import Order, OrderMenu
from Store.serializers import OptionField


class OrderMenuSerializer(serializers.ModelSerializer):
    menu_name = serializers.ReadOnlyField(source='menu.name')
    option = OptionField()

    class Meta:
        model = OrderMenu
        fields = ('id', 'menu_name', 'quantity', 'option')


# User 입장에서 보는 Order
class UserOrderSerializer(serializers.ModelSerializer):
    store_id = serializers.ReadOnlyField(source='store.id')
    store_name = serializers.ReadOnlyField(source='store.name')
    menus = OrderMenuSerializer(many=True)

    class Meta:
        model = Order
        fields = ('id', 'request', 'done', 'created', 'store_id', 'store_name', 'menus', 'total_price')


# Store 입장에서 보는 Order
class StoreOrderSerializer(serializers.ModelSerializer):
    user_nickname = serializers.ReadOnlyField(source='user.nickname')
    menus = OrderMenuSerializer(many=True)

    class Meta:
        model = Order
        fields = ('id', 'request', 'done', 'created', 'user_nickname', 'menus', 'total_price')


