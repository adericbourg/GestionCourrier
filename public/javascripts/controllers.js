function ResidentCtrl($scope, $dialog, residentService) {
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
            if (result) {
                $scope.messages.push(result);
                refresh();
            }
        });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    var refresh = function () {
        residentService.allResidents().then(function (data) {
            $scope.allResidents = data;
        });
    }

    refresh();
}

function NewResidentCtrl($scope, $http, dialog, referenceListService) {
    $scope.messages = [];
    $scope.resident = {};

    $scope.sexes = [];
    $scope.departments = [];

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.createResident = function () {
        $scope.messages = [];
        $http.post("/resident/create", $scope.resident).
            success(function (data, status, headers, config) {
                dialog.close({type: 'success', msg: "La fiche de " + $scope.resident.firstName + " " + $scope.resident.lastName + " a été créée"
                });
            }
        ).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création"});
            });
    }

    referenceListService.listDepartments().then(function (data) {
        $scope.departments = data;
    });

    referenceListService.listSexes().then(function (data) {
        $scope.sexes = data;
    });
}

function ViewResidentCtrl($scope, $http, $dialog, $routeParams, residentService) {
    $scope.resident = {};

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
                $scope.messages.push(result);
            }
        });
    };

    $scope.renewLatestResidence = function () {
        var d = $dialog.dialog($scope.renewResidenceDialogOpts);
        d.open().then(function (result) {
            if (result) {
                $scope.messages.push(result);
            }
        });
    };

    residentService.fetchResident($routeParams.residentId).then(function (data) {
        $scope.resident = data;
    });
}

function EditResidentCtrl($scope, $http, $location) {
    // TODO
}

function NewResidenceCtrl($scope, $http, dialog, residentId, referenceListService) {

    $scope.residentId = residentId;
    $scope.residence = {};

    $scope.messages = [];
    $scope.residenceTypes = [];

    $scope.createResidence = function () {
        $scope.messages = [];
        $http.post("/resident/" + $scope.residentId + "/addResidence", $scope.residence).
            success(function (data, status, headers, config) {
                dialog.close({type: 'success', msg: "Domiciliation ajoutée"
                });
            }
        ).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création de la domiciliation"});
            });
    }

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

function RenewResidenceCtrl($scope, $http, dialog, resident) {

    $scope.resident = resident;

    $scope.renewResidence = function () {
        $http.post("/resident/" + $scope.resident.id + "/renewResidence").
            success(function (data, status, headers, config) {
                // TODO
            }
        ).
            error(function (data, status, headers, config) {
                // TODO
            });
    }

    $scope.cancel = function () {
        dialog.close();
    };
}