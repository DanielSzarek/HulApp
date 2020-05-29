from rest_framework.test import APITestCase
from rest_framework import status
from rest_framework.test import APIClient
from django.utils import timezone
from .models import User, Track


class TrackTestCase(APITestCase):
    def setUp(self):
        self.user = User.objects.create_user("test", "test@gmail.com", "P@$$w0rd", is_active=True)
        data = {"email": "test@gmail.com", "password": "P@$$w0rd"}
        self.token_jwt = self.get_token(data)
        self.client = APIClient()

    def get_token(self, data):
        # Get Access JWT Bearer
        response = self.client.post('/auth/jwt/create/', data)
        self.assertEquals(response.status_code, status.HTTP_200_OK)
        return response.data.get("access")

    def test_should_add_track(self, token=None):
        if token is None:
            token = self.token_jwt
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        data = {'time_start': timezone.now(), 'time_finish': timezone.now(), 'duration': 100, 'track_length': 1.43}
        response = self.client.post("/api/my-tracks/", data)
        self.assertEquals(response.status_code, status.HTTP_201_CREATED)

    def test_should_not_add_track(self):
        first_time = timezone.now()
        bad_finish_time = first_time - timezone.timedelta(days=100)
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + self.token_jwt)
        data = {'time_start': first_time, 'time_finish': bad_finish_time, 'duration': 100, 'track_length': 1.43}
        response = self.client.post("/api/my-tracks/", data)
        self.assertEquals(response.status_code, status.HTTP_400_BAD_REQUEST)
        # self.assertContains(response.data.get('non_field_errors')[0][0], "Koniec musi wystąpić po starcie!")

    def test_should_get_my_tracks(self):
        # Add track
        self.test_should_add_track(self.token_jwt)
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + self.token_jwt)
        response = self.client.get("/api/my-tracks/")
        self.assertEquals(response.status_code, status.HTTP_200_OK)
        self.assertEquals(len(response.data), 1)

    def test_should_return_details(self):
        self.test_should_add_track(self.token_jwt)
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + self.token_jwt)
        response = self.client.get("/api/track/1")
        self.assertEquals(response.status_code, status.HTTP_200_OK)
        self.assertEquals(response.data.get('user'), Track.objects.get(pk=1).user_id)

    def test_should_not_return_details(self):
        self.test_should_add_track(self.token_jwt)
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + self.token_jwt)
        response = self.client.get("/api/track/2")
        self.assertEquals(response.status_code, status.HTTP_404_NOT_FOUND)

    def test_should_get_other_user_track(self):
        # Create second user and add him a track
        self.user_2 = User.objects.create_user("test2", "test2@gmail.com", "P@$$w0rd2", is_active=True)
        data = {"email": "test2@gmail.com", "password": "P@$$w0rd2"}
        token_jwt_2 = self.get_token(data)
        self.test_should_add_track(token_jwt_2)

        # Find a user_2 tracks as main user
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + self.token_jwt)
        response = self.client.get("/api/tracks/?id=2")
        self.assertEquals(response.status_code, status.HTTP_200_OK)
