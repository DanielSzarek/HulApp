from django.urls import path
from . import views

app_name = 'map'

urlpatterns = [
    path('point/', views.PointView.as_view(), name="points"),
    path('point/<int:pk>', views.PointDetailView.as_view(), name='point_details')
]