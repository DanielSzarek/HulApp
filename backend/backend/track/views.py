from rest_framework import generics
from rest_framework.exceptions import ValidationError, NotFound
from rest_framework.permissions import IsAuthenticated
from .serializers import TrackSerializer
from .models import Track, User
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


class OtherUserTracksView(generics.ListAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = TrackSerializer

    def get_queryset(self):
        user_id = self.request.query_params.get('id', None)
        if user_id is not None:
            # check if id is given properly -> (int)
            try:
                user_id = int(user_id)
            except ValueError:
                raise ValidationError(detail='Brak lub błędny parametr id.')
            # find given user and his tracks
            try:
                user = User.objects.get(pk=user_id)
            except User.DoesNotExist:
                raise NotFound
            queryset = Track.objects.filter(user=user).order_by('add_date')
            return queryset
        else:
            raise ValidationError(detail='Brak lub błędny parametr id.')


class TrackDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated, (IsOwner | ReadOnly),)
    serializer_class = TrackSerializer
    queryset = Track.objects.all()

    def perform_update(self, serializer):
        serializer.save(mod_date=timezone.now())
