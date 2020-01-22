from django.db import models
from ..api.models import User


class Track(models.Model):
    user = models.ForeignKey(User, null=False, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    time_start = models.DateTimeField(blank=False, null=False)
    time_finish = models.DateTimeField(blank=False, null=False)
    duration = models.IntegerField(blank=False, null=False)  # TODO Think about duration field!
    track_length = models.FloatField(blank=False, null=False, default=0.0)
