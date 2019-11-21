from rest_framework import generics
from .models import User
from .serializers import ApiUserRegistrationSerializer
from rest_framework.permissions import IsAuthenticated


class UserListView(generics.ListCreateAPIView):
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()


class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()

