# Generated by Django 3.0 on 2019-12-09 17:42

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('User', '0011_auto_20191209_1737'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='customuser',
            name='picture',
        ),
    ]