from django.contrib import admin
from .models import Order, OrderMenu

# Register your models here.
admin.site.register(Order)
admin.site.register(OrderMenu)