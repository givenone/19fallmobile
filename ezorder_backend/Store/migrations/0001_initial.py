# Generated by Django 2.2.6 on 2019-11-01 18:30

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='StoreProfile',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.TextField(max_length=30)),
                ('information', models.TextField(max_length=200)),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='StoreProfile', to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='Menu',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.TextField(max_length=20)),
                ('picture', models.ImageField(null=True, upload_to='')),
                ('price', models.IntegerField()),
                ('expected_time', models.DurationField()),
                ('take_out_available', models.BooleanField(default=False)),
                ('option', models.CharField(max_length=300)),
                ('store', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='menus', to='Store.StoreProfile')),
            ],
        ),
    ]
