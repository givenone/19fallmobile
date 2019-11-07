from rest_framework import serializers
from .models import (CustomUser, UserProfile)
from Store.models import StoreProfile

from django.contrib.auth.hashers import make_password


class SignUpSerializer(serializers.ModelSerializer):
    # field for user
    nickname = serializers.CharField(required=False)

    # field for store
    name = serializers.CharField(required=False)
    information = serializers.CharField(required=False)

    class Meta:
        model = CustomUser
        fields = ('id', 'username', 'email', 'password', 'isStore', 'phone', 'nickname', 'name', 'information')

    def create(self, validated_data):
        user = CustomUser.objects.create(username=validated_data['username'], email=validated_data['email'],
                                         password=make_password(validated_data['password']),
                                         isStore=validated_data['isStore'], phone=validated_data['phone'])

        if validated_data['isStore']:
            user.store_profile = StoreProfile.objects.create(name=validated_data['name'],
                                                             information=validated_data['information'], user=user)
        elif not validated_data['isStore']:
            user.user_profile = UserProfile.objects.create(nickname=validated_data['nickname'], user=user)
        user.save()
        return user


class UserProfileSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username')
    email = serializers.EmailField(source='user.email')
    isStore = serializers.BooleanField(source='user.isStore')
    phone = serializers.CharField(source='user.phone')

    class Meta:
        model = UserProfile
        fields = ('id', 'username', 'email', 'isStore', 'phone', 'nickname')


