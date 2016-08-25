app.controller('ChallengeProfileController', ['$scope', '$log', '$location', '$routeParams', 'scopeService', 'challengeService', '$window',
    function ($scope, $log, $location, $routeParams, scopeService, challengeService, $window) {


        var urlString = $window.location.href;
        var formattedHttps = urlString.replace("https://", "https%3A//");
        $scope.websiteUrl = formattedHttps.replace("#", "%23");
        /*
        Get challenge from database depending on the id in the url-path, and save it to the
        activeChallenge scope.
         */
        var challengeUrlId = $routeParams.id;
        var loggedInUserId;
        $scope.loggedInUser = scopeService.getLoggedInUser();
        if(sessionStorage.getItem("isLoggedIn") == 'true'){
            loggedInUserId = $scope.loggedInUser.id;
        }else{
            loggedInUserId = 0;
        }
        challengeService.getChallengeById(challengeUrlId, loggedInUserId).success(function (response) {
            $scope.activeChallenge = response;

            //TODO rethink this if-case
/*
            $scope.loggedInUser = scopeService.getLoggedInUser();

            if (response.challengeClaimed && !response.challengeCompleted) {
                console.log("Challenge is claimed!");
                if (($scope.loggedInUser != null) && (response.challengeClaimer.id == $scope.loggedInUser.id)) {
                    $scope.activeChallenge = response;
                } else {
                    $location.path('/error-challenge');
                }
            } else {
                console.log("Challenge is not claimed!");
                $scope.activeChallenge = response;
            }

*/
        }).error(function () {
            console.log("getChallengeById was sent to error!");
            $location.path('/error-challenge');
        });
        
        
        $scope.isLoggedInUserTheChallengeCreator = function (challenge) {
            return scopeService.isLoggedInUserTheChallengeCreator(challenge);
        };

        $scope.viewUserProfilePage = function (challenge) {
            scopeService.viewUserProfilePage(challenge);
        };

        /*
         Below code manage different scenarios for when a user tries to update and manage the challenge.
         The response is updating the challenge the user is currently viewing.
         Usually all the code requires the user to be logged in before execution.
         */

        // The user claims the challenge and "promise" to perform it.
        $scope.claimCurrentChallenge = function (challenge) {
            if (sessionStorage.getItem("isLoggedIn") == 'true') {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));
                challengeService.updateChallengeClaimer($scope.loggedInUser, challenge.id)
                    .success(function (response) {
                        $log.info("challengeService.updateChallenge() was successfully executed!");
                        $scope.activeChallenge = response;
                    })
                    .error(function (error, status) {
                        if (status === 400) {
                            $log.error("challengeService.updateChallenge() ***FAILED*** to execute! Bad request! User cannot claim own challenge!");
                        } else {
                            $log.error("challengeService.updateChallenge() ***FAILED*** to execute!");
                            $log.error(error);
                        }
                    });
            } else {
                scopeService.showAlertPopup(scopeService.loginAlertMessage());
            }
        };

        // The user adds a YouTube url after performing the challenge.
        $scope.addYoutubeUrlToCurrentChallenge = function (challenge) {
            // TODO review naming conventions for 'YouTube' and 'Url'.

            var userProvidedUrl = $('#input-youtube-url').val();
            var convertedYoutubeUrl = $scope.convertToYouTubeEmbedUrl(userProvidedUrl);


            challengeService.addYoutubeUrlToChallenge(challenge.id, convertedYoutubeUrl)
                .success(function (response) {
                    $log.info("challengeService.addYoutubeUrlToChallenge() was successfully executed and YouTube url was saved to the challenge!");
                    $scope.activeChallenge = response;
                })
                .error(function (error) {
                    $log.error("challengeService.addYoutubeUrlToChallenge() ***FAILED*** to execute!");
                    $log.error(error);
                });

            scopeService.showAlertPopup('Once you click confirm in the next step, the video will be sent to ' + challenge.challengeCreator.firstName + ' ' + challenge.challengeCreator.lastName + '. Be sure it is the right one!');
        };

        /*
         Handles the YouTube URL.
         */

        $scope.markUrlAsTrusted = function (src) {
            return scopeService.markUrlAsTrusted(src);
        };

        // Convert the user provided YouTube URL to a embedded URL for use in an iframe.
        $scope.convertToYouTubeEmbedUrl = function (url) {
            var isYoutubeUrlCorrect = url.indexOf("watch?v=") > 1; // Is true if the string contains "watch?v="
            var baseUrl = "https://www.youtube.com/embed/";

            if (isYoutubeUrlCorrect) {
                var youTubeVideoId = url.substr(32);
                var finalUrl = baseUrl + youTubeVideoId;
            } else {
                scopeService.showAlertPopup(scopeService.alertPopupMsgInvalidYoutubeUrl());
            }
            return finalUrl;
        };

        $scope.confirmYoutubeVideoToCurrentChallenge = function (challenge) {
            // TODO should we have a confirmation-popup or not?
            // Reset form after confirmation of YouTube URL.
            $('#uploadYoutubeVideoForm').each(function () {
                this.reset();
            });

            challengeService.confirmUploadedYoutubeUrl(challenge.id)
                .success(function (response, status) {
                    if (status == 200) {
                        $log.info("challengeService.confirmUploadedYoutubeUrl() was successfully executed!");
                        $scope.activeChallenge = response;
                    }
                })
                .error(function (error) {
                    $log.error("challengeService.confirmUploadedYoutubeUrl() ***FAILED*** to execute!");
                    $log.error(error);
                });
            scopeService.showAlertPopup('The video is sent to ' + challenge.challengeCreator.firstName + ' ' + challenge.challengeCreator.lastName + ' and is now pending, waiting for confirmation.');

        };

    }]);