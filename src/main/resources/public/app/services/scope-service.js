app.service('scopeService', ['$sce', '$location', 'userService', 'challengeService', '$log',
    function ($sce, $location, userService, challengeService, $log) {

        var activeChallenge;
        var activeUser;
        var loggedInUser;


        return {
            setActiveUser: function (user) {
                activeUser = user;
            },

            getActiveUser: function () {
                return activeUser;
            },

            setActiveChallenge: function (challenge) {
                activeChallenge = challenge;
            },

            getActiveChallenge: function () {
                return activeChallenge;
            },

            getLoggedInUser: function () {
                return JSON.parse(sessionStorage.getItem('loggedInUser'));
            },

            viewUserProfilePage: function (user) {
                userService.getUserByEmail(user.email).success(function (response) {
                    activeUser = response;
                    $location.path('/user-profile/' + response.id);
                });
            },

            viewChallengeProfilePage: function (challenge) {
                activeChallenge = challenge;
                $location.path('/challenge-profile/' + activeChallenge.id);
                //$scope.disableLikeButton = $scope.isChallengeUpvotedByUser(challenge); // scope variable for using with ng-show.
            },

            // Return true if the logged in user is the creator of the challenge, else false.
            isLoggedInUserTheChallengeCreator: function (challenge) {
                if (challenge != null || challenge != undefined) {
                    if (sessionStorage.getItem('isLoggedIn') == 'true') {
                        loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                        if (challenge.challengeCreator.id == loggedInUser.id) {
                            return true;
                        }
                    }
                }
                return false;
            },

            // Function for marking a YouTube URL as trusted.
            markUrlAsTrusted: function (src) {
                return $sce.trustAsResourceUrl(src);
            },

            getYoutubeUrlId: function (youtubeUrl) {
                console.log(youtubeUrl.substring(30));
                return youtubeUrl.substring(30);
            },

            // Messages for the alert-popup.
            showAlertPopup: function (msg) {
                $.alert({
                    title: 'Alert',
                    content: msg
                });
            },

            loginAlertMessage: function () {
                return 'Login to use this feature!';
            },

            alertPopupMsgInvalidYoutubeUrl: function () {
                return 'Please provide a valid YouTube Url';
            }
        }
    }]);