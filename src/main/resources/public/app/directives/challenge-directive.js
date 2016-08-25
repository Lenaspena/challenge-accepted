app.directive("directiveChallenge", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/challenge-profile.html'
    }
});

app.directive("directiveCreateChallenge", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/create-challenge.html'
    }
});

app.directive("directiveListChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-challenges.html'
    }
});

app.directive("directiveListCompletedChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-completed-challenges.html'
    }
});

app.directive("directiveListApproveVideos", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-approve-videos.html'
    }
});

app.directive("directiveListYourChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-your-challenges.html'
    }
});
app.directive("directiveChallengeProfile", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/challenge-profile.html'
    }
});

app.directive("directiveListUsers", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-users.html'
    }
});

app.directive("directiveListClaimedChallenges", function () {
    return {
        restrict: 'C',
        templateUrl: 'views/list-claimed-challenges.html'
    }
});

app.directive("directiveListChallengesNotLoggedIn", function() {
    return {
        restrict: 'C',
        templateUrl: 'views/list-challenges-not-logged-in.html'
    }
});

app.directive("directiveSearchChallenges", function() {
    return {
        restrict: 'C',
        templateUrl: 'views/search-challenge-directive.html'
    }
});