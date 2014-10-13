app.directive('contenteditable', function () {
    return {
        scope: {
            ngModel: '='
        },
        link: function ($scope, elem, attrs) {
            elem.bind('blur', function () {
                $scope.ngModel = attrs.format == 'Number' ? parseInt(elem.html()) : elem.html();
                $scope.$apply();
            });

            elem.bind('keydown', function (event) {
                if (27 === event.which) {
                    event.target.blur();
                    event.preventDefault();
                }
            });
        }
    };
});

app.directive('iautocomplete', ['$resource', 'apiUrl', function ($resource, apiUrl) {
    return {
        restrict: 'A',
        link: function ($scope, elem, attrs) {
            elem.bind('keyup', function (event) {
                var x;
                if (27 === event.which) {
                    event.target.blur();
                    event.preventDefault();
                } else {
                    x = $resource(apiUrl + '/taxes/:id', {}, {query: {method: 'GET', isArray: true, params: {id: '@id'}}});
                    x.query({id: elem.get(0).value}).$promise.then(function (result) {
                        if (window.console) window.console.log(result);
                    });

                }
            });
        }
    };
}]);