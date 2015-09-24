'use strict';
  
angular.module('Authentication')

.config(function($locationProvider) {
  $locationProvider.html5Mode(true);
})
  
.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'AuthenticationService',
    function ($scope, $rootScope, $location, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();
    
        $scope.login = function () {	
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.retcode == "0") {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    $scope.dataLoading = false; 
                    $location.path('/protected/index.html');
                    window.location.reload();
                } else {
                    $scope.error = response.retMsg;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);