'use strict';

function AllPersonsCtrl($scope, $dialog, personService) {
    $scope.messages = [];

    $scope.tableSortPredicate = "lastName";
    $scope.tableSortReverse = false;

    $scope.newPersonDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/person/newPerson.html',
        controller: 'NewPersonCtrl'
    };

    $scope.newPersonDialog = function () {
        var d = $dialog.dialog($scope.newPersonDialogOpts);
        d.open().then(function (result) {
            $scope.messages = [];
            if (result) {
                $scope.messages.push(result);
                refresh();
            }
        });
    };

    $scope.setSortCriterion = function (sortCriterion) {
        if (sortCriterion == $scope.tableSortPredicate) {
            $scope.tableSortReverse = !$scope.tableSortReverse;
        } else {
            $scope.tableSortPredicate = sortCriterion;
            $scope.tableSortReverse = false;
        }
    };


    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    var refresh = function () {
        personService.all().then(function (data) {
            $scope.allPersons = data;
        });
    };

    refresh();
}

function NewPersonCtrl($scope, dialog, personService, referenceListService) {
    $scope.messages = [];
    $scope.person = {};

    $scope.genders = [];
    $scope.departments = [];

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.createPerson = function () {
        $scope.messages = [];
        personService.create($scope.person).
            success(function () {
                dialog.close({type: 'success', msg: "La fiche de " + $scope.person.firstName + " " + $scope.person.lastName + " a été créée"
                });
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    referenceListService.listDepartments().then(function (data) {
        $scope.departments = data;
    });

    referenceListService.listGenders().then(function (data) {
        $scope.genders = data;
    });
}

function ViewPersonCtrl($scope, $dialog, $routeParams, personService) {
    $scope.messages = [];
    $scope.person = {};
    $scope.residenceProgress = {};

    $scope.newResidenceDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/person/residence/newResidence.html',
        controller: 'NewResidenceCtrl',
        resolve: {personId: function () {
            return $scope.person.id
        }}
    };

    $scope.renewResidenceDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/person/residence/renewResidence.html',
        controller: 'RenewResidenceCtrl',
        resolve: {person: function () {
            return $scope.person
        }}
    };

    $scope.newResidenceDialog = function () {
        var d = $dialog.dialog($scope.newResidenceDialogOpts);
        d.open().then(function (result) {
            if (result) {
                $scope.messages = [];
                refresh();
                $scope.messages.push(result);
            }
        });
    };

    $scope.renewLatestResidence = function () {
        var d = $dialog.dialog($scope.renewResidenceDialogOpts);
        d.open().then(function (result) {
            if (result) {
                $scope.messages = [];
                refresh();
                $scope.messages.push(result);
            }
        });
    };

    $scope.follow = function () {
        personService.follow($scope.person.id).
            success(function () {
                $scope.person.isFollowed = true;
            }).
            error(function (data, status, headers, config) {
                $scope.messages = [];
                $scope.messages.push({type: 'error', msg: "Erreur technique : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.unfollow = function () {
        personService.unfollow($scope.person.id).
            success(function () {
                $scope.person.isFollowed = false;
            }).
            error(function (data, status, headers, config) {
                $scope.messages = [];
                $scope.messages.push({type: 'error', msg: "Erreur technique : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.getResidenceClass = function (residence) {
        if (residence.currentResidence) {
            return "info"
        } else {
            return "";
        }
    };

    var computeResidenceProgress = function () {
        var value = $scope.person.residenceProgress;
        if (value < 0) {
            return;
        }
        var type;
        if (value < 50) {
            type = 'info';
        } else if (value < 75) {
            type = 'warning';
        } else {
            type = 'danger';
        }
        $scope.residenceProgress = {value: value, type: type};
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    var refresh = function () {
        personService.fetch($routeParams.personId).then(function (data) {
            $scope.person = data;
            computeResidenceProgress();
        });
    };

    refresh();
}

function EditPersonCtrl($scope, $location, $routeParams, personService, referenceListService) {
    $scope.messages = [];
    $scope.person = {};
    $scope.departments = [];

    $scope.updatePerson = function () {
        personService.update($scope.person).
            success(function () {
                $location.path("/person/" + $scope.person.id);
            }).
            error(function (data, status, headers, config) {
                $scope.messages = [];
                $scope.messages.push({type: 'error', msg: "Erreur de mise-à-jour : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    personService.fetch($routeParams.personId).then(function (data) {
        $scope.person = data;
    });

    referenceListService.listDepartments().then(function (data) {
        $scope.departments = data;
    });
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