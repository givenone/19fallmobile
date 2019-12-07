from django.db import models
from Store.models import StoreProfile, Menu
from User.models import UserProfile


class Order(models.Model):
    request = models.CharField(max_length=100, blank=True)
    done = models.BooleanField(default=False)
    created = models.DateTimeField(auto_now_add=True)
    store = models.ForeignKey(StoreProfile, on_delete=models.CASCADE)
    user = models.ForeignKey(UserProfile, on_delete=models.CASCADE)
    total_price = models.IntegerField()

    class Meta:
        ordering = ['-created']


class OrderMenu(models.Model):
    order = models.ForeignKey(Order, related_name='menus', on_delete=models.CASCADE)
    menu = models.ForeignKey(Menu, on_delete=models.CASCADE)
    quantity = models.SmallIntegerField(default=1)
    option = models.TextField(max_length=500, null=True)
