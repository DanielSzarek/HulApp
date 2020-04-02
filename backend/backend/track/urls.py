from django.urls import path
from . import views

app_name = 'track'

urlpatterns = [
    path('my-tracks/', views.TrackView.as_view(), name="tracks"),
    path('track/<int:pk>', views.TrackDetailView().as_view(), name="tracks"),
    path('tracks/', views.OtherUserTracksView.as_view(), name="tracks"),
]
