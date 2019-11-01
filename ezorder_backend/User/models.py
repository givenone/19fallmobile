from django.db import models
from django.contrib.auth.models import AbstractUser


class CustomUser(AbstractUser):
    isStore = models.BooleanField(default=True)
    phone = models.TextField(max_length=True, null=False)


class UserProfile(models.Model):
    user = models.ForeignKey(CustomUser, related_name='UserProfile', on_delete=models.CASCADE)
    nickname = models.TextField(max_length=20, null=True)