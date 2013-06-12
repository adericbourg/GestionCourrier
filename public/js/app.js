"use strict";

var app = angular.module('gestionCourrier', ['gestionCourrierFilters', 'gestionCourrierServices', 'ui.bootstrap', '$strap.directives']).
    config(function ($routeProvider) {
        $routeProvider.
            when('/', {redirectTo: '/resident/all'}).
            when('/resident/all', {controller: AllResidentsCtrl, templateUrl: '/assets/partials/resident/residents.html'}).
            when('/resident/:residentId', {controller: ViewResidentCtrl, templateUrl: '/assets/partials/resident/viewResident.html'}).
            when('/resident/:residentId/edit', {controller: EditResidentCtrl, templateUrl: '/assets/partials/resident/editResident.html'}).
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


