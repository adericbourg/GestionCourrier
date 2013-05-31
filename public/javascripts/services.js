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

app.factory('referenceListService', function ($http) {
    var referenceListService = {
        listDepartments: function () {
            var promise = $http.get('/departments').then(function (response) {
                return response.data;
            });
            return promise;
        }
    }
    return referenceListService;
});
