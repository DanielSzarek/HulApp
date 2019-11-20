from django.urls import path
from . import views

urlpatterns = [
    path('users', views.UserListView.as_view()),
    path('users/<int:pk>', views.UserDetailView.as_view()),
    path('hello/', views.HelloView.as_view(), name='hello'),
]
