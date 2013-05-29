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
                $scope.messages = [];
                $http.post("/resident/create", result).
                    success(function (data, status, headers, config) {
                        $scope.messages.push({type: 'success', msg: "Utilisateur créé avec succès"});
                        refreshResidents();
                    }).
                    error(function (data, status, headers, config) {
                        $scope.messages.push({type: 'error', msg: "Erreur de création"});
                    });
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

function NewResidentCtrl($scope, dialog) {
    $scope.close = function (result) {
        dialog.close(result);
    };
}

function ViewResidentCtrl($scope) {
    // TODO
}

function EditResidentCtrl($scope, $http, $location) {
    // TODO
}
