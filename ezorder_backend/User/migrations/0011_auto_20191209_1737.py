# Generated by Django 3.0 on 2019-12-09 17:37

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('User', '0010_auto_20191209_1737'),
    ]

    operations = [
        migrations.AlterField(
            model_name='customuser',
            name='picture',
            field=models.ImageField(null=True, upload_to='user/%Y/%m/%d'),
        ),
    ]
