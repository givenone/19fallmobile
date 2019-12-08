from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.response import Response

from  django.utils import timezone

from .serializers import (UserOrderSerializer, StoreOrderSerializer)
from .models import Order, OrderMenu

from Store.models import Menu

from django.http import Http404

from Order.fcm import send_fcm_notification

import json


class OrderList(APIView):
    # TODO: ifUserOrReadOnly permission class 만들기
    permission_classes = (permissions.IsAuthenticated, )

    def get_queryset(self, request):
        if not request.user.isStore:
            expire_date = timezone.now() + timezone.timedelta(days=-30)
            Order.objects.filter(user = request.user.user_profile, done=True, created__lt=expire_date).delete()
            return Order.objects.filter(user=request.user.user_profile)
        else:
            expire_date = timezone.now() + timezone.timedelta(days=-30)
            Order.objects.filter(store=request.user.store_profile, done=True, created__lt=expire_date).delete()
            return Order.objects.filter(store=request.user.store_profile)

    def get(self, request):
        queryset = self.get_queryset(request)
        if not request.user.isStore:
            serializer = UserOrderSerializer(queryset, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            serializer = StoreOrderSerializer(queryset, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        try:
            store = Menu.objects.get(id=int(request.data['menus'][0]['id'])).store
            order = Order.objects.create(request=request.data['request'],
                                         store=store,
                                         user=request.user.user_profile,
                                         expected_time=self.get_excepted_time(request, store),
                                         total_price=request.data['total_price'])

            menus = request.data['menus']
            for menu in menus:
                option = menu.get('option', None)
                OrderMenu.objects.create(order=order,
                                         menu=Menu.objects.get(id=int(menu['id'])),
                                         quantity=menu['quantity'],
                                         option=json.dumps(option))

            return Response(UserOrderSerializer(order).data, status=status.HTTP_201_CREATED)
        except KeyError:
            return Response({'message': 'Please Check request field!'}, status=status.HTTP_400_BAD_REQUEST)

    def get_excepted_time(self, request, store):
        waiting_orders = []
        for order in store.orders:
            if order.done == False:
                waiting_orders.append(order)

        menus = [Menu.objects.get(id=int(menu['id'])) for menu in request.data['menus']]
        result = max(menus, key=lambda menu: menu.expected_time)

        if len(waiting_orders) >= 5:
            result += sum(menu.expected_time for menu in menus)/len(menus)

        return result


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
