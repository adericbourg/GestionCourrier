function RegisterMailCtrl($scope, residentService) {

    $scope.inbox = {};

    $scope.setArrivalDateToday = function () {
        $scope.inbox.arrivalDate = moment().format("L");
    };

    $scope.findResidents = function (text) {
        return residentService.findResidents(text);
    };
}
