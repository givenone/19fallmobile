from rest_framework import serializers
from .models import Order
from Store.serializers import OptionField

# TODO: serializer order조회, order수정(오직 done만 가능)


# User 입장에서 보는 Order
class UserOrderSerializer(serializers.ModelSerializer):
    store_name = serializers.ReadOnlyField(source='store.name')
    menu_name = serializers.ReadOnlyField(source='menu.name')
    menu_picture = serializers.ImageField(use_url=True, source='menu.picture', allow_null=True)
    expected_time = serializers.ReadOnlyField(source='menu.expected_time')
    # FIXME: JSONField 좀 못미덥다.
    option = OptionField()

    class Meta:
        model = Order
        fields = ('id', 'done', 'created', 'expected_time', 'store_name', 'menu_name',
                  'menu_picture', 'request', 'option')


# Store 입장에서 보는 Order
class StoreOrderSerializer(serializers.ModelSerializer):
    user_nickname = serializers.ReadOnlyField(source='user.nickname')
    menu_name = serializers.ReadOnlyField(source='menu.name')
    expected_time = serializers.ReadOnlyField(source='menu.expected_time')
    option = OptionField()

    class Meta:
        model = Order
        fields = ('id', 'done', 'created', 'expected_time', 'user_nickname', 'menu_name', 'request', 'option')