from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.generics import ListAPIView
from .serializers import StoreSerializer
from .models import (StoreProfile, Menu)


class StoreList(ListAPIView):
    permission_classes = (permissions.IsAuthenticated, )
    serializer_class = StoreSerializer



class StoreDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, )