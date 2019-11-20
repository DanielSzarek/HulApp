from django.contrib import admin
from django.conf.urls import include, url
from django.urls import path
from rest_framework_simplejwt import views as jwt_views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api/', include('backend.api.urls')),
    path('api/token/', jwt_views.TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', jwt_views.TokenRefreshView.as_view(), name='token_refresh'),
]
