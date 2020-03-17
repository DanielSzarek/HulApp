from django.urls import path, re_path
from . import views

urlpatterns = [
    path('users/', views.UserListView.as_view(), name="users"),
    path('users/<int:pk>', views.UserDetailView.as_view(), name="users"),
    re_path('cities/(?P<city>.+)/$', views.CityListView.as_view(), name="cities"),
    re_path('cities-details/(?P<city>.+)/$', views.CityDetailsListView.as_view(), name="cities"),
    path('cities/', views.CityPostView.as_view(), name="cities"),
    path('cities/<int:pk>', views.CityDetailView.as_view(), name="cities"),
    re_path('countries/(?P<country>.+)/$', views.CountryListView.as_view(), name="countries"),
    path('countries/', views.CountryPostView.as_view(), name="countries"),
    path('countries/<int:pk>', views.CountryDetailView.as_view(), name="countries"),
    path('provinces/', views.ProvincePostView.as_view(), name="provinces"),
    path('provinces/<int:pk>', views.ProvinceDetailView.as_view(), name="provinces"),
]
