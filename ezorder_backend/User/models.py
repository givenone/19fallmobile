from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

from django.db import models
from django.contrib.auth.models import AbstractUser


class CustomUser(AbstractUser):
    isStore = models.BooleanField(default=True)
    phone = models.CharField(max_length=15, null=False)
    token = models.CharField(max_length=200, null=True)


@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)


class UserProfile(models.Model):
    user = models.OneToOneField(CustomUser, related_name='user_profile', on_delete=models.CASCADE)
    nickname = models.CharField(max_length=20, null=True)


