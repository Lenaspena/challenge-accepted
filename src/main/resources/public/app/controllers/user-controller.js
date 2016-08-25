app.controller('UserController', ['$scope', '$location', '$log', '$routeParams', 'userService', 'challengeService', 'scopeService', '$window',
    function ($scope, $location, $log, $routeParams, userService, challengeService, scopeService, $window) {

        var urlString = $window.location.href;
        var formattedHttps = urlString.replace("https://", "https%3A//");
        $scope.websiteUrl = formattedHttps.replace("#", "%23");
        
        var userUrlId = $routeParams.id;
        userService.getUserById(userUrlId)
            .success(function (response) {
                $scope.activeUser = response;
            })
            .error(function () {
                $location.path('/error-user');
            });

        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
        };

        $scope.getListOfChallenges = function () {
            challengeService.getListOfAllChallenges()
                .success(function (response) {
                    $log.info('challengeService.getListOfChallenges() fetched the challenges from the database successfully!');
                    $scope.listOfChallenges = response;
                })
                .error(function (error) {
                    $log.error('challengeService.getListOfChallenges() ***FAILED*** to fetch the challenges from the database!');
                    $log.error(error);
                });
        };
        

        $scope.getListOfCompletedChallenges = function () {
            challengeService.getListOfCompletedChallenges()
                .success(function (response) {
                    $log.info("challengeService.getListOfCompletedChallenges() fetched all the completed challenges from the database successfully!");
                    $scope.listOfCompletedChallenges = response;
                })
                .error(function (error) {
                    $log.error("challengeService.getListOfCompletedChallenges() ***FAILED*** to fetch the completed challenges from the database!");
                    $log.error(error);
                });
        };

        $scope.getListOfCompletedChallenges();
        $scope.getListOfChallenges();

    }]);