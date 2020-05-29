from django.contrib import admin
from .models import (User, City, Province, Country)

admin.site.register(User)
admin.site.register(City)
admin.site.register(Province)
admin.site.register(Country)
