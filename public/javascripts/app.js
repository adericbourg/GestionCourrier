"use strict";

angular.module('gestionCourrier', ['$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/residents'}).
            when('/residents/', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/new/', {controller: NewResidentCtrl, templateUrl: '/assets/partials/resident/newResident.html'}).
            otherwise({redirectTo: '/'})
    });
