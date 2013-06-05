"use strict";

var app = angular.module('gestionCourrier', ['ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/resident/all'}).
            when('/resident/all', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/:residentId', {controller: ViewResidentCtrl, templateUrl: '/assets/partials/resident/viewResident.html'}).
            when('/resident/:residentId/edit', {controller: EditResidentCtrl, templateUrl: '/assets/partials/resident/editResident.html'}).
            otherwise({redirectTo: '/'})
    });

app.controller('NavigationCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.navClass = function (page) {
        var currentRoute = $location.path().substring(1).split('/')[0] || 'resident';
        return page === currentRoute ? 'active' : '';
    };
}]);

app.value('$strapConfig', {
    datepicker: {
        language: 'fr'
    }
});

app.filter('localDate', function () {
    return function (text, format) {
        if (!text) {
            return;
        }

        format = format || 'L';

        var pad = function (n, width) {
            n = n + '';
            return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
        }

        var value = text[0] + '-' + pad(text[1], 2) + '-' + pad(text[2], 2);
        return moment(value, 'YYYY-MM-DD').format(format);
    };
});

app.filter('staticList', function () {
    return function (text) {
        if (!text) {
            return '';
        }
        return text.value;
    };
})
