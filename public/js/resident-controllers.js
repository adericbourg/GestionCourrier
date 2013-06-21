'use strict';

function AllResidentsCtrl($scope, $dialog, residentService) {
    $scope.messages = [];

    $scope.tableSortPredicate = "lastName";
    $scope.tableSortReverse = false;

    $scope.newResidentDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/resident/newResident.html',
        controller: 'NewResidentCtrl'
    };

    $scope.newResidentDialog = function () {
        var d = $dialog.dialog($scope.newResidentDialogOpts);
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
        residentService.allResidents().then(function (data) {
            $scope.allResidents = data;
        });
    };

    refresh();
}

function NewResidentCtrl($scope, dialog, residentService, referenceListService) {
    $scope.messages = [];
    $scope.resident = {};

    $scope.genders = [];
    $scope.departments = [];

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.createResident = function () {
        $scope.messages = [];
        residentService.createResident($scope.resident).
            success(function () {
                dialog.close({type: 'success', msg: "La fiche de " + $scope.resident.firstName + " " + $scope.resident.lastName + " a été créée"
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

function ViewResidentCtrl($scope, $dialog, $routeParams, residentService) {
    $scope.messages = [];
    $scope.resident = {};
    $scope.residenceProgress = {};

    $scope.newResidenceDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/resident/residence/newResidence.html',
        controller: 'NewResidenceCtrl',
        resolve: {residentId: function () {
            return $scope.resident.id
        }}
    };

    $scope.renewResidenceDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/resident/residence/renewResidence.html',
        controller: 'RenewResidenceCtrl',
        resolve: {resident: function () {
            return $scope.resident
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

    $scope.getResidenceClass = function (residence) {
        if (residence.currentResidence) {
            return "info"
        } else {
            return "";
        }
    };

    var computeResidenceProgress = function () {
        var value = $scope.resident.residenceProgress;
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
        residentService.fetchResident($routeParams.residentId).then(function (data) {
            $scope.resident = data;
            computeResidenceProgress();
        });
    };

    refresh();
}

function EditResidentCtrl($scope, $location, $routeParams, residentService, referenceListService) {
    $scope.messages = [];
    $scope.resident = {};
    $scope.departments = [];

    $scope.updateResident = function () {
        residentService.updateResident($scope.resident).
            success(function () {
                $location.path("/resident/" + $scope.resident.id);
            }).
            error(function (data, status, headers, config) {
                $scope.messages = [];
                $scope.messages.push({type: 'error', msg: "Erreur de mise-à-jour : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    residentService.fetchResident($routeParams.residentId).then(function (data) {
        $scope.resident = data;
    });

    referenceListService.listDepartments().then(function (data) {
        $scope.departments = data;
    });
}

function NewResidenceCtrl($scope, dialog, residentId, residentService, referenceListService) {

    $scope.residentId = residentId;
    $scope.residence = {};

    $scope.messages = [];
    $scope.residenceTypes = [];

    $scope.createResidence = function () {
        $scope.messages = [];
        residentService.createResidence(residentId, $scope.residence).
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

function RenewResidenceCtrl($scope, residentService, dialog, resident) {

    $scope.resident = resident;

    $scope.renewResidence = function () {
        residentService.renewResidence($scope.resident.id).
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