from rest_framework import serializers
from .models import Track


class TrackSerializer(serializers.ModelSerializer):
    class Meta:
        model = Track
        exclude = (
            'add_date',
            'mod_date',
            'user'
        )

    def validate(self, attrs):
        if attrs['time_start'] > attrs['time_finish']:
            raise serializers.ValidationError("Koniec musi wystąpić po starcie!")
        return attrs
