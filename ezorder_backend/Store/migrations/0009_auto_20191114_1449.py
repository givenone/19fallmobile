# Generated by Django 2.2.6 on 2019-11-14 14:49

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Store', '0008_auto_20191106_1050'),
    ]

    operations = [
        migrations.AlterField(
            model_name='menu',
            name='option',
            field=models.TextField(blank=True, max_length=500, null=True),
        ),
    ]
