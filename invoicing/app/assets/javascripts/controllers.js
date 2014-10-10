//noinspection JSHint,JSUnresolvedVariable,JSLint
var controllers = angular.module('controllers', []);

controllers.controller('AppCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
}]);

controllers.controller('TaxCtrl', ['$scope', '$resource', '$routeParams', '$http', 'apiUrl', 'TaxRateService', function ($scope, $resource, $routeParams, $http, apiUrl) {
    'use strict';
    $scope.taxRates = [];
    $resource(apiUrl + '/taxes', {}, {
        query: {method: 'GET', isArray: true}
    }).query().$promise.then(function (result) {
            $scope.taxRates = result;
        });

    $scope.createNewTaxRate = function (isValid) {
        var $resource2 = $resource(apiUrl + '/taxes', {}, {query: {method: 'POST'}});
        $resource2.save($scope.taxRate).$promise.then(function (result) {
            $scope.taxRates.push(result);
        });
    };

    $scope.deleteTaxRate = function (taxRateId) {
        var $resource2 = $resource(apiUrl + '/taxes/:id', {}, {query: {method: 'DELETE', params: {id: '@id'}}});
        $resource2['delete']({id: taxRateId}).$promise.then(function (result) {
            $scope.taxRates = $scope.taxRates.filter(function (taxRate) {
                return parseInt(taxRate.id) !== parseInt(result.id);
            });
        });
    };

    $scope.updateTaxRate = function (taxRate) {
        taxRate.amount = parseInt(taxRate.amount);
        var $resource2 = $resource(apiUrl + '/taxes', {}, {query: {method: 'PUT'}});
        $resource2.query(taxRate);
    };
}]);
