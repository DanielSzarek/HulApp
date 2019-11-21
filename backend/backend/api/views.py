from rest_framework import generics
from rest_framework.response import Response
from rest_framework.views import APIView
from .models import User
from .serializers import ApiUserRegistrationSerializer
from rest_framework.permissions import IsAuthenticated


class UserListView(generics.ListCreateAPIView):
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()


class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()


class HelloView(APIView):
    permission_classes = (IsAuthenticated,)

    def get(self, request):
        content = {'message': 'Hello, World!'}
        return Response(content)
