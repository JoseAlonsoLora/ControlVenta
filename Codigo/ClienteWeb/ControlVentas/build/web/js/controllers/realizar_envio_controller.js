
//Variables globales del controlador
var f_inicio = new Date(new Date().getFullYear(), 0, 1);
var f_fin = new Date(new Date().getFullYear(), 11, 31);
var f_estado = "*";
var host = 'http://localhost:8080';

//Controlador de combobox
mod_realizar_envio.controller("filtro_estado", function ($scope) {
    $scope.value = "*";

    $scope.filtros = {
        "Todas las ventas": "*",
        "Pendientes de envío": "pendiente",
        "Enviados": "enviado",
        "Recibidos": "recibido",
    };

    $scope.select = function () {
        f_estado = $scope.value;
    }
});

mod_realizar_envio.controller("boton_inventario", function ($scope, $window,$http) {
    $scope.q = window.location.search.slice(1);
    var dataObj = {
        "key": $scope.q
    };
    $http.post("http://127.0.0.1:9000/validar/", dataObj)
            .then(function (response) {
                if (!response.data.result || response.data.tipoUsuario !== "envios") {
                    $window.location = "Login.html";
                }
            }, function (response) {
                $window.location = "Login.html";
            });
    $scope.inventario = function () {
        $window.location = "Inventario.html?"+$scope.q;
    };
});

//Controlador de tabla
mod_realizar_envio.controller("tabla_ventas", function ($scope, $http) {
    var arr;
    var data;
    $http.get(host + '/ServiciosVentas/webresources/modelo.venta').then(function (response) {
        data = response.data;
        $scope.recargar();

    });

    $scope.recargar = function () {
        arr = [];
        data.forEach(function (value) {
            var fechaValor = new Date(value.fechaVenta);
            if (fechaValor >= f_inicio && fechaValor <= f_fin && (f_estado == "*" || value.estado == f_estado)) {
                arr.push(value);
            }
        });
        $scope.res = arr;
    };
});
//Controlador de botón consulta
mod_realizar_envio.controller('datos_de_venta', function ($uibModal, $log, $document) {
    var $con = this;
    $con.mostrar_datos = function (x, size, parentSelector) {
        var parentElem = parentSelector ?
                angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        var modalInstance = $uibModal.open({
            animation: $con.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'datos_venta.html',
            controller: 'controlador_modal',
            controllerAs: '$ctrl',
            size: size,
            appendTo: parentElem,
            resolve: {
                venta: function () {
                    return x;
                }
            }
        });

        modalInstance.result.then(function () {

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});
//Controlador de cuadro con la consulta
mod_realizar_envio.controller('controlador_modal', function ($http, $uibModalInstance, venta) {
    var $ctrl = this;
    $ctrl.venta = venta;
    $http.get(host + '/ServiciosVentas/webresources/modelo.ventahasarticulo').then(function (response) {
        $ctrl.res = response.data;
        $ctrl.res = response.data.filter(function (obj) {
            return obj.venta.idVenta == $ctrl.venta.idVenta;
        });
    });

    $ctrl.enviado = $ctrl.venta.estado !== 'pendiente';
    console.log($ctrl.enviado);

    $ctrl.enviar = function () {
        $ctrl.venta.estado = 'enviado';
        $http.put(host + '/ServiciosVentas/webresources/modelo.venta/' + $ctrl.venta.idVenta,
                $ctrl.venta).then(function (response) {
            console.log("Success");
        });
        console.log("Enviado");
    }

    $ctrl.ok = function () {
        $uibModalInstance.close();
    };

    $ctrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});


//Controladores de calendarios
mod_realizar_envio.controller('fecha_inicio', function ($scope) {
    $scope.select = function () {
        f_inicio = $scope.dt;
    }

    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.dt = f_inicio;

    $scope.clear = function () {
        $scope.dt = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: false
    };

    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        //maxDate: new Date(2020, 5, 22),
        //minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    //function disabled(data) {
    // var date = data.date,
    //  mode = data.mode;
    // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    // }

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date,
                mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});
mod_realizar_envio.controller('fecha_fin', function ($scope) {
    $scope.select = function () {
        f_fin = $scope.dt;
    }

    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.dt = f_fin;

    $scope.clear = function () {
        $scope.dt = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: false
    };

    $scope.dateOptions = {
        //dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    //function disabled(data) {
    // var date = data.date,
    //  mode = data.mode;
    // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    // }

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date,
                mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});