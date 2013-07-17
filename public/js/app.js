"use strict";

var app = angular.module('gestionCourrier', ['gestionCourrierFilters', 'gestionCourrierServices', 'gestionCourrierDirectives', 'ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/person/all'}).
            when('/person/all', {controller: AllPersonsCtrl, templateUrl: '/assets/partials/person/persons.html'}).
            when('/person/:personId', {controller: ViewPersonCtrl, templateUrl: '/assets/partials/person/viewPerson.html'}).
            when('/person/:personId/edit', {controller: EditPersonCtrl, templateUrl: '/assets/partials/person/editPerson.html'}).
            when('/residence', {controller: ResidentTabsCtrl, templateUrl: '/assets/partials/residence/residents.html'}).
            when('/inbox', {controller: RegisterMailCtrl, templateUrl: '/assets/partials/inbox/registerMail.html'}).
            when('/outbox', {controller: AllMailsController, templateUrl: '/assets/partials/outbox/mails.html'}).
            otherwise({redirectTo: '/'})
    });

/**
 * Utility functions available in any controller.
 */
app.run(function ($rootScope) {
    /**
     * Handle $http errors and populate message.
     * @param scope Current scope.
     * @param data Response data.
     * @param status Response status.
     * @param headers Response headers.
     * @param config Response configuration.
     */
    $rootScope.handleError = function (scope, data, status, headers, config) {
        if (data) {
            scope.messages.push(data);
        } else {
            scope.messages.push({type: 'error', msg: "Erreur non-gérée : " + status + " (" + config.method + ":" + config.url + ")."});
        }
    }
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


