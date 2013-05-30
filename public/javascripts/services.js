//
// Resident service
//
app.factory('residentService', function ($http) {
    var residentService = {
        async: function () {
            var promise = $http.get('/residents').then(function (response) {
                return response.data;
            });

            return promise;
        }
    };
    return residentService;
});
