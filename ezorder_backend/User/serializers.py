from rest_framework import serializers
from .models import (CustomUser, UserProfile)
from Store.models import StoreProfile

from django.contrib.auth.hashers import make_password


class SignUpSerializer(serializers.ModelSerializer):
    # field for user
    nickname = serializers.CharField()
    # field for store
    name = serializers.CharField()
    information = serializers.CharField()
    latitude = serializers.FloatField()
    longitude = serializers.FloatField()
    class Meta:
        model = CustomUser
        fields = ('id', 'username', 'email', 'password', 'isStore', 'phone', 'nickname',
                  'name', 'information', 'latitude', 'longitude', 'token')

    def create(self, validated_data):
        user = CustomUser.objects.create(username=validated_data['username'], email=validated_data['email'],
                                         password=make_password(validated_data['password']),
                                         isStore=validated_data['isStore'], phone=validated_data['phone'],
                                         token=validated_data['token'])
        if validated_data['isStore']:
            user.store_profile = StoreProfile.objects.create(name=validated_data['name'],
                                                             information=validated_data['information'],
                                                             latitude=validated_data.get('latitude',None),
                                                             longitude=validated_data.get('longitude',None),
                                                             user=user)
        elif not validated_data['isStore']:
            user.user_profile = UserProfile.objects.create(nickname=validated_data['nickname'], user=user)
        user.save()
        return user

    def update(self, instance, validated_data):
        instance.username = validated_data.get('username', instance.username)
        instance.email = validated_data.get('email', instance.email)
        instance.password = validated_data.get('password', instance.password)
        instance.phone = validated_data.get('phone', instance.phone)

        if not instance.isStore:
            instance.user_profile.nickname = validated_data.get('nickname', instance.user_profile.nickname)
            instance.user_profile.save()
        else:
            instance.store_profile.name = validated_data.get('name', instance.store_profile.name)
            instance.store_profile.information = validated_data.get('information', instance.store_profile.name)
            instance.store_profile.latitude = validated_data.get('latitude', instance.store_profile.latitude)
            instance.store_profile.longitude = validated_data.get('longitude', instance.store_profile.longitude)
            instance.store_profile.save()

        instance.save()
        return instance


class UserProfileSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username')
    email = serializers.EmailField(source='user.email')
    isStore = serializers.BooleanField(source='user.isStore')
    phone = serializers.CharField(source='user.phone')

    class Meta:
        model = UserProfile
        fields = ('id', 'username', 'email', 'isStore', 'phone', 'nickname')


