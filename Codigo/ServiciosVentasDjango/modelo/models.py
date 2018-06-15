# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Articulo(models.Model):
    idarticulo = models.AutoField(db_column='idArticulo', primary_key=True)  # Field name made lowercase.
    nombre = models.CharField(max_length=45)
    descripcion = models.CharField(max_length=255)
    preciounitario = models.FloatField(db_column='precioUnitario')  # Field name made lowercase.
    cantidad = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'Articulo'


class Cliente(models.Model):
    idcliente = models.AutoField(db_column='idCliente', primary_key=True)  # Field name made lowercase.
    nombres = models.CharField(max_length=45)
    apellidos = models.CharField(max_length=45)
    correoelectronico = models.CharField(db_column='correoElectronico', max_length=60)  # Field name made lowercase.
    direccion = models.CharField(max_length=255)
    codigopostal = models.CharField(db_column='codigoPostal', max_length=6)  # Field name made lowercase.
    rfc = models.CharField(max_length=13)
    empleado_idempleado = models.ForeignKey('Empleado', models.DO_NOTHING, db_column='Empleado_idEmpleado')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Cliente'


class Empleado(models.Model):
    idempleado = models.AutoField(db_column='idEmpleado', primary_key=True)  # Field name made lowercase.
    nombres = models.CharField(max_length=45)
    apellidos = models.CharField(max_length=45)
    correoelectronico = models.CharField(db_column='correoElectronico', max_length=60)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Empleado'


class Sesion(models.Model):
    tipousuario = models.CharField(db_column='tipoUsuario', max_length=45)  # Field name made lowercase.
    clave = models.CharField(primary_key=True, max_length=45)

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


class Venta(models.Model):
    idventa = models.AutoField(db_column='idVenta', primary_key=True)  # Field name made lowercase.
    fechaventa = models.DateField(db_column='fechaVenta', blank=True, null=True)  # Field name made lowercase.
    montoventa = models.FloatField(db_column='montoVenta', blank=True, null=True)  # Field name made lowercase.
    estado = models.CharField(max_length=45, blank=True, null=True)
    empleado_idempleado = models.ForeignKey(Empleado, models.DO_NOTHING, db_column='Empleado_idEmpleado')  # Field name made lowercase.
    cliente_idcliente = models.ForeignKey(Cliente, models.DO_NOTHING, db_column='Cliente_idCliente')  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Venta'


class VentaHasArticulo(models.Model):
    cantidad = models.IntegerField()
    subtotal = models.FloatField()
    articulo_idarticulo = models.ForeignKey(Articulo, models.DO_NOTHING, db_column='Articulo_idArticulo')  # Field name made lowercase.
    venta_idventa = models.ForeignKey(Venta, models.DO_NOTHING, db_column='Venta_idVenta')  # Field name made lowercase.
    idventahasarticulo = models.AutoField(db_column='idVentaHasArticulo', primary_key=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'Venta_has_Articulo'
