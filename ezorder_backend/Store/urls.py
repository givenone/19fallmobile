from django.urls import path
import Store.views as views


urlpatterns = [
    path('', views.StoreList.as_view()),
    path('<int:pk>', views.StoreDetail.as_view())
]