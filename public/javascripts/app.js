"use strict";

angular.module('gestionCourrier', []).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/residents'}).
            when('/residents', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            otherwise({redirectTo: '/'})
    });
