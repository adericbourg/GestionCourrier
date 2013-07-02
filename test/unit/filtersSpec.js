'use strict';

describe('filter', function () {

    beforeEach(module('gestionCourrierFilters'));

    describe('localDate', function () {

        it('should convert local date to french formatted date',
            inject(function (localDateFilter) {
                expect(localDateFilter(undefined)).toBe('');
                expect(localDateFilter('some dummy')).toBe('');
                expect(localDateFilter('')).toBe('');
                expect(localDateFilter([2013, 42, 22])).toBe('');
                expect(localDateFilter([2013, 5, 12])).toBe('12/05/2013');
                expect(localDateFilter([2013, 5, 12], 'YYYY DD MM')).toBe('2013 12 05');
            }));
    });

    describe('staticList', function () {

        it('should convert static list object into its display value',
            inject(function (staticListFilter) {
                expect(staticListFilter(undefined)).toBe('');
                expect(staticListFilter({key: 'A'})).toBe('');
                expect(staticListFilter({value: 'B'})).toBe('B');
                expect(staticListFilter({key: 'A', value: 'B'})).toBe('B');
                expect(staticListFilter({key: {a: 'A'}, value: 'B'})).toBe('B');
            }));
    });
});
