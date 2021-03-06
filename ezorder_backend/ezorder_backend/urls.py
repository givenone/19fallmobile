"""ezorder_backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path, re_path
from rest_framework.authtoken import views


urlpatterns = [
    # TODO: url 만들면 나중에 주석 지우기
    path('order/', include('Order.urls')),
    path('user/', include('User.urls')),
    path('menu/', include('Store.urls_menu')),
    path('store/', include('Store.urls_store')),
    path('admin/', admin.site.urls),
]
