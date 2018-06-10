from rest_framework import serializers
from modelo.models import *

class EmpleadoSerializer(serializers.ModelSerializer):
	class Meta:
		model = Empleado
		fields = ('__all__')

class UsuarioSerializer(serializers.ModelSerializer):
	class Meta:
		model = Usuario
		fields = ('__all__')

class UsuarioLoginSerializer(serializers.ModelSerializer):
	class Meta:
		model = Usuario
		fields = ('nombreusuario','contraseña')

class SesionSerializer(serializers.ModelSerializer):
	class Meta:
		model = Sesion
		fields = ('__all__')
