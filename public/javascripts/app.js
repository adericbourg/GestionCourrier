"use strict";

angular.module('gestionCourrier', ['ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/residents'}).
            when('/residents/', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/new/', {controller: EditResidentCtrl, templateUrl: '/assets/partials/resident/editResident.html'}).
            otherwise({redirectTo: '/'})
    });
