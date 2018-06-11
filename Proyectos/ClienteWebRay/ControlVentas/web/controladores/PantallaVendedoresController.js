/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global $modal */

var app = angular.module('AdministrarVendedores', ['ui.bootstrap']);
app.controller('tablaVendedoresController', function ($scope, $http, $window) {
    $http.get("http://127.0.0.1:8000/empleado/?format=json")
            .then(function (response) {
                $scope.datosEmpleados = response.data;
                $scope.empleados = $scope.datosEmpleados.reverse();
            }, function (response) {
                alert("Algo salio mal :(");
            });


    $scope.open = function () {
        $scope.nombres = '';
        $scope.apellidos = '';
        $scope.correoElectronico = '';
        $scope.nombreUsuario = '';
        $scope.contrasena = '';
        $scope.esconder = true;
        $scope.idEmpleado = -1;
        $scope.showModal = true;
    };

    $scope.openEdit = function (idEmpleado, nombres, apellidos, correoElectronico) {
        $scope.esconder = false;
        $scope.idEmpleado = idEmpleado;
        $scope.nombres = nombres;
        $scope.apellidos = apellidos;
        $scope.correoElectronico = correoElectronico;
        $scope.showModal = true;
    };

    $scope.guardar = function () {
        $scope.showConfirmationModal = false;
        if ($scope.nombres === '' || $scope.apellidos === '' || $scope.correoElectronico === ''
                || $scope.contrasena === '' || $scope.nombreUsuario === '') {
            $window.alert("Algunos campos estan vacios");
        } else {
                if ($scope.idEmpleado === -1) {
                    var dataObj = {
                        "idempleado": 700,
                        "nombres": $scope.nombres,
                        "apellidos": $scope.apellidos,
                        "correoelectronico": $scope.correoElectronico
                    };
                    $http.post("http://127.0.0.1:8000/empleado/?format=json", dataObj).
                            then(function (response) {
                                $scope.nombres = '';
                                $scope.apellidos = '';
                                $scope.correoElectronico = '';
                                $scope.showModal = false;
                                $window.alert("Vendedor guardado exitosamente");
                                $window.location.reload();
                            }, function (response) {
                                $window.alert("Lo sentimos, algo salio mal");
                            });
                } else {
                    var dataObj = {
                        "idempleado": $scope.idEmpleado,
                        "nombres": $scope.nombres,
                        "apellidos": $scope.apellidos,
                        "correoelectronico": $scope.correoElectronico,
                    };
                    $http.put("http://127.0.0.1:8000/empleado/?format=json" + $scope.idEmpleado, dataObj).
                            then(function (response) {
                                $scope.nombres = '';
                                $scope.apellidos = '';
                                $scope.correoElectronico = '';
                                $scope.showModal = false;
                                $window.alert("Vendedor modificado exitosamente");
                                $window.location.reload();
                            }, function (response) {
                                $window.alert("Lo sentimos, algo salio mal");
                            });
                }
        }
    };

    $scope.cancelarDatos = function () {
        $scope.showConfirmationModal = false;
        $scope.showEliminacionModal = false;
        $scope.showModal = false;
    };

    $scope.cancelarConfirmacion = function () {
        $scope.showConfirmationModal = false;
    };

    $scope.cancelarEliminacion = function () {
        $scope.showEliminacionModal = false;
    };

    $scope.eliminar = function () {
        $scope.showEliminacionModal = false;
        $http.delete("http://127.0.0.1:8000/empleado/?format=json" + $scope.idEmpleado).
                then(function (response) {
                    $scope.showModal = false;
                    $window.alert("Vendedor eliminado exitosamente");
                    $window.location.reload();
                }, function (response) {
                    $window.alert("Lo sentimos, algo salio mal");
                });
    };

    $scope.confirmar = function () {
        $scope.showConfirmationModal = true;
    };

    $scope.confirmarEliminar = function () {
        $scope.showEliminacionModal = true;
    };

});
