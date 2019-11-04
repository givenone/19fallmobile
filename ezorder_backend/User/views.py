from rest_framework import (permissions, status)
from rest_framework.views import APIView
from .serializers import UserSerializer
from .models import (UserProfile)


class UserDetail(APIView):
    permission_classes = (permissions.IsAuthenticated, )