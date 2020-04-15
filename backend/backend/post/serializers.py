from abc import ABC, ABCMeta

from rest_framework import serializers
from .models import Post, Comment
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
        post = Post.objects.create(author=self.context['request'].user, **validated_data)
        return post


class CommentSerializer(serializers.ModelSerializer):
    author = ApiCurrentUserSerializer(read_only=True)

    class Meta:
        model = Comment
        fields = (
            'id',
            'post',
            'author',
            'add_date',
            'mod_date',
            'published',
            'text',
        )

    def create(self, validated_data):
        comment = Comment.objects.create(author=self.context['request'].user, **validated_data)
        return comment


class PostWithCommentsSerializer(serializers.ModelSerializer):
    comments = serializers.StringRelatedField(many=True)
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
            'comments',
        )
