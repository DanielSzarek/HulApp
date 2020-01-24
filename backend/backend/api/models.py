from django.db import models
from django.contrib.auth.models import AbstractUser
from django.utils.translation import ugettext_lazy as _


class Country(models.Model):
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    name = models.CharField(max_length=75, unique=True)


class Province(models.Model):
    id_country = models.ForeignKey(Country, on_delete=models.PROTECT)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    name = models.CharField(max_length=75, unique=True)


class City(models.Model):
    id_province = models.ForeignKey(Province, on_delete=models.PROTECT)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    name = models.CharField(max_length=75)


class User(AbstractUser):
    email = models.EmailField(_('email address'), unique=True)
    username = models.EmailField(_('email address'), unique=True)
    country = models.ForeignKey(Country, on_delete=models.SET_NULL, null=True)
    city = models.ForeignKey(City, on_delete=models.SET_NULL, null=True)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    profile_img = models.ImageField(upload_to='images')

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', 'first_name', 'last_name']

    def __str__(self):
        return "{}".format(self.email)
