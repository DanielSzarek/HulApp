from rest_framework import serializers
from .models import Track


class TrackSerializer(serializers.ModelSerializer):
    user = serializers.PrimaryKeyRelatedField(read_only=True)

    class Meta:
        model = Track
        exclude = (
            'add_date',
            'mod_date',
        )

    def create(self, validated_data):
        track = Track.objects.create(user=self.context['request'].user, **validated_data)
        return track

    def validate(self, attrs):
        if attrs['time_start'] > attrs['time_finish']:
            raise serializers.ValidationError(detail="Koniec musi wystąpić po starcie!")
        if attrs['duration'] < 0:
            raise serializers.ValidationError(detail="Długość nie może być krótsza od 0!")
        return attrs
