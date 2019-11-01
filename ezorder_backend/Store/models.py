from django.db import models


class StoreProfile(models.Model):
    name = models.TextField(max_length=30)
    user = models.ForeignKey('auth.User', related_name='StoreProfile', on_delete=models.CASCADE)
    information = models.TextField(max_length=200)
    # TODO: location은 나중에 구현하자.


class Menu(models.Model):
    store = models.ForeignKey(StoreProfile, related_name='menus', on_delete=models.CASCADE)
    price = models.IntegerField()
    expected_time = models.DurationField()
    take_out_available = models.BooleanField(default=False)
    option_json = models.CharField(max_length=300)


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
