from rest_framework import generics, status
from rest_framework.exceptions import ValidationError, NotFound
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response

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

    def create(self, request, *args, **kwargs):
        # We give extra context to add automatically user
        serializer = TrackSerializer(data=request.data, context={'request': request})
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


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
