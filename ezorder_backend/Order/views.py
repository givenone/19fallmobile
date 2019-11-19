from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.response import Response

from  django.utils import timezone

from .serializers import (UserOrderSerializer, StoreOrderSerializer)
from .models import Order
from .permissions import IsOwner

from Store.models import Menu

from django.http import Http404

import json


class OrderList(APIView):
    # TODO: ifUserOrReadOnly permission class 만들기
    permission_classes = (permissions.IsAuthenticated, )

    def get_queryset(self, request):
        if not request.user.isStore:
            expire_date = timezone.now() + timezone.timedelta(days=-30)
            Order.objects.filter(user = request.user.user_profile, done=True, created__lt=expire_date).delete()
            return Order.objects.filter(user = request.user.user_profile)
        else:
            return Order.objects.filter(store = request.user.store_profile)

    def get(self, request):
        queryset = self.get_queryset()
        if not request.user.isStore:
            serializer = UserOrderSerializer(queryset, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            serializer = StoreOrderSerializer(queryset, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        data = None
        data['request'] = request.data['request']
        menu = request.data['menu']
        store = Menu.objects.get(pk=menu).store
        option = json.dumps(request.data['option'])
        newOrder = Order.objects.create(request=request.data['request'],
                             done=False,
                             store=store,
                             user=request.user,
                             menu=menu,
                             option=option)

        return Response(UserOrderSerializer(newOrder).data, status=status.HTTP_201_CREATED)


class OrderDetail(APIView):
    # TODO: isOwner, ifStoreOrReadOnly permission class 만들기
    permission_classes = (permissions.IsAuthenticated, )

    def get_object(self, pk):
        try:
            return Order.objects.get(pk=pk)
        except Order.DoesNotExist:
            raise Http404

    def get(self, request, pk):
        order = self.get_object(pk)

        if not request.user.isStore:
            serializer = UserOrderSerializer(order)
        else:
            serializer = StoreOrderSerializer(order)

        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, pk):
        order = self.get_object(pk)
        order.done = True
        order.save()

        return Response(data=StoreOrderSerializer(order).data, status=status.HTTP_200_OK)
