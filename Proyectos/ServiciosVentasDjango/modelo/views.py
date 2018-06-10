from django.shortcuts import render
from modelo.models import *
from modelo.serializers import *
from rest_framework import generics
from django.http import JsonResponse
from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.parsers import JSONParser
import json
import os

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
			sesion = Sesion()
			sesion.nombreusuario = usuario.nombreusuario
			sesion.save()
			return Response(request.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
	return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class UsuarioList(generics.ListCreateAPIView):
    queryset =  Usuario.objects.all()
    serializer_class = UsuarioSerializer

class EmpleadoList(generics.ListCreateAPIView):
	queryset =  Empleado.objects.all()
	serializer_class = EmpleadoSerializer

		