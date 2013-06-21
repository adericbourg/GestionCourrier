"use strict";

angular.module('gestionCourrierDirectives', []).
    directive('inverted',function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {
                ngModel.$parsers.push(function (val) {
                    return !val;
                });
                ngModel.$formatters.push(function (val) {
                    return !val;
                });
            }
        };
    });
