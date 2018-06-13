/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global $modal */

var app = angular.module('AdministrarVendedores', ['ui.bootstrap']);
app.controller('tablaVendedoresController', function ($scope, $http, $window) {
    $scope.q = window.location.search.slice(1);
    var dataObj = {
        "key": $scope.q
    };
    $http.post("http://127.0.0.1:9000/validar/", dataObj)
            .then(function (response) {
                if (!response.data.result || response.data.tipoUsuario !== "ventas") {
                    $window.location = "Login.html";
                }
            }, function (response) {
                $window.location = "Login.html";
            });
    $http.get("http://127.0.0.1:9000/empleado/")
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
        $scope.esconder = false;
        $scope.esconder2 = false;
        $scope.idEmpleado = -1;
        $scope.showModal = true;
    };

    $scope.openEdit = function (idEmpleado, nombres, apellidos, correoElectronico) {
        $scope.esconder = true;
        $scope.esconder2 = true;
        $scope.idEmpleado = idEmpleado;
        $scope.nombres = nombres;
        $scope.apellidos = apellidos;
        $scope.correoElectronico = correoElectronico;
        $scope.nombreUsuario = '-1';
        $scope.contrasena = '-1';
        $scope.showModal = true;
    };

    $scope.guardar = function () {
        $scope.showConfirmationModal = false;
        if ($scope.nombres === '' || $scope.apellidos === '' || $scope.correoElectronico === ''
                || $scope.contrasena === '' || $scope.nombreUsuario === '') {
            $window.alert("Algunos campos estan vacios");
        } else {
            if ($scope.idEmpleado === -1) {
                $scope.hash = CryptoJS.SHA256($scope.contrasena);
                var dataObj = {
                    "nombres": $scope.nombres,
                    "apellidos": $scope.apellidos,
                    "correoelectronico": $scope.correoElectronico,
                    "nombreusuario": $scope.nombreUsuario,
                    "contraseña": $scope.hash.toString()
                };
                $http.post("http://127.0.0.1:9000/empleado/", dataObj).
                        then(function (response) {
                            $scope.nombres = '';
                            $scope.apellidos = '';
                            $scope.correoElectronico = '';
                            $scope.nombreUsuario = '';
                            $scope.contrasena = '';
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
                    "correoelectronico": $scope.correoElectronico
                };
                $http.put("http://127.0.0.1:9000/empleados/" + $scope.idEmpleado + "/", dataObj).
                        then(function (response) {
                            $scope.nombres = '';
                            $scope.apellidos = '';
                            $scope.correoElectronico = '';
                            $scope.nombreUsuario = '';
                            $scope.contrasena = '';
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

    $scope.confirmar = function () {
        $scope.showConfirmationModal = true;
    };

});
