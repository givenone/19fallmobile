from django.urls import path
import User.views as views


urlpatterns = [
    path('signup/', views.SignUp.as_view()),
    path('', views.UserDetail.as_view()),
    path('login/', views.CustomAuthToken.as_view())
]

