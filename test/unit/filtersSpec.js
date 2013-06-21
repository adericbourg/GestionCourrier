'use strict';

describe('filter', function () {

    beforeEach(module('gestionCourrierFilters'));

    describe('localDate', function () {

        it('should convert local date to french formatted date',
            inject(function (localDateFilter) {
                expect(undefined).toBe(undefined);
                expect(localDateFilter([2013, 5, 12])).toBe('12/05/2013');
            }));
    });

    describe('staticList', function () {

        it('should convert static list object into its display value',
            inject(function (staticListFilter) {
                expect(staticListFilter(undefined)).toBe('');
                expect(staticListFilter({key: 'A', value: 'B'})).toBe('B');
            }));
    });
});
