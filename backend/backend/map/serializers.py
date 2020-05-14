from rest_framework import serializers
from .models import Point
from ..api.serializers import ApiCurrentUserSerializer


class PointSerializer(serializers.ModelSerializer):
    author = ApiCurrentUserSerializer(read_only=True)

    class Meta:
        model = Point
        fields = (
            'id',
            'author',
            'name',
            'description',
            'rating',
            'latitude',
            'longitude',
        )

    def create(self, validated_data):
        track = Point.objects.create(author=self.context['request'].user, **validated_data)
        return track
