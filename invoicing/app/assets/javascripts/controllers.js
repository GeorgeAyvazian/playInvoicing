//noinspection JSHint,JSUnresolvedVariable,JSLint
var controllers = angular.module('controllers', []);

controllers.controller('AppCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
    $scope.searchTerm = '';
    $scope.getPdf = function () {
        window.open('/pdf', '_blank', '');
    };
    $scope.getItem = function search($event, resource, field) {
        return $resource(apiUrl + '/' + resource + '/:term', {term: '@term'})
            .query({fields: field}, {term: $event.currentTarget.innerHTML})
            .$promise
            .then(function (result) {
                return result.map(function (r) {
                    return r.description;
                });
            });
    };
    $scope.isCollapsed = false;
}]);

controllers.controller('TaxCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', 'TaxRateService', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
    $scope.taxRates = [];
    $scope.gridOptions = {
        data: 'taxRates',
        columnDefs: [
            {
                field: 'description',
                displayName: 'Description',
                editableCellTemplate: "<input ng-class=\"'colt' + col.index\" ng-input=\"COL_FIELD\" ng-model=\"COL_FIELD\" ng-change=\"updateTaxRate(row.entity)\"/>"

            },
            {
                field: 'amount',
                displayName: 'Amount',
                editableCellTemplate: "<input type='number' ng-class=\"'colt' + col.index\" ng-input=\"COL_FIELD\" ng-model=\"COL_FIELD\" ng-change=\"updateTaxRate(row.entity)\"/>"
            },
            {
                displayName: 'Remove',
                editableCellTemplate: "<button type='button' class='btn btn-primary' ng-click='window.console.log(row)'>&times;</button>"
            }
        ],
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEditOnFocus: true,
        primaryKey: 'id'
    };
    $resource(apiUrl + '/taxes')
        .query()
        .$promise
        .then(function (result) {
            $scope.taxRates = result;
        });

    $scope.createNewTaxRate = function (isValid) {
        $resource(apiUrl + '/taxes')
            .save($scope.taxRate)
            .$promise
            .then(function (result) {
                $scope.taxRates.push(result);
            });
    };

    $scope.deleteTaxRate = function (taxRateId) {
        $resource(apiUrl + '/taxes/:id')['delete']({id: taxRateId})
            .$promise
            .then(function () {
                $scope.taxRates = $scope.taxRates.filter(function (taxRate) {
                    return taxRate.id !== taxRateId;
                });
            });
    };

    $scope.updateTaxRate = function (taxRate) {
        $resource(apiUrl + '/taxes', {}, {query: {method: 'PUT'}})
            .query(taxRate);
    };
}]);

controllers.controller('InvoiceCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
    $scope.LineItem = function LineItemConstructor() {
    };
    $scope.lineItems = [];
    $scope.taxRates = [];
    $scope.getTaxRates = function () {
        $resource(apiUrl + '/taxes', {}, {
            query: {method: 'GET', isArray: true}
        }).query().$promise.then(function (result) {
                $scope.taxRates = result;
            });
    };
    $scope.getTaxRates();

    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $resource(apiUrl + '/lineitems/create')
        .get()
        .$promise
        .then(function (result) {
            var clone = function clone(obj) {
                var cloneVal = {};
                Object.keys(obj).map(function (key) {
                    cloneVal[key] = 'object' === typeof obj[key] ? clone(obj[key]) : obj[key];
                });
                return cloneVal;
            };
            $scope.LineItem.prototype = clone(result);
            $scope.lineItems.push(result);
        });

    $scope.addLineItem = function () {
        $scope.lineItems.push(new $scope.LineItem());
    };

    $scope.removeLineItem = function (lineItem) {
        1 < $scope.lineItems.length && $scope.lineItems.splice($scope.lineItems.indexOf(lineItem), 1);
    };

    $scope.calculateAmount = function calcAmount(lineItem) {
        var amount = lineItem.quantity * lineItem.product.unitPrice,
            taxAmount = (lineItem.product.tax.amount / 100).toPrecision(2) * amount;
        lineItem.amount = amount + taxAmount;
    };
}]);

controllers.controller('ProductCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', 'TaxRateService', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
    $scope.products = [];
    $scope.gridOptions = {
        data: 'products',
        columnDefs: [
            {
                field: 'description',
                displayName: 'Description'

            },
            {
                field: 'unitPrice',
                displayName: 'Unit Price'
            },
            {
                field: 'tax.description',
                displayName: 'Tax'
            }
        ],
        enableCellSelection: true,
        enableRowSelection: false,
        enableCellEditOnFocus: true,
        primaryKey: 'id'
    };
    $resource(apiUrl + '/products')
        .query()
        .$promise
        .then(function (result) {
            $scope.products = result;
        });
}]);