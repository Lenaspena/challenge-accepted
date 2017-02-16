app.factory('challengeService', function ($http) {

    var baseUrl = "https://localhost:63342/challengeaccepted/api/";
    
    return {
        getChallengeById: function (id, loggedInUserId) {
            return $http({
                url: baseUrl + 'challenge/' + id + '/user/' + loggedInUserId,
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        getListOfAllChallenges: function () {
            return $http({
                url: baseUrl + 'challenges/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },
        createNewChallenge: function (data, loggedInUserId) {
            return $http({
                url: baseUrl + 'challenge/create/challenge-creator/' + loggedInUserId,
                method: 'POST',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },
        updateChallenge: function (data) {
            return $http({
                url: baseUrl + 'challenge/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        updateChallengeClaimer: function (loggedInUser, challengeId) {
            return $http({
                url: baseUrl + 'challenge/' + challengeId + '/update-challenge-claimer/',
                method: 'PUT',
                data: loggedInUser,
                header: {'Content-Type': 'application/json'}
            })
        },

        addYoutubeUrlToChallenge: function (challengeId, youtubeUrl) {
            return $http({
                url: baseUrl + 'challenge/' + challengeId + '/add-youtube-url/',
                method: 'PUT',
                data: youtubeUrl,
                header: {'Content-Type': 'application/json'}
            })
        },

        confirmUploadedYoutubeUrl: function (challengeId) {
            return $http({
                url: baseUrl + 'challenge/' + challengeId + '/confirm-uploaded-youtube-url/',
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        addOrRemoveUserToChallengeUpvoters: function (data, id) {
            return $http({
                url: baseUrl + 'challenge/' + id + '/add-or-remove-user-to-challenge-upvoters/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        addOrRemovePointToCompletedChallenge: function (data, id) {
            return $http({
                url: baseUrl + 'challenge/' + id + '/add-or-remove-point-to-completed-challenge/',
                method: 'PUT',
                data: data,
                header: {'Content-Type': 'application/json'}
            })
        },

        assignPointsToUser: function (id) {
            return $http({
                url: baseUrl + 'challenge/' + id + '/assign-points-to-user/',
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        disapproveCurrentChallenge: function (id, data) {
            return $http({
                url: baseUrl + 'challenge/' + id + '/disapprove-challenge/',
                data: data,
                method: 'PUT',
                header: {'Content-Type': 'application/json'}
            })
        },

        checkIfChallengeIsUpvotedByUser: function (loggedInUserId, challengeId) {
            return $http({
                url: baseUrl + 'challenge/' + challengeId + '/checkifchallengeisupvotedbyuser/' + loggedInUserId + '/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })

        },

        getListOfUnapprovedChallenges: function () {
            return $http({
                url: baseUrl + 'challenges/unapproved/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        },

        getListOfCompletedChallenges: function() {
            return $http({
                url: baseUrl + 'challenges/completed/',
                method: 'GET',
                header: {'Content-Type': 'application/json'}
            })
        }
    }
});