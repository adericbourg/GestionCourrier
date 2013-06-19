'use strict';

function RegisterMailCtrl($scope, $http, residentService) {

    $scope.inbox = {};
    $scope.messages = [];

    $scope.setArrivalDateToday = function () {
        $scope.inbox.arrivalDate = moment().format("L");
    };

    $scope.findResidents = function (text) {
        if (!text || text.length < 3) {
            return;
        }
        return residentService.findResidents(text);
    };

    $scope.registerMail = function () {
        $http.post("/inbox/register", $scope.inbox).
            success(function (data, status, headers, config) {
                $scope.messages = [
                    {type: 'success', msg: "Courrier à destination de " + $scope.inbox.recipient.display + " enregistré"}
                ];
                $scope.inbox = {};
            }).
            error(function (data, status, headers, config) {
                $scope.messages = [
                    {type: 'error', msg: "Erreur d'enregistrement : " + status + " (" + config.method + ":" + config.url + ")."}
                ];
            });
    }

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };
}
