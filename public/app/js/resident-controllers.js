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

    $scope.genders = [];
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
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    }

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
                refresh();
                $scope.messages.push(result);
            }
        });
    };

    $scope.renewLatestResidence = function () {
        var d = $dialog.dialog($scope.renewResidenceDialogOpts);
        d.open().then(function (result) {
            if (result) {
                refresh();
                $scope.messages.push(result);
            }
        });
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
    }

    var refresh = function () {
        residentService.fetchResident($routeParams.residentId).then(function (data) {
            $scope.resident = data;
            computeResidenceProgress();
        });
    }

    refresh();
}

function EditResidentCtrl($scope, $http, $location, $routeParams, residentService, referenceListService) {
    $scope.messages = [];
    $scope.resident = {};
    $scope.departments = [];

    $scope.updateResident = function () {
        $http.post("/resident/" + $scope.resident.id + "/update", $scope.resident).
            success(function (data, status, headers, config) {
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

function NewResidenceCtrl($scope, $http, dialog, residentId, referenceListService) {

    $scope.residentId = residentId;
    $scope.residence = {};

    $scope.messages = [];
    $scope.residenceTypes = [];

    $scope.createResidence = function () {
        $scope.messages = [];
        $http.post("/resident/" + $scope.residentId + "/addResidence", $scope.residence).
            success(function (data, status, headers, config) {
                dialog.close({type: 'success', msg: "Domiciliation ajoutée"});
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création de la domiciliation : " + status + " (" + config.method + ":" + config.url + ")."});
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
                dialog.close({type: 'success', msg: "Domiciliation renouvelée"});
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de renouvellement de la domiciliation : " + status + " (" + config.method + ":" + config.url + ")."});
            });
    }

    $scope.cancel = function () {
        dialog.close();
    };
}