# Generated by Django 2.2.7 on 2020-05-12 17:27

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
            name='Point',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('add_date', models.DateTimeField(auto_now_add=True)),
                ('mod_date', models.DateTimeField(auto_now=True)),
                ('longitude', models.FloatField()),
                ('latitude', models.FloatField()),
                ('name', models.CharField(max_length=100)),
                ('description', models.TextField()),
                ('author', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.AddIndex(
            model_name='point',
            index=models.Index(fields=['name', 'description'], name='name_desc_idx'),
        ),
        migrations.AddIndex(
            model_name='point',
            index=models.Index(fields=['longitude', 'latitude'], name='longitude_latitude_idx'),
        ),
    ]
