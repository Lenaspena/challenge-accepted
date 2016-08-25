app.controller('ApproveVideoController', ['$scope', '$log', 'scopeService', 'challengeService',
    function ($scope, $log, scopeService, challengeService) {

        $scope.activeChallenge = scopeService.getActiveChallenge();

        $scope.getListOfUnapprovedChallenges = function () {
            challengeService.getListOfUnapprovedChallenges()
                .success(function (response) {
                    $scope.listOfUnapprovedChallenges = response;
                    $log.info("challengeService.getListOfUnapprovedChallenges fetched all the unapproved challenges successfully!");
                })
                .error(function (error) {
                    $log.error(error);
                    $log.error("challengeService.getListOfUnapprovedChallenges() ***FAILED*** to fetch all the challenges from the database!");
                });
        };


        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
        };

        $scope.markUrlAsTrusted = function (src) {
            return scopeService.markUrlAsTrusted(src);
        };
        
        $scope.getYoutubeUrlId = function (youtubeUrl) {
            return scopeService.getYoutubeUrlId(youtubeUrl);
        }

        // The challenge requester confirm the challenge is performed satisfactory.
        $scope.completeChallenge = function (challenge) {
            challengeService.assignPointsToUser(challenge.id)
                .success(function (response) {
                    $log.info("challengeService.assignPointsToUser() was successfully executed!");
                    $scope.activeChallenge = response;
                    $scope.getListOfUnapprovedChallenges();
                })
                .error(function (error) {
                    $log.error(error);
                    $log.error("challengeService.assignPointsToUser() ***FAILED*** to execute!");
                });
        };

        $scope.disapproveChallenge = function (challenge) {
            challengeService.disapproveCurrentChallenge(challenge.id, $('#disapprove-commentfield').val())
                .success(function (response) {
                    $log.info("Challenge was disapproved! Returned to available challenges");
                    challenge = response;
                    $scope.getListOfUnapprovedChallenges();
                }).error(function (error) {
                $log.error(error);
            })
        };

        $scope.getListOfUnapprovedChallenges();

    }]);