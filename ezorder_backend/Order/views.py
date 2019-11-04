from rest_framework import (permissions, status)
from rest_framework.views import APIView
from .serializers import (UserOrderSerializer, StoreOrderSerializer)
from .models import Order
from .permissions import IsOwner


class OrderList(APIView):
    permission_classes = (permissions.IsAuthenticated, )


class OrderDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, IsOwner, )


