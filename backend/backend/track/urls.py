from django.urls import path
from . import views

urlpatterns = [
    path('tracks/', views.TrackView.as_view(), name="tracks"),
    path('tracks/<int:pk>', views.TrackDetailView().as_view(), name="tracks"),
]
