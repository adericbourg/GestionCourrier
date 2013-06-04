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
    // Usage: {{aDate | moment:'format'}}
    return function (dateString, format) {
        if (!format) {
            format = "L";
        }
        return moment(dateString).format(format);
    };
});
