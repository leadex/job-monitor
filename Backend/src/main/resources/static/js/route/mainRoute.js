var app = angular.module('JobMonitor', ['ngRoute', 'ngMaterial', 'ngMessages', 'material.svgAssetsCache'])

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/manage', {
            templateUrl: 'view/manage.html',
            controller: 'manageController'
        }).
        when('/', {
            templateUrl: 'view/main.html',
            controller: 'mainController'
        }).
        when('/login', {
            templateUrl: 'view/login.html',
            controller: 'loginController'
        }).
        otherwise({
            redirectTo: '/'
        });
    }]);

app.directive('mdChips', function () {
    return {
        restrict: 'E',
        require: 'mdChips', // Extends the original mdChips directive
        link: function (scope, element, attributes, mdChipsCtrl) {
            mdChipsCtrl.onInputBlur = function () {
                this.inputHasFocus = false;

                // ADDED CODE
                var chipBuffer = this.getChipBuffer();
                if (chipBuffer != "") { // REQUIRED, OTHERWISE YOU'D GET A BLANK CHIP
                    this.appendChip(chipBuffer);
                    this.resetChipBuffer();
                }
                // - EOF - ADDED CODE
            };
        }
    }
});