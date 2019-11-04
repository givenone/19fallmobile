from rest_framework import permissions
from Store.models import StoreProfile
from User.models import UserProfile


class IsOwner(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        return obj.store == StoreProfile.objects.get(user=request.user.id)\
               or obj.user == UserProfile.objects.get(user=request.user.id)

