function ResidentCtrl($scope, $http, $log) {
    $scope.resident = new Object();
    $scope.errors = [];
    $scope.messages = [];

    $scope.tableSortPredicate = "lastName";
    $scope.tableSortReverse = false;

    $scope.newResident = function () {
        $scope.errors = [];
        $scope.messages = [];
        $http.post("/resident/create", $scope.resident).
            success(function (data, status, headers, config) {
                $scope.messages.push({value: "Utilisateur créé avec succès"});
                $scope.resident = new Object();
                refreshResidents();
            }).
            error(function (data, status, headers, config) {
                $scope.errors.push({value: "Erreur de création"});
            });
    }

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
