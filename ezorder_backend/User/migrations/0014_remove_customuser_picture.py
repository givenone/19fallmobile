# Generated by Django 3.0 on 2019-12-09 19:02

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('User', '0013_customuser_picture'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='customuser',
            name='picture',
        ),
    ]