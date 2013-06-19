"use strict";

angular.module('gestionCourrierServices', []).
    factory('residentService',function ($http) {
        /**
         * Service for "residents" module.
         */
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
             * Find a resident by text.
             * @param text Text search criteria.
             * @returns {*} List of residents matching search criteria.
             */
            findResidents: function (text) {
                if (!text || text.length < 3) {
                    return [];
                }
                var promise = $http.get('/residents/find/' + text).then(function (response) {
                    return response.data;
                });
                return promise;
            }
        };
        return residentService;
    }).
    factory('mailService',function ($http) {
        /**
         * Mail service
         */
        var mailService = {
            allMails: function () {
                var promise = $http.get('/outbox/mails').then(function (response) {
                    return response.data;
                });
                return promise;
            }
        };
        return mailService;
    }).
    factory('referenceListService', function ($http) {
        /**
         * Service providing general purpose reference lists.
         */
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
