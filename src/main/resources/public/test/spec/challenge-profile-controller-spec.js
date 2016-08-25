describe('Test challenge-profile-controller.js', function () {

    // Get the module before each test and inject user-service.js
    beforeEach(module('ChallengeAccepted'));

    // Inject controller and user service.
    var $controller, scopeService, challengeService, userService, $scope;
    beforeEach(inject(function (_$controller_, _scopeService_, _challengeService_, _userService_) {
        $controller = _$controller_;
        scopeService = _scopeService_;
        challengeService = _challengeService_;
        userService = _userService_;
    }));

    // Tests!
    describe('Challenge Profile Controller', function () {
        var controller;
        beforeEach(function () {
            $scope = {};
            controller = $controller('ChallengeProfileController', {$scope: $scope, scopeService: scopeService, challengeService: challengeService});
        });

        it('The controller should not be null', function () {
            expect(controller).not.toBeNull();
        });

        it('Should return correct YouTube url', function(){
            expect($scope.convertToYouTubeEmbedUrl("https://www.youtube.com/watch?v=jZViOEv90dI")).toEqual('https://www.youtube.com/embed/jZViOEv90dI');
        })
    });

});