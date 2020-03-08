from django.urls import path
from . import views

urlpatterns = [
    path('my-tracks/', views.TrackView.as_view(), name="tracks"),
    path('track/<int:pk>', views.TrackDetailView().as_view(), name="tracks"),
    path('tracks/', views.OtherUserTracksView.as_view(), name="tracks"),
]
