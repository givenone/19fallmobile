# Generated by Django 3.0 on 2019-12-09 15:29

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('User', '0008_customuser_picture'),
    ]

    operations = [
        migrations.AlterField(
            model_name='customuser',
            name='picture',
            field=models.ImageField(null=True, upload_to='user/%Y/%m/%d'),
        ),
    ]
