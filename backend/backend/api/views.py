from rest_framework import generics, status
from rest_framework.response import Response
from rest_framework.views import APIView
from .models import User, Country, City, Province
from .serializers import ApiUserRegistrationSerializer, CountrySerializer, CitySerializer, ProvinceSerializer
from rest_framework.permissions import IsAuthenticated, IsAdminUser


class UserListView(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()


class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = ApiUserRegistrationSerializer
    queryset = User.objects.all()


class CountryListView(generics.ListAPIView):
    serializer_class = CountrySerializer

    def get_queryset(self):
        country = self.kwargs['country']
        country = country[0].upper() + country[1:]
        return Country.objects.filter(name__contains=country)


class CountryDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = CountrySerializer
    queryset = Country.objects.all()


class ProvinceListView(generics.ListAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = ProvinceSerializer
    queryset = Province.objects.all()


class ProvinceDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = ProvinceSerializer
    queryset = Province.objects.all()


class CityListView(generics.ListAPIView):
    serializer_class = CitySerializer

    def get_queryset(self):
        city = self.kwargs['city']
        city = city[0].upper() + city[1:]
        return City.objects.filter(name__contains=city)


class CityPostView(generics.CreateAPIView):
    permission_classes = (IsAdminUser,)
    serializer_class = CitySerializer

    def create(self, request, *args, **kwargs):
        # I created special JSON structure using free database of some polish cities
        serializer = self.get_serializer(data=request.data['cities'], many=True)
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


class CityDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = (IsAuthenticated,)
    serializer_class = CitySerializer
    queryset = City.objects.all()


class HelloView(APIView):
    permission_classes = (IsAuthenticated,)

    def get(self, request):
        content = {'message': 'Hello, World!'}
        return Response(content)
