from rest_framework import generics
from rest_framework.permissions import IsAuthenticated
from .serializers import TrackSerializer
from .models import Track
from .permissions import IsOwner, ReadOnly
from django.utils import timezone


class TrackView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = TrackSerializer

    def get_queryset(self):
        queryset = Track.objects.filter(user=self.request.user).order_by('add_date')
        return queryset

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)


class TrackDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated, (IsOwner | ReadOnly),)
    serializer_class = TrackSerializer
    queryset = Track.objects.all()

    def perform_update(self, serializer):
        serializer.save(mod_date=timezone.now())

