/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('login', ['ui.bootstrap']);
app.controller('loginController', function ($scope, $http, $window) {
    $scope.iniciarSesion = function () {
        if ($scope.username === '' && $scope.password === '') {
            $window.alert("Algunos campos estan vacios");
        } else {
            $scope.hash = CryptoJS.SHA256($scope.password);
            var dataObj = {
                "nombreusuario": $scope.username,
                "contraseña": $scope.hash.toString()
            };
            $http.post("http://127.0.0.1:9000/login/", dataObj)
                    .then(function (response) {
                        var tipoUsuario = response.data.tipoUsuario;
                        var key = response.data.key;
                        if (tipoUsuario === "ventas") {
                            $window.location = "Articulos.html?" + key;
                        } else if (tipoUsuario === "envios") {
                            $window.location = "realizar_envio.html?" + key;
                        } else {
                            $window.location = "consultar_ventas.html?" + key;
                        }
                    }, function (response) {
                        $window.alert("Lo sentimos, algo salio mal");
                    });
        }
    };
});

