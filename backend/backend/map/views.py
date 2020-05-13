from django.utils import timezone
from rest_framework import generics, status
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

from .permissions import IsOwner, ReadOnly
from .serializers import PointSerializer
from .models import Point


class PointView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = PointSerializer

    def get_queryset(self):
        queryset = Point.objects.all()
        return queryset

    def create(self, request, *args, **kwargs):
        # We give extra context to add automatically user
        serializer = PointSerializer(data=request.data, context={'request': request})
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


class PointDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated, (IsOwner | ReadOnly),)
    serializer_class = PointSerializer

    queryset = Point.objects.all()

    def perform_update(self, serializer):
        # I set new modification date
        serializer.save(mod_date=timezone.now())
