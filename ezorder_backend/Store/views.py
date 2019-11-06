from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import StoreSerializer, StoreListSerializer
from .models import (StoreProfile)

from django.http import Http404


class StoreList(APIView):
    permission_classes = (permissions.IsAuthenticated, )

    def get_queryset(self, request):
        queryset = StoreProfile.objects.all()
        return queryset

    def get(self, request, *args, **kwargs):
        queryset = self.get_queryset(request)
        serializer = StoreListSerializer(queryset, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


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