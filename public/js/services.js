"use strict";

angular.module('gestionCourrierServices', []).
    factory('residentService',function ($http) {
        /**
         * Service for "residents" module.
         */
        return {
            /**
             * Returns all residents.
             * @returns {*}
             */
            allResidents: function () {
                return $http.get('/json/residents').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Fetches a resident detail.
             * @param residentId Resident id.
             * @returns {*} Resident detail for specified id.
             */
            fetchResident: function (residentId) {
                return $http.get('/json/resident/' + residentId).then(function (response) {
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
                return $http.get('/json/residents/find/' + text).then(function (response) {
                    return response.data;
                });
            },
            /**
             * Create new resident.
             * @param resident Resident to be created.
             * @returns {*|HttpPromise} Promise.
             */
            createResident: function (resident) {
                return $http.post("/json/resident/create", resident);
            },
            /**
             * Update resident.
             * @param resident Resident to be updated.
             * @returns {*|HttpPromise} Promise.
             */
            updateResident: function (resident) {
                return $http.post("/json/resident/" + resident.id + "/update", resident);
            },
            /**
             * Create new residence for resident.
             * @param residentId Resident id.
             * @param residence Residence to be created.
             * @returns {*|HttpPromise} Promise.
             */
            createResidence: function (residentId, residence) {
                return $http.post("/json/resident/" + residentId + "/addResidence", residence);
            },
            /**
             * Renew latest residence.
             * @param residentId Resident id to whom residence is to be renewed.
             * @returns {*|HttpPromise} Promise.
             */
            renewResidence: function (residentId) {
                return $http.post("/json/resident/" + residentId + "/renewResidence")
            }
        };
    }).
    factory('mailService',function ($http) {
        /**
         * Mail service
         */
        return {
            /**
             * Return all mails in outbox.
             * @returns {*} All mails still on outbox.
             */
            allMails: function () {
                return $http.get('/json/outbox/mails').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Register inbox mail.
             * @param inboxMail Inbox mail.
             * @returns {*|HttpPromise} Promise.
             */
            registerMail: function (inboxMail) {
                return $http.post("/json/inbox/register", inboxMail);
            },
            /**
             * Withdraw outbox mail.
             * @param mailId Mail id from outbox.
             * @returns {*|HttpPromise} Promise.
             */
            withdraw: function (mailId) {
                return $http.post('/json/outbox/withdraw/' + mailId);
            }
        };
    }).
    factory('referenceListService', function ($http) {
        /**
         * Service providing general purpose reference lists.
         */
        return {
            /**
             * Lists all departments.
             * @returns {*} All departments.
             */
            listDepartments: function () {
                return $http.get('/json/departments').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Lists all residence types.
             * @returns {*} All residence types.
             */
            listResidenceTypes: function () {
                return $http.get('/json/residencetypes').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Lists all genders.
             * @returns {*} All genders.
             */
            listGenders: function () {
                return $http.get('/json/genders').then(function (response) {
                    return response.data;
                });
            }
        };
    });
