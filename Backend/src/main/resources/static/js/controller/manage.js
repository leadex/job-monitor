var app = angular.module('JobMonitor');

app.controller('manageController', function ($scope, $http, $mdToast) {

    $scope.client = {
        query: {
            upwork: [],
            freelancer: [],
            guru: []
        },
        notification: {
            slack: {
                webHook: null,
                channel: null,
                botName: null,
                iconEmoji: null
            }
        }
    };

    $scope.showToastSuccess = function () {
        $mdToast.show({
            hideDelay: 3000,
            position: 'bottom right',
            controller: 'successToastController',
            templateUrl: 'view/successToast.html'
        });
    };

    $scope.showToastFail = function () {
        $mdToast.show({
            hideDelay: 3000,
            position: 'bottom right',
            controller: 'failToastController',
            templateUrl: 'view/failToast.html'
        });
    };

    $scope.getClient = function () {
        $http.get('/api/client').success(function (data) {
            $scope.client.query.upwork = data.query.upwork;
            $scope.client.query.freelancer = data.query.freelancer;
            $scope.client.query.guru = data.query.guru;

            $scope.client.notification.slack = data.notification.slack;
        });
    };

    $scope.saveClient = function () {
        $http.post('/api/client', $scope.client)
            .then(function (response) {
                    $scope.saveResponse = response.data;
                    $scope.showToastSuccess();
                },
                function (response) {
                    $scope.saveResponse = response.data;
                    $scope.showToastFail();
                });
    };

    $scope.getClient();
});