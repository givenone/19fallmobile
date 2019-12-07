from django.urls import path
import Order.views as views


urlpatterns = [
    path('', views.OrderList.as_view()),
    path('<int:pk>/', views.OrderDetail.as_view())
]

