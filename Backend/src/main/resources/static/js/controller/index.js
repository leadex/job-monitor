var app = angular.module('JobMonitor');
app.controller('indexController', function ($scope, $http, $interval) {
    $scope.isAuthorizated = false;

    //check authorization
    $http.get('/api/isAuth').success(function (response) {
        if (response.code == 'OK') {
            $scope.isAuthorizated = true;
        }
    });

    $scope.gaHook = function (event) {
        var ga = window['ga'];
        ga('send', {
            hitType: 'pageview',
            page: location.hash
        });
    }
});

app.config(function ($mdThemingProvider) {
    //Available palettes: red, pink, purple,
    // deep-purple, indigo, blue, light-blue, cyan, teal,
    // green, light-green, lime, yellow, amber, orange,
    // deep-orange, brown, grey, blue-grey
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('light-blue');
});