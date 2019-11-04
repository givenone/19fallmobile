from rest_framework import (permissions, status)
from rest_framework.views import APIView
from .serializers import StoreSerializer
from .models import (StoreProfile, Menu)


class StoreList(APIView):
    permission_classes = (permissions.IsAuthenticated, )


class StoreDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, )