from django.db import models
from Store.models import StoreProfile, Menu
from User.models import UserProfile

# Create your models here.

class Order(models.Model):
    request = models.TextField(max_length=100)
    done = models.BooleanField(default=False)
    # TODO: 주문한지 한달 지나면 지워지는 기능... 구현가능 나중에 생각 ㄱ
    created = models.DateTimeField(auto_now_add=True)
    store = models.ForeignKey(StoreProfile, related_name='orders', on_delete=models.CASCADE)
    user = models.ForeignKey(UserProfile, related_name='orders', on_delete=models.CASCADE)
    menu = models.ForeignKey(Menu, on_delete=models.CASCADE)

    class Meta:
        ordering = ['-created']

