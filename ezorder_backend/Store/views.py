from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import StoreSerializer, StoreListSerializer, MenuSerializer
from .models import StoreProfile, Menu

from haversine import haversine
import heapq

from django.http import Http404


class StoreList(APIView):
    permission_classes = (permissions.IsAuthenticated, )

    def get_queryset(self, request):
        try:
            if request.GET.get('name') is not None:
                return StoreProfile.objects.filter(name__icontains=request.GET.get('name'))
            elif request.GET.get('latitude') is not None and request.GET.get('longitude') is not None:
                return self.store_search(latitude=request.GET.get('latitude'),
                                         longitude=request.GET.get('longitude'))
            return StoreProfile.objects.all()
        except StoreProfile.DoesNotExist:
            raise Http404

    def get(self, request, *args, **kwargs):
        queryset = self.get_queryset(request)
        serializer = StoreListSerializer(queryset, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def store_search(self, latitude, longitude):
        my_location = (float(latitude), float(longitude))
        stores_nearby = StoreProfile.objects.filter(latitude__lt=latitude + 0.02,
                                                    latitude__gt=latitude - 0.02,
                                                    longitude__lt=longitude + 0.02,
                                                    longitude__gt=longitude - 0.02)
        if not stores_nearby.exists():
            raise Http404

        if stores_nearby.count() <= 50:
            return stores_nearby
        else:
            return heapq.nsmallest(50, stores_nearby,
                                   key=lambda store: haversine((store.latitude, store.longitude), my_location))


class StoreDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, )

    def get_object(self, pk):
        try:
            return StoreProfile.objects.get(pk=pk)
        except StoreProfile.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        store = self.get_object(pk)
        serializer = StoreSerializer(store)
        return Response(serializer.data, status=status.HTTP_200_OK)


# TODO: 여기 아래 모든 view에 대해 permission class에 isOwner추가하기

class MenuList(APIView):
    permission_classes = (permissions.IsAuthenticated, )

    def get_queryset(self, request):
        try:
            queryset = Menu.objects.filter(store=request.user.store_profile)
        except Menu.DoesNotExist:
            raise Http404
        return queryset

    def get(self, request):
        queryset = self.get_queryset(request)
        serializer = MenuSerializer(queryset, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        data = request.data
        data['store'] = request.user.store_profile.id
        print(data)
        serializer = MenuSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response({'message': 'Please check your form'}, status=status.HTTP_400_BAD_REQUEST)


class MenuDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, )

    def get_object(self, pk):
        try:
            return Menu.objects.get(pk=pk)
        except Menu.DoesNotExist:
            raise Http404

    def get(self, request, pk):
        menu = self.get_object(pk)
        serializer = MenuSerializer(menu, partial=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request, pk):
        menu = self.get_object(pk)
        serializer = MenuSerializer(menu, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            return Response({'message': 'Please check your form'}, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk):
        menu = self.get_object(pk)
        menu.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

