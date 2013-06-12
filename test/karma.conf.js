basePath = '../';

files = [
    JASMINE,
    JASMINE_ADAPTER,
    'public/third-party/angular/angular.js',
    'public/third-party/angular/angular-*.js',
    'public/js/*.js',
    'test/unit/*.js',
    'test/unit/**/*.js',
];

autoWatch = true;

browsers = ['Firefox'];

junitReporter = {
    outputFile: 'test_out/unit.xml',
    suite: 'unit'
};
