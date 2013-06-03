//
// Resident service
//
app.factory('residentService', function ($http) {
    var residentService = {
        allResidents: function () {
            var promise = $http.get('/residents').then(function (response) {
                return response.data;
            });

            return promise;
        },
        fetchResident: function (id) {
            var promise = $http.get('/resident/' + id).then(function (response) {
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
        },
        listResidenceTypes: function () {
            var promise = $http.get('/residencetypes').then(function (response) {
                return response.data;
            });
            return promise;
        }
    }
    return referenceListService;
});
