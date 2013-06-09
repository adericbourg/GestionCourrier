function AllMailsController($scope, mailService) {

    $scope.allMails = [];

    var refresh = function () {
        mailService.allMails().then(function (data) {
            $scope.allMails = data;
        });
    }

    refresh();
}
