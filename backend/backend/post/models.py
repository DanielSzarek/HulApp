from django.db import models
from ..api.models import User


class Post(models.Model):
    author = models.ForeignKey(User, null=False, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(null=True, default=None)
    published = models.BooleanField(default=True)
    text = models.TextField()

    class Meta:
        verbose_name_plural = "Posts"
        indexes = [
            models.Index(fields=['author', 'published', 'text'], name='post_idx')
        ]

    def __str__(self):
        return f"Post: {self.text} written by {self.author}"
