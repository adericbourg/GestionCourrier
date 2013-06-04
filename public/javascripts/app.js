"use strict";

var app = angular.module('gestionCourrier', ['ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/residents'}).
            when('/residents/', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/:residentId/', {controller: ViewResidentCtrl, templateUrl: '/assets/partials/resident/viewResident.html'}).
            when('/resident/:residentId/edit/', {controller: EditResidentCtrl, templateUrl: '/assets/partials/resident/editResident.html'}).
            otherwise({redirectTo: '/'})
    });

app.value('$strapConfig', {
    datepicker: {
        language: 'fr'
    }
});

app.filter('moment', function () {
    return function (text, format) {
        format = format || 'L';

        var pad = function (n, width) {
            n = n + '';
            return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
        }

        if (!text) {
            return 'Invalid date';
        }
        var value = text[0] + '-' + pad(text[1], 2) + '-' + pad(text[2], 2);
        return moment(value, 'YYYY-MM-DD').format(format);
    };
});
