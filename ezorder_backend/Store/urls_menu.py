from django.urls import path
from .views import MenuDetail, MenuList


urlpatterns = [
    path('menu/', MenuList.as_view()),
    path('menu/<int:pk>/', MenuDetail.as_view())
]