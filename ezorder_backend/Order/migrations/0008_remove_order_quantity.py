# Generated by Django 2.2.6 on 2019-11-22 03:48

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Order', '0007_auto_20191122_0322'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='order',
            name='quantity',
        ),
    ]
