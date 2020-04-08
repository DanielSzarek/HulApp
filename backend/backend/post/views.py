from rest_framework import generics, status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from .models import Post
from .serializers import PostSerializer
from .permissions import IsOwner, ReadOnly
from django.utils import timezone


class PostView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = PostSerializer

    def get_queryset(self):
        queryset = Post.objects.filter(published=True).order_by('add_date')
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
