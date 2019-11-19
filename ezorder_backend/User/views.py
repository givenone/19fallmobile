from rest_framework import (permissions, status)
from rest_framework.views import APIView
from rest_framework.authtoken.views import ObtainAuthToken
from rest_framework.authtoken.models import Token
from rest_framework.response import Response

from django.contrib.auth.hashers import make_password

from .serializers import UserProfileSerializer, SignUpSerializer
from Store.serializers import StoreProfileSerializer
from .models import CustomUser


class CustomAuthToken(ObtainAuthToken):
    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data,
                                           context={'request': request})
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data['user']
        token, created = Token.objects.get_or_create(user=user)
        return Response({
            'token': token.key,
            'isStore': user.isStore,
        })


class SignUp(APIView):
    def post(self, request):
        serializer = SignUpSerializer(data=request.data, partial=True)
        if serializer.is_valid():
            user = serializer.save()
            return Response({'isStore': user.isStore, 'token': user.auth_token.key}, status=status.HTTP_201_CREATED)
        else:
            return Response({'message': 'Please check your form'}, status=status.HTTP_400_BAD_REQUEST)


class UserDetail(APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def get(self, request):
        try:
            user = CustomUser.objects.get(pk=request.user.id)
        except CustomUser.DoesNotExist:
            return Response({'message': 'does not found.'}, status=status.HTTP_404_NOT_FOUND)

        if user.isStore:
            serializer = StoreProfileSerializer(user.store_profile)
        else:
            serializer = UserProfileSerializer(user.user_profile)

        return Response(serializer.data, status=status.HTTP_200_OK)

    def put(self, request):
        # data = request.data
        try:
            user = CustomUser.objects.get(pk=request.user.id)
        except CustomUser.DoesNotExist:
            return Response({"message": "does not found."}, status=status.HTTP_404_NOT_FOUND)

        raw_password = request.data.get('password', '')
        request.data['password'] = make_password(raw_password) if raw_password != '' else user.password
        serializer = SignUpSerializer(user, data=request.data, partial=True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        else:
            return Response({'message': 'Please check your form'}, status=status.HTTP_400_BAD_REQUEST)