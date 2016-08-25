app.controller('ListController', ['$scope', '$log', 'scopeService', 'challengeService', 'userService',
    function ($scope, $log, scopeService, challengeService, userService) {

        $scope.loggedInUser = scopeService.getLoggedInUser();

        /*
         * Functions for challenges in lists
         * */
        $scope.viewChallengeProfilePage = function (challenge) {
            scopeService.viewChallengeProfilePage(challenge);
            //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
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
        
        /*
         * Functions for users in lists
         * */

        $scope.viewUserProfilePage = function (user) {
            scopeService.viewUserProfilePage(user);
        };

        $scope.getListOfUsers = function () {
            userService.getListOfAllUsers()
                .success(function (response) {
                    $log.info('challengeService.getListOfUsers() fetched the users from the database!');
                    $scope.listOfUsers = response;
                })
                .error(function (error) {
                    $log.error('challengeService.getListOfUsers() ***FAILED*** to fetch the users from the database!');
                    $log.error(error);
                })
        };


        /*
         Below code handles the upvoting and the managing of the already upvoted challenges.
         The code does not execute if no user is logged in.
         */
        $scope.upvoteChallenge = function (challenge) {
            if (sessionStorage.getItem("isLoggedIn") == 'true') {
                challengeService.addOrRemoveUserToChallengeUpvoters(sessionStorage.getItem('loggedInUser'), challenge.id)
                    .success(function () {
                        $log.info("challengeService.addUserToChallengeUpvoters() was successfully executed!");
                        // Update the list of challenges after creation of the new challenge,
                        $scope.getListOfChallenges();
                        $scope.getListOfCompletedChallenges();
                    })
                    .error(function (error) {
                        $log.error("challengeService.addUserToChallengeUpvoters() ***FAILED*** to execute!");
                        $log.error(error);
                    });
            } else {
                scopeService.showAlertPopup(scopeService.loginAlertMessage());
            }
        };

        // Return true if the challenge is already upvoted by the logged in user, else false.
        $scope.isChallengeUpvotedByUser = function (challenge) {
            if ((sessionStorage.getItem('isLoggedIn') == 'true') && (challenge != null)) {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                for (var i = 0; i < challenge.challengeUpvoters.length; i++) {
                    if (challenge.challengeUpvoters[i] === $scope.loggedInUser.id) {
                        return true;
                    }
                }
            }
            return false;
        };

        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };

        $scope.markUrlAsTrusted = function (src) {
            return scopeService.markUrlAsTrusted(src);
        };

        $scope.addPointToChallenge = function (challenge) {
            if (sessionStorage.getItem('isLoggedIn') == 'true') {
                challengeService.addOrRemovePointToCompletedChallenge(scopeService.getLoggedInUser(), challenge.id)
                    .success(function () {
                        $log.info("addPointToCompletedChallenge() was successfully executed!");
                        $scope.getListOfCompletedChallenges();

                    })
                    .error(function (error) {
                        $log.error("addPointToCompletedChallenge() ***FAILED*** to execute");
                        $log.error(error);

                    });
            } else {
                scopeService.showAlertPopup(scopeService.loginAlertMessage());
            }

        };


        // Fetch the list of challenges on application start.
        $scope.getListOfChallenges();
        $scope.getListOfUsers();
        $scope.getListOfCompletedChallenges();


    }]);