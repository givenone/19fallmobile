from rest_framework import serializers
from .models import (StoreProfile, Menu)


class StoreSerializer(serializers.ModelSerializer):
    menus = serializers.PrimaryKeyRelatedField