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
    }).
    directive('residenceProgress', function () {
        return {
            restrict: 'AC',
            link: function (scope, element, attrs) {
                scope.$watch(attrs.residenceProgress, function (value) {
                    var cssClass;
                    if (value >= 50 && value < 75) {
                        cssClass = 'label label-warning';
                    } else if (value >= 75) {
                        cssClass = 'label label-important';
                    }
                    if (cssClass) {
                        element.addClass(cssClass);
                    }
                });
            }
        };
    });
