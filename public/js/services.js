"use strict";

angular.module('gestionCourrierServices', []).
    factory('personService',function ($http) {
        /**
         * Service for "persons" module.
         */
        return {
            /**
             * Returns all persons.
             * @returns {*}
             */
            all: function () {
                return $http.get('/json/persons').then(function (response) {
                    return response.data;
                });
            },
            /**
             * Fetches a person detail.
             * @param personId Person id.
             * @returns {*} Person detail for specified id.
             */
            fetch: function (personId) {
                return $http.get('/json/person/' + personId).then(function (response) {
                    return response.data;
                });
            },
            /**
             * Find a person by text.
             * @param text Text search criteria.
             * @returns {*} List of persons matching search criteria.
             */
            find: function (text) {
                if (!text || text.length < 3) {
                    return [];
                }
                return $http.get('/json/person/find/' + text).then(function (response) {
                    return response.data;
                });
            },
            /**
             * Create new person.
             * @param person Person to be created.
             * @returns {*|HttpPromise} Promise.
             */
            create: function (person) {
                return $http.post("/json/person/create", person);
            },
            /**
             * Update person.
             * @param person Person to be updated.
             * @returns {*|HttpPromise} Promise.
             */
            update: function (person) {
                return $http.post("/json/person/" + person.id + "/update", person);
            },
            /**
             * Follow person.
             * @param personId Person id.
             * @returns {*|HttpPromise} Promise.
             */
            follow: function (personId) {
                return $http.post("/json/person/" + personId + "/follow");
            },
            /**
             * Unfollow person.
             * @param personId Person id.
             * @returns {*|HttpPromise} Promise.
             */
            unfollow: function (personId) {
                return $http.post("/json/person/" + personId + "/unfollow");
            },
            /**
             * Create new residence for person.
             * @param personId Person id.
             * @param residence Residence to be created.
             * @returns {*|HttpPromise} Promise.
             */
            createResidence: function (personId, residence) {
                return $http.post("/json/person/" + personId + "/addResidence", residence);
            },
            /**
             * Renew latest residence.
             * @param personId Person id to whom residence is to be renewed.
             * @returns {*|HttpPromise} Promise.
             */
            renewResidence: function (personId) {
                return $http.post("/json/person/" + personId + "/renewResidence")
            }
        };
    }).
    factory('residenceService',function ($http) {
        /**
         * Residence service.
         */
        return {
            allResidents: function () {
                return $http.get('/json/residence/allResidents').then(function (response) {
                    return response.data;
                });
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
