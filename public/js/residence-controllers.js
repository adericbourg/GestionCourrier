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