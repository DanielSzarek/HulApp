from rest_framework import generics, status
from rest_framework.exceptions import ValidationError
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from .models import Post, Comment
from .serializers import PostSerializer, CommentSerializer, PostWithCommentsSerializer
from .permissions import IsOwner, ReadOnly
from django.utils import timezone


class PostView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = PostSerializer

    def get_queryset(self):
        queryset = Post.objects.filter(published=True).order_by('-add_date')
        return queryset

    def create(self, request, *args, **kwargs):
        # We give extra context to add automatically user
        serializer = PostSerializer(data=request.data, context={'request': request})
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


class PostDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated, (IsOwner | ReadOnly),)
    serializer_class = PostSerializer

    queryset = Post.objects.all()

    def perform_update(self, serializer):
        # I set new modification date
        serializer.save(mod_date=timezone.now())

    def perform_destroy(self, instance):
        # Here I just set published flag on false to archive our post
        instance.published = False
        instance.save()


class CommentView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = CommentSerializer

    def get_queryset(self):
        post_id = self.request.query_params.get('post', None)
        if post_id is not None:
            queryset = Comment.objects.filter(published=True, post=post_id).order_by('-add_date')
        else:
            raise ValidationError(detail='Brak parametru post')
        return queryset

    def create(self, request, *args, **kwargs):
        # We give extra context to add automatically user
        serializer = CommentSerializer(data=request.data, context={'request': request})
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


class CommentDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated, (IsOwner | ReadOnly),)
    serializer_class = CommentSerializer

    queryset = Comment.objects.all()

    def perform_update(self, serializer):
        # I set new modification date
        serializer.save(mod_date=timezone.now())

    def perform_destroy(self, instance):
        # Here I just set published flag on false to archive our post
        instance.published = False
        instance.save()


class PostsWithCommentsView(generics.ListAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = PostWithCommentsSerializer
    queryset = Post.objects.all()
