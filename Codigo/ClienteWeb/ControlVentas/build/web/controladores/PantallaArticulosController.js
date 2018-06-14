/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global $modal */

var app = angular.module('AdministrarCatalogo', ['ui.bootstrap']);
app.controller('tablaArticulosController', function ($scope, $http, $window) {
    $scope.q = window.location.search.slice(1);
    var dataObj = {
        "key": $scope.q
    };
    $http.post("http://127.0.0.1:9000/validar/",dataObj)
            .then(function (response) {
                if(!response.data.result || response.data.tipoUsuario !== "ventas"){
                    $window.location = "Login.html";
                }                  
            }, function (response) {
                 $window.location = "Login.html";
            });
    $http.get("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo")
            .then(function (response) {
                $scope.datosArticulos = response.data;
                $scope.articulos = $scope.datosArticulos.reverse();
            }, function (response) {
                alert("Algo salio mal :(");
            });


    $scope.open = function () {
        $scope.nombre = '';
        $scope.descripcion = '';
        $scope.precioUnitario = '';
        $scope.esconder = true;
        $scope.id = -1;
        $scope.showModal = true;
    };

    $scope.openEdit = function (id, nombre, descripcion, precioUnitario, cantidad) {
        $scope.esconder = false;
        $scope.id = id;
        $scope.nombre = nombre;
        $scope.descripcion = descripcion;
        $scope.precioUnitario = precioUnitario;
        $scope.cantidad = cantidad;
        $scope.showModal = true;
    };

    $scope.guardar = function () {
        $scope.showConfirmationModal = false;
        if ($scope.nombre === '' || $scope.descripcion === '' || $scope.precioUnitario === '') {
            $window.alert("Algunos campos estan vacios");
        } else {
            if (Number.isInteger($scope.precioUnitario) && $scope.precioUnitario > -1) {
                if ($scope.id === -1) {
                    var dataObj = {
                        "nombre": $scope.nombre,
                        "descripcion": $scope.descripcion,
                        "precioUnitario": $scope.precioUnitario
                    };
                    $http.post("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo", dataObj).
                            then(function (response) {
                                $scope.nombre = '';
                                $scope.descripcion = '';
                                $scope.precioUnitario = '';
                                $scope.showModal = false;
                                $window.alert("Artículo guardado exitosamente");
                                $window.location.reload();
                            }, function (response) {
                                $window.alert("Lo sentimos, algo salio mal");
                            });
                } else {
                    var dataObj = {
                        "idArticulo": $scope.id,
                        "nombre": $scope.nombre,
                        "descripcion": $scope.descripcion,
                        "precioUnitario": $scope.precioUnitario,
                        "cantidad": $scope.cantidad
                    };
                    $http.put("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo/" + $scope.id, dataObj).
                            then(function (response) {
                                $scope.nombre = '';
                                $scope.descripcion = '';
                                $scope.precioUnitario = '';
                                $scope.cantidad = 0;
                                $scope.showModal = false;
                                $window.alert("Artículo modificado exitosamente");
                                $window.location.reload();
                            }, function (response) {
                                $window.alert("Lo sentimos, algo salio mal");
                            });
                }
            } else {
                $window.alert("Introduzca un monto válido");
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
        $http.delete("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo/" + $scope.id).
                then(function (response) {
                    $scope.showModal = false;
                    $window.alert("Artículo eliminado exitosamente");
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



