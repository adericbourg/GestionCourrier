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
                return $http.get('/residents').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Fetches a resident detail.
             * @param residentId Resident id.
             * @returns {*} Resident detail for specified id.
             */
            fetchResident: function (residentId) {
                return $http.get('/resident/' + residentId).then(function (response) {
                    return response.data;
                });
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
                return $http.get('/residents/find/' + text).then(function (response) {
                    return response.data;
                });
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
                return $http.get('/outbox/mails').then(function (response) {
                    return response.data;
                });
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
                return $http.get('/departments').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Lists all residence types.
             * @returns {*} All residence types.
             */
            listResidenceTypes: function () {
                return $http.get('/residencetypes').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Lists all genders.
             * @returns {*} All genders.
             */
            listGenders: function () {
                return $http.get('/genders').then(function (response) {
                    return response.data;
                });
            }
        };
        return referenceListService;
    });
