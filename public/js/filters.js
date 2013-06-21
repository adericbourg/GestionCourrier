"use strict";

angular.module('gestionCourrierFilters', []).
    filter('localDate', function () {
        /**
         * Filter for date provided in format [yyyy,MM,dd] to display it in French format (dd/MM/yyyy).
         */
        return function (text, format) {
            if (!text) {
                return '';
            }

            format = format || 'L';

            var pad = function (n, width) {
                n = n + '';
                return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
            };

            var value = text[0] + '-' + pad(text[1], 2) + '-' + pad(text[2], 2);
            return moment(value, 'YYYY-MM-DD').format(format);
        };
    }).
    filter('staticList', function () {
        /**
         * Filter for static list values. Static lists are provided by backend with the following structure:
         *     {key: "some key", value: "some value"}
         *
         * The filter will display the value if it exists and an empty string otherwise.
         */
        return function (listItem) {
            if (!listItem) {
                return '';
            }
            return listItem.value ? listItem.value : '';
        };
    });
