from django.db import models
from User.models import CustomUser


class StoreProfile(models.Model):
    user = models.OneToOneField(CustomUser, related_name='store_profile', on_delete=models.CASCADE, null=True)
    name = models.CharField(max_length=30)
    information = models.TextField(max_length=200)
    latitude = models.FloatField(null=True)
    longitude = models.FloatField(null=True)


class Menu(models.Model):
    name = models.CharField(max_length=50)
    store = models.ForeignKey(StoreProfile, related_name='menus', on_delete=models.CASCADE)
    picture = models.ImageField(null=True, blank=True)
    price = models.IntegerField()
    expected_time = models.DurationField()
    take_out_available = models.BooleanField(default=False)
    option = models.TextField(max_length=500, blank=True, null=True)


'''
    option_json 예시:
    [
        {
            "text" : "hot or cold?",
            "choice" : ["hot", "cold"]
        },
        {
            "text" : "Add shot",
            "choice" : ["yes", "no"]
        }
    ]
'''
