var app = angular.module('JobMonitor');
app.controller('successToastController', function ($scope, $mdToast) {

    var isDlgOpen = false;
    $scope.closeToast = function () {
        if (isDlgOpen) return;

        $mdToast
            .hide()
            .then(function () {
                isDlgOpen = false;
            });
    };
});