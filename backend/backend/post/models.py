from django.db import models
from ..api.models import User


class Post(models.Model):
    author = models.ForeignKey(User, null=False, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    published = models.BooleanField(default=True)
    text = models.TextField()

    class Meta:
        verbose_name_plural = "Posts"

    def __str__(self):
        return f"Post: {self.text} written by {self.author}"
