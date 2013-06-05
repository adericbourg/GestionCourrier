/**
 * Service for "residents" module.
 */
app.factory('residentService', function ($http) {
    var residentService = {
        /**
         * Returns all residents.
         * @returns {*}
         */
        allResidents: function () {
            var promise = $http.get('/residents').then(function (response) {
                return response.data;
            });

            return promise;
        },
        /**
         * Fetches a resident detail.
         * @param residentId Resident id.
         * @returns {*} Resident detail for specified id.
         */
        fetchResident: function (residentId) {
            var promise = $http.get('/resident/' + residentId).then(function (response) {
                return response.data;
            });
            return promise;
        },
        /**
         * Fetches all residences for specified resident.
         * @param residentId Resident id.
         * @returns {*} Residences for specified resident.
         */
        fetchResidences: function (residentId) {
            var promise = $http.get('/resident/' + residentId + '/residences').then(function (response) {
                return response.data;
            });
            return promise;
        }
    };
    return residentService;
});

/**
 * Service providing general purpose reference lists.
 */
app.factory('referenceListService', function ($http) {
    var referenceListService = {
        /**
         * Lists all departments.
         * @returns {*} All departments.
         */
        listDepartments: function () {
            var promise = $http.get('/departments').then(function (response) {
                return response.data;
            });
            return promise;
        },
        /**
         * Lists all residence types.
         * @returns {*} All residence types.
         */
        listResidenceTypes: function () {
            var promise = $http.get('/residencetypes').then(function (response) {
                return response.data;
            });
            return promise;
        },
        /**
         * Lists all genders.
         * @returns {*} All genders.
         */
        listGenders: function () {
            var promise = $http.get('/genders').then(function (response) {
                return response.data;
            });
            return promise;
        }
    }
    return referenceListService;
});
