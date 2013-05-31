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
        residentService.async().then(function (data) {
            $scope.allResidents = data;
        });
    }

    refresh();
}

function NewResidentCtrl($scope, $http, dialog, referenceListService) {
    $scope.messages = [];
    $scope.resident = {};

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
}

function ViewResidentCtrl($scope) {
    // TODO
}

function EditResidentCtrl($scope, $http, $location) {
    // TODO
}
