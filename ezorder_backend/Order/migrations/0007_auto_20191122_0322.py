# Generated by Django 2.2.6 on 2019-11-22 03:22

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Order', '0006_order_amount'),
    ]

    operations = [
        migrations.RenameField(
            model_name='order',
            old_name='amount',
            new_name='quantity',
        ),
    ]