app.controller('NotificationsController', ['$scope', '$log', 'scopeService', 'notificationService',
    function ($scope, $log, scopeService, notificationService) {

        $scope.loggedInUser = scopeService.getLoggedInUser();

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
        };

        /*
         Below code handles notifications.
         */
        $scope.updateListOfNotifications = function () {
            notificationService.getAllNotifications($scope.loggedInUser.id)
                .success(function (response) {
                    $log.info('notificationService.getAllNotifications() was successfully executed!');
                    $scope.listOfNotifications = response;
                })
                .error(function (error) {
                    $log.error('$scope.listOfNotifications ***FAILED*** to execute!');
                    $log.error(error);
                })
        };

        $scope.updateListOfNotifications();

    }]);