from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.response import Response
import datetime

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
            Order.objects.filter(user=request.user.user_profile, done=True, created__lt=expire_date).delete()
            return Order.objects.filter(user=request.user.user_profile)
        else:
            # TODO: 여기를 잘 건드려야겠구만.
            expire_date = timezone.now() + timezone.timedelta(days=-30)
            Order.objects.filter(store=request.user.store_profile, done=True, created__lt=expire_date).delete()
            orders = Order.objects.filter(store=request.user.store_profile, done=False)
            return sorted(orders, key=lambda order: order.expected_time + order.created)

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
            print(request.data)
            store = Menu.objects.get(id=int(request.data['menus'][0]['id'])).store
            order = Order.objects.create(request=request.data['request'],
                                         store=store,
                                         user=request.user.user_profile,
                                         expected_time=self.get_excepted_time(request, store),
                                         total_price=request.data['total_price'],
                                         take_out=False)

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
        orders = Order.objects.filter(store=store)
        for order in orders:
            if not order.done:
                waiting_orders.append(order)

        menus = [Menu.objects.get(id=int(menu['id'])) for menu in request.data['menus']]
        result = max(menus, key=lambda menu: menu.expected_time).expected_time

        if len(waiting_orders) >= 5:
            result += datetime.timedelta(seconds=(sum([menu.expected_time for menu in menus],
                                                      datetime.timedelta()).total_seconds()/len(menus)))

        return result

    def early(self, lhs, rhs):
        return (lhs.expected_time + lhs.created) < (rhs.expected_time + rhs.created)


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
