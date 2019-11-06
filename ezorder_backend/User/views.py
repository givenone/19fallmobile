from rest_framework import (permissions, status)
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.authentication import TokenAuthentication
from .serializers import UserProfileSerializer, SignUpSerializer
from Store.serializers import StoreProfileSerializer
from .models import CustomUser
from .models import UserProfile
from Store.models import StoreProfile


class SignUp(APIView):
    def post(self, request):
        print(request.data)
        serializer = SignUpSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'message': 'Sign Up Succeed!'}, status=status.HTTP_201_CREATED)
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

