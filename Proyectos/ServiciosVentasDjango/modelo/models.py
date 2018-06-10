# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Empleado(models.Model):
    idempleado = models.IntegerField(db_column='idEmpleado', primary_key=True)  # Field name made lowercase.
    nombres = models.CharField(max_length=45)
    apellidos = models.CharField(max_length=45)
    correoelectronico = models.CharField(db_column='correoElectronico', max_length=60)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Empleado'


class Sesion(models.Model):
    nombreusuario = models.CharField(db_column='nombreUsuario', primary_key=True, max_length=45)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Sesion'


class Usuario(models.Model):
    nombreusuario = models.CharField(db_column='nombreUsuario', primary_key=True, max_length=45)  # Field name made lowercase.
    tipousuario = models.CharField(db_column='tipoUsuario', max_length=45)  # Field name made lowercase.
    contraseña = models.CharField(max_length=256)
    empleado_idempleado = models.ForeignKey(Empleado, models.DO_NOTHING, db_column='Empleado_idEmpleado')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Usuario'
