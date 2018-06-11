/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('Inventario', ['ui.bootstrap']);
app.controller('tablaInventarioController', function ($scope, $http, $window) {
    $http.get("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo")
            .then(function (response) {
                $scope.datosArticulos = response.data;
                $scope.articulos = $scope.datosArticulos.reverse();
            }, function (response) {
                alert("Algo salio mal :(");
            });

    $scope.open = function (id, nombre, descripcion, precioUnitario, cantidad) {
        $scope.id = id;
        $scope.nombre = nombre;
        $scope.descripcion = descripcion;
        $scope.precioUnitario = precioUnitario;
        $scope.cantidad = cantidad;
        $scope.showModal = true;
    };

    $scope.cancelarDatos = function () {
        $scope.showConfirmationModal = false;
        $scope.showModal = false;
    };

    $scope.cancelarConfirmacion = function () {
        $scope.showConfirmationModal = false;
    };

    $scope.confirmar = function () {
        $scope.showConfirmationModal = true;
    };

    $scope.guardar = function () {
        $scope.showConfirmationModal = false;
        if ($scope.cantidadActualizada === '') {
            $window.alert("Algunos campos estan vacios");
        } else {
            if (Number.isInteger($scope.cantidadActualizada) && $scope.cantidadActualizada > -1) {
                var dataObj = {
                    "idArticulo": $scope.id,
                    "nombre": $scope.nombre,
                    "descripcion": $scope.descripcion,
                    "precioUnitario": $scope.precioUnitario,
                    "cantidad": $scope.cantidadActualizada
                };
                $http.put("http://localhost:8080/ServiciosVentas/webresources/modelo.articulo/" + $scope.id, dataObj).
                        then(function (response) {
                            $scope.showModal = false;
                            $window.alert("Inventario modificado exitosamente");
                            $window.location.reload();
                        }, function (response) {
                            $window.alert("Lo sentimos, algo salio mal");
                        });
            } else {
                $window.alert("Introduzca una cantidad válida");
            }
        }
    };
});

