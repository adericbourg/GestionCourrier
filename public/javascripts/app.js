"use strict";

var app = angular.module('gestionCourrier', ['ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/resident/all'}).
            when('/resident/all', {controller: AllResidentsCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/:residentId', {controller: ViewResidentCtrl, templateUrl: '/assets/partials/resident/viewResident.html'}).
            when('/resident/:residentId/edit', {controller: EditResidentCtrl, templateUrl: '/assets/partials/resident/editResident.html'}).
            when('/inbox', {controller: RegisterMailCtrl, templateUrl: '/assets/partials/inbox/registerMail.html'}).
            otherwise({redirectTo: '/'})
    });

/**
 * Navigation controller.
 * The function identifies if the route comes from the supplied identification group and returns proper CSS class
 * when appropriate.
 */
app.controller('NavigationCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.navClass = function (navigationGroup) {
        var currentRoute = $location.path().substring(1).split('/')[0] || 'resident';
        return navigationGroup === currentRoute ? 'active' : '';
    };
}]);

/**
 * Angular Strap configuration.
 */
app.value('$strapConfig', {
    datepicker: {
        language: 'fr'
    }
});

/**
 * Filter for date provided in format [yyyy,MM,dd] to display it in French format (dd/MM/yyyy).
 */
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

/**
 * Filter for static list values. Static lists are provided by backend with the following structure:
 *     {key: "some key", value: "some value"}
 *
 * The filter will display the value if it exists and an empty string otherwise.
 */
app.filter('staticList', function () {
    return function (listItem) {
        if (!listItem) {
            return '';
        }
        return listItem.value;
    };
});
