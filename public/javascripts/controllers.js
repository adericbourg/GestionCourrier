function ResidentCtrl($scope, $http, $log) {
    $scope.tableSortPredicate = "lastName";
    $scope.tableSortReverse = false;

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

function NewResidentCtrl($scope, $http, $location) {
    $scope.resident = new Object();
    $scope.errors = [];
    $scope.messages = [];

    $scope.newResident = function () {
        $scope.errors = [];
        $scope.messages = [];
        $http.post("/resident/create", $scope.resident).
            success(function (data, status, headers, config) {
                $scope.messages.push({value: "Utilisateur créé avec succès"});
                $scope.resident = new Object();
                $location.path('/residents');
            }).
            error(function (data, status, headers, config) {
                $scope.errors.push({value: "Erreur de création"});
            });
    }
}
