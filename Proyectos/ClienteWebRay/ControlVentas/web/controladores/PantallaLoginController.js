/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('login', ['ui.bootstrap']);
app.controller('loginController', function ($scope, $http, $window) {
    $scope.iniciarSesion = function () {
        if ($scope.username === 'Ventas' && $scope.password === 'pass') {
            var hash = CryptoJS.SHA256($scope.password);
            $window.location = "Articulos.html?" + $scope.username + "?" + hash;
        } else if ($scope.username === 'CEO' && $scope.password === 'pass') {
            var hash = CryptoJS.SHA256($scope.password);
            $window.location = "consultar_ventas.html?" + $scope.username + "?" + hash;
        } else if ($scope.username === 'Envios' && $scope.password === 'pass') {
            $window.location = "realizar_envio.html?" + $scope.username + "?" + hash;
        } else {
            $scope.username = '';
            $scope.password = '';
        }
    };
});

