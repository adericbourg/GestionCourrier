"use strict";

angular.module('gestionCourrier', []).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {controller: ResidentCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            otherwise({redirectTo: '/'})
    });

