from django.shortcuts import render
from modelo.models import *
from modelo.serializers import *
from rest_framework import generics
from django.http import JsonResponse
from rest_framework.response import Response
from django.http import HttpResponse
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser
from django.core.serializers import serialize
from django.core.serializers.json import DjangoJSONEncoder
import json
import random
import string

# Create your views here.
@api_view(['POST'])
def login(request):
	diccionario = {}
	try:
		diccionario = request.data
	except:
		pass
	usuario = Usuario.objects.get(pk = diccionario.get('nombreusuario'))
	if usuario:
		if usuario.contraseña == diccionario.get('contraseña'):			
			serializer = EmpleadoSerializer(usuario.empleado_idempleado)
			sesion = Sesion()			
			sesion.tipousuario = usuario.tipousuario
			key = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(20))
			sesion.clave = key
			sesion.save()
			return JsonResponse({'result': 'true', 'key': key,'tipoUsuario':usuario.tipousuario}, safe=False)
		return Response(request.data, status=status.HTTP_400_BAD_REQUEST)
	return Response(request.data, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET','POST'])
def crearObtenerEmpleado(request):
	if request.method == 'GET':
		empleado = Empleado.objects.all()
		serializer = EmpleadoSerializer(empleado, many=True)
		return Response(serializer.data)
	elif request.method == 'POST':
		diccionario = {}
		try:
			diccionario = request.data
		except:
			pass
		empleado = Empleado()
		empleado.nombres = diccionario.get('nombres')
		empleado.apellidos = diccionario.get('apellidos')
		empleado.correoelectronico = diccionario.get('correoelectronico')
		empleado.save()
		usuario = Usuario()
		usuario.nombreusuario = diccionario.get('nombreusuario')
		usuario.tipousuario = 'vendedor'
		usuario.contraseña = diccionario.get('contraseña')
		usuario.empleado_idempleado = empleado
		usuario.save()
		return Response({'result':'ok'}, status=status.HTTP_201_CREATED)

class EmpleadoDetail(generics.RetrieveUpdateDestroyAPIView):
	queryset =  Empleado.objects.all()
	serializer_class = EmpleadoSerializer




		