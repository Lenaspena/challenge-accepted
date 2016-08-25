describe('Test user-controller.js', function () {

    // Get the module before each test and inject user-service.js
    beforeEach(module('ChallengeAccepted'));

    // Mock the user.
    var mockedUser = {id: 1, firstName: "Kalle"};

    // Inject controller and user service.
    var $controller, userService, $scope;
    beforeEach(inject(function (_$controller_, _userService_) {
        $controller = _$controller_;
        userService = _userService_;
    }));

    // Tests!
    describe('User Controller', function () {
        var controller;
        beforeEach(function () {
            $scope = {};
            controller = $controller('UserController', {$scope: $scope, userService: userService});
        });

        it('The controller should not be null', function () {
            expect(controller).not.toBeNull();
        });
    });

});