"use strict";

var app = angular.module('gestionCourrier', ['gestionCourrierFilters', 'gestionCourrierServices', 'gestionCourrierDirectives', 'ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/person/all'}).
            when('/person/all', {controller: AllPersonsCtrl, templateUrl: '/assets/partials/person/persons.html'}).
            when('/person/:personId', {controller: ViewPersonCtrl, templateUrl: '/assets/partials/person/viewPerson.html'}).
            when('/person/:personId/edit', {controller: EditPersonCtrl, templateUrl: '/assets/partials/person/editPerson.html'}).
            when('/residence', {controller: AllResidentsCtrl, templateUrl: '/assets/partials/residence/residents.html'}).
            when('/inbox', {controller: RegisterMailCtrl, templateUrl: '/assets/partials/inbox/registerMail.html'}).
            when('/outbox', {controller: AllMailsController, templateUrl: '/assets/partials/outbox/mails.html'}).
            otherwise({redirectTo: '/'})
    });

/**
 * Navigation controller.
 * The function identifies if the route comes from the supplied identification group and returns proper CSS class
 * when appropriate.
 */
app.controller('NavigationCtrl', ['$scope', '$location', function ($scope, $location) {
    $scope.navClass = function (navigationGroup) {
        var currentRoute = $location.path().substring(1).split('/')[0] || 'person';
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


