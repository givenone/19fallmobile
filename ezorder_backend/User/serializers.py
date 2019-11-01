from rest_framework import serializers
from .models import (CustomUser, UserProfile)


class UserSerializer(serializers.ModelSerializer):
    username = serializers.ReadOnlyField(source='user.username')
    email = serializers.ReadOnlyField(source='user.email')
    phone = serializers.ReadOnlyField(source='user.phone')

    class Meta:
        model = UserProfile
        fields = ('id', 'username', 'email', 'nickname', 'phone')

