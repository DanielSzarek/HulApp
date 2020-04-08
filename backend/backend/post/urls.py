from django.urls import path
from . import views

app_name = 'post'

urlpatterns = [
    path('post/', views.PostView.as_view(), name="posts"),
    path('post/<int:pk>', views.PostDetailView.as_view(), name='post_details')
]