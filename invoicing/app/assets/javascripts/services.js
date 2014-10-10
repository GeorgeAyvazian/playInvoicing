'use strict';

var TaxRateService = angular.module('TaxRateService', ['ngResource']);
TaxRateService.factory('TaxRateService', ['$resource', 'apiUrl',
    function ($resource, apiUrl) {
        return $resource(apiUrl + '/taxes', {}, {
            query: {method: 'GET', isArray: true}
        });
    }]);