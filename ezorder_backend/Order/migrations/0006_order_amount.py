# Generated by Django 2.2.6 on 2019-11-22 03:18

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Order', '0005_auto_20191118_1817'),
    ]

    operations = [
        migrations.AddField(
            model_name='order',
            name='amount',
            field=models.SmallIntegerField(default=1, max_length=100),
        ),
    ]
