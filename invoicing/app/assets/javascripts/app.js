'use strict';
var app = angular.module('app', [
    'ngRoute',
    'ngResource',
    'ui.bootstrap',
    'controllers',
    'TaxRateService'
])
    .constant('apiUrl', 'http://localhost:9000/api')
    .config(['$routeProvider', function ($routeProvider) {
        return $routeProvider
            .when("/", {
                templateUrl: '/views/index',
                controller: "AppCtrl"
            })
            .when("/taxes", {
                templateUrl: '/views/taxes',
                controller: "TaxCtrl"
            })
            .when("/invoice", {
                templateUrl: '/views/invoice',
                controller: "InvoiceCtrl"
            })
            .otherwise({
                redirectTo: "/"
            });
    }])
    .config(['$resourceProvider', function ($resourceProvider) {
        // resource provider options
    }])
    .config(["$locationProvider", function ($locationProvider) {
        return $locationProvider.html5Mode(true).hashPrefix("!");
    }]);