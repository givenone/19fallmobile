from django.urls import path
from .views import MenuDetail, MenuList


urlpatterns = [
    path('', MenuList.as_view()),
    path('<int:pk>/', MenuDetail.as_view())
]