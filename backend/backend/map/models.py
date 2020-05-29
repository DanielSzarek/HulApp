from django.db import models
from ..api.models import User


class Point(models.Model):
    # TODO Think about GeoDjango
    author = models.ForeignKey(User, null=False, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now=True)
    longitude = models.FloatField(null=False)
    latitude = models.FloatField(null=False)
    name = models.CharField(max_length=100)
    description = models.TextField()
    rating = models.IntegerField()  # TODO need to be change when we will let every person add points

    class Meta:
        indexes = [
            models.Index(fields=['author', 'name', 'description', 'rating', 'longitude', 'latitude'], name='point_idx'),
        ]

    def __str__(self):
        return f"Point: {self.name} - {self.longitude} x {self.latitude}"

