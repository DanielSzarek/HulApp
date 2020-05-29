from rest_framework import serializers
from .models import Post
from ..api.serializers import ApiCurrentUserSerializer


class PostSerializer(serializers.ModelSerializer):
    author = ApiCurrentUserSerializer(read_only=True)

    class Meta:
        model = Post
        fields = (
            'id',
            'author',
            'add_date',
            'mod_date',
            'published',
            'text',
        )

    def create(self, validated_data):
        track = Post.objects.create(author=self.context['request'].user, **validated_data)
        return track
