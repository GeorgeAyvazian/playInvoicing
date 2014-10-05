var app =
    function initializeApp() {
        'use strict';
        //noinspection JSHint,JSUnresolvedVariable,JSLint
        return angular.module('app', ['ngRoute', 'ngResource', 'controllers'])
            .constant('apiUrl', 'http://localhost:9000/api')
            .config(['$routeProvider', function ($routeProvider) {
                return $routeProvider
                    .when("/", {
                        templateUrl: '/views/index',
                        controller: "AppCtrl"
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
    };