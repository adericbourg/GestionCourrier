function ResidentCtrl($scope, $http, $log, $dialog) {
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
                refreshResidents();
            }
        });
    };

    var refreshResidents = function () {
        $http({
            method: 'GET',
            url: '/residents'
        }).success(function (data, status, headers, config) {
                $scope.allResidents = data;
                $log.log('Fetched ' + data.length + ' residents');
            });
    }

    refreshResidents();
}

function NewResidentCtrl($scope, $http, dialog) {
    $scope.messages = [];
    $scope.resident = {};

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.createResident = function () {
        $scope.messages = [];
        $http.post("/resident/create", $scope.resident).
            success(function (data, status, headers, config) {
                dialog.close({type: 'success', msg: "Utilisateur créé avec succès"});
            }).
            error(function (data, status, headers, config) {
                $scope.messages.push({type: 'error', msg: "Erreur de création"});
            });
    }
}

function ViewResidentCtrl($scope) {
    // TODO
}

function EditResidentCtrl($scope, $http, $location) {
    // TODO
}
