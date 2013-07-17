'use strict';

function AllMailsController($scope, $dialog, mailService) {

    $scope.allMails = [];
    $scope.messages = [];

    $scope.withdrawDialogOpts = {
        backdrop: true,
        keyboard: true,
        backdropClick: true,
        templateUrl: '/assets/partials/outbox/withdraw.html',
        controller: 'WithdrawMailCtrl'
    };

    $scope.withdraw = function (mail) {
        $scope.withdrawDialogOpts.resolve = {mail: function () {
            return mail;
        }};

        var d = $dialog.dialog($scope.withdrawDialogOpts);
        d.open().then(function (result) {
            $scope.messages = [];
            $scope.messages.push(result);
            refresh();
        });
    };

    $scope.closeAlert = function (index) {
        $scope.messages.splice(index, 1);
    };

    var refresh = function () {
        mailService.allMails().then(function (data) {
            $scope.allMails = data;
        });
    };

    refresh();
}

function WithdrawMailCtrl($scope, mailService, dialog, mail) {
    $scope.mail = mail;

    $scope.cancel = function () {
        dialog.close();
    };

    $scope.confirmWithdrawal = function () {
        mailService.withdraw($scope.mail.id).
            success(function () {
                dialog.close({type: 'success', msg: "Courrier remis"});
            }).
            error(function (data, status, headers, config) {
                $scope.handleError($scope, data, status, headers, config);
            });
    };
}
