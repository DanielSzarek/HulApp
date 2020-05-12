from django.db import models
from ..api.models import User


class Point(models.Model):
    # TODO Think about GeoDjango
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now=True)
    longitude = models.FloatField(null=False)
    latitude = models.FloatField(null=False)
    name = models.CharField(max_length=100)
    description = models.TextField()
    author = models.ForeignKey(User, null=False, on_delete=models.CASCADE)

    class Meta:
        indexes = [
            models.Index(fields=['name', 'description'], name='name_desc_idx'),
            models.Index(fields=['longitude', 'latitude'], name='longitude_latitude_idx')
        ]

    def __str__(self):
        return f"Point: {self.name} - {self.longitude} x {self.latitude}"

