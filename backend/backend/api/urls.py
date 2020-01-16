from django.urls import path, re_path
from . import views

urlpatterns = [
    path('users/', views.UserListView.as_view()),
    path('users/<int:pk>', views.UserDetailView.as_view()),
    re_path('cities/(?P<city>.+)/$', views.CityListView.as_view()),
    path('cities/', views.CityPostView.as_view()),
    path('cities/<int:pk>', views.CityDetailView.as_view()),
    re_path('countries/(?P<country>.+)/$', views.CountryListView.as_view()),
    path('countries/', views.CountryPostView.as_view()),
    path('countries/<int:pk>', views.CountryDetailView.as_view()),
    path('provinces/', views.ProvincePostView.as_view()),
    path('provinces/<int:pk>', views.ProvinceDetailView.as_view()),
]
