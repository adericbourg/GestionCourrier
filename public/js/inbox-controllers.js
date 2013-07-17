'use strict';

function RegisterMailCtrl($scope, personService, mailService) {

    $scope.inbox = {};
    $scope.messages = [];

    $scope.setArrivalDateToday = function () {
        $scope.inbox.arrivalDate = moment().format("L");
    };

    $scope.findPersons = function (text) {
        return personService.find(text);
    };

    $scope.registerMail = function () {
        mailService.registerMail($scope.inbox).
            success(function () {
                $scope.messages = [
                    {type: 'success', msg: "Courrier à destination de " + $scope.inbox.recipient.display + " enregistré"}
                ];
                $scope.inbox = {};
            }).
            error(function (data, status, headers, config) {
                $scope.handleError($scope, data, status, headers, config);
            });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };
}
