# Generated by Django 3.0 on 2019-12-07 18:38

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('User', '0006_customuser_firebase_id'),
    ]

    operations = [
        migrations.RenameField(
            model_name='customuser',
            old_name='firebase_id',
            new_name='token',
        ),
    ]
