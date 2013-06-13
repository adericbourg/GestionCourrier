basePath = '../';

files = [
    JASMINE,
    JASMINE_ADAPTER,
    'public/third-party/jquery/*.js',
    'public/third-party/angular/angular.js',
    'public/third-party/angular/i18n/angular-*.js',
    'public/third-party/moment/moment.min.js',
    'public/third-party/moment/moment-*.js',
    'public/js/**/*.js',
    'test/lib/**/*.js',
    'test/unit/**/*.js'
];

colors = true;
autoWatch = true;

browsers = ['Firefox'];

junitReporter = {
    outputFile: 'test_out/unit.xml',
    suite: 'unit'
};
