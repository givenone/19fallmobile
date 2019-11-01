from rest_framework import serializers
from .models import Order


class OrderSerializer(serializers.ModelSerializer):
    store = serializers.ReadOnlyField(source='store.name')
    user = serializers.ReadOnlyField(source='user.nickname')
    menu_name = serializers.ReadOnlyField(source='menu.name')
    menu_picture = serializers.ImageField(use_url=True, source='menu.picture',allow_null=True)

    class Meta:
        model = Order
        fields = ('id', 'menu_name', 'menu_picture', 'user', 'store')

    def create(self, validated_data):
        return Order.objects.create(**validated_data)