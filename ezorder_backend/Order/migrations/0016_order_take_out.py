# Generated by Django 3.0 on 2019-12-09 00:42

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Order', '0015_auto_20191208_1728'),
    ]

    operations = [
        migrations.AddField(
            model_name='order',
            name='take_out',
            field=models.BooleanField(default=False),
        ),
    ]
