'use strict';

function AllResidentsCtrl($scope, residenceService) {

    $scope.allResidents = residenceService.allResidents();

    $scope.tableSortPredicate = "lastName";
    $scope.tableSortReverse = false;

    $scope.setSortCriterion = function (sortCriterion) {
        if (sortCriterion == $scope.tableSortPredicate) {
            $scope.tableSortReverse = !$scope.tableSortReverse;
        } else {
            $scope.tableSortPredicate = sortCriterion;
            $scope.tableSortReverse = false;
        }
    };
}

function NewResidenceCtrl($scope, dialog, personId, personService, referenceListService) {

    $scope.personId = personId;
    $scope.residence = {};

    $scope.messages = [];
    $scope.residenceTypes = [];

    $scope.createResidence = function () {
        $scope.messages = [];
        personService.createResidence(personId, $scope.residence).
            success(function () {
                dialog.close({type: 'success', msg: "Domiciliation ajoutée"});
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création de la domiciliation : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    referenceListService.listResidenceTypes().then(function (data) {
        $scope.residenceTypes = data;
    });
}

function RenewResidenceCtrl($scope, personService, dialog, person) {

    $scope.person = person;

    $scope.renewResidence = function () {
        personService.renewResidence($scope.person.id).
            success(function () {
                dialog.close({type: 'success', msg: "Domiciliation renouvelée"});
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de renouvellement de la domiciliation : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.cancel = function () {
        dialog.close();
    };
}
