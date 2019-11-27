from rest_framework import serializers
from .models import User, Province, Country, City
from djoser.serializers import UserCreateSerializer


class ProvinceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Province
        fields = ['id', 'name']


class CountrySerializer(serializers.ModelSerializer):
    id_province = serializers.PrimaryKeyRelatedField(read_only=True)

    class Meta:
        model = Country
        fields = ['id', 'id_province', 'name']


class CitySerializer(serializers.ModelSerializer):
    id_country = serializers.PrimaryKeyRelatedField(read_only=True)

    class Meta:
        model = City
        fields = ['id', 'id_country', 'name']


class ApiUserRegistrationSerializer(UserCreateSerializer):
    id_country = serializers.PrimaryKeyRelatedField(read_only=True)
    id_city = serializers.PrimaryKeyRelatedField(read_only=True)

    class Meta(UserCreateSerializer.Meta):
        model = User
        fields = ['id', 'id_country', 'id_city' 'username', 'email', 'first_name', 'last_name', 'password']


# class UserSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = User
#         fields = ['id', 'email', 'first_name', 'last_name', 'password', 'country', 'city']
#         extra_kwargs = {'password': {'write_only': True}}
#
#     def create(self, validated_data):
#         password = validated_data.pop('password')
#         user = User(**validated_data)
#         user.set_password(password)
#         user.save()
#         return user
#
#     def update(self, instance, validated_data):
#         instance.email = validated_data.get('email', instance.email)
#         instance.save()
