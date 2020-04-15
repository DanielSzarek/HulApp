from django.urls import path
from . import views

app_name = 'post'

urlpatterns = [
    path('post/', views.PostView.as_view(), name="posts"),
    path('post/<int:pk>', views.PostDetailView.as_view(), name='post_details'),
    path('comment/', views.CommentView.as_view(), name="comments"),
    path('comment/<int:pk>', views.CommentDetailView.as_view(), name='comment_details'),
    path('posts-with-comments/', views.PostsWithCommentsView.as_view(), name='posts_with_comments')
]
