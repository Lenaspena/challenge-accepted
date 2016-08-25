app.controller('CreateChallengeController', ['$scope', '$location', '$log', 'scopeService', 'challengeService',
    function ($scope, $location, $log, scopeService, challengeService) {

        /*
         Below code handles the creation of the new challenges.
         The code does not execute if no user is logged in.
         */
        $scope.getUserInputsFromCreateChallengeForm = function () {
            return JSON.stringify({
                'topic': $('#input-topic').val(),
                'description': $('#input-description').val(),
                'creationDate': null,
                'challengeClaimed': false,
                'youtubeVideoUploaded': false,
                'youtubeVideoCorrect': false,
                'challengeCompleted': false,
                'challengeDisapproved': false,
                'upvotes': 0
            });
        };

        $scope.createNewChallenge = function (form) {
            if (form.validate()) {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                challengeService.createNewChallenge($scope.getUserInputsFromCreateChallengeForm(), $scope.loggedInUser.id)
                    .success(function (response, status) {
                        if (status == 201) {
                            $log.info('challengeService.createNewChallenge() called. New challenge created and saved to the database!');
                            // Update the list of challenges after creation of the new challenge,
                            $location.path('/available-challenges');
                        } else {
                            $log.info('challengeService.createNewChallenge() called. Challenge ***NOT*** created. Fields was empty!');
                        }
                    })
                    .error(function (response) {
                        $log.error('challengeService.createNewChallenge() called. ***FAILED*** to create new challenge!');
                        $log.error(response);
                    });

                $('#form-create-new-challenge').each(function () {
                    this.reset();
                });
            }
        };


        //Form Validation Options.
        $scope.validationOptions = {
            rules: {
                inputTopic: {
                    required: true,
                    minlength: 5,
                    maxlength: 21
                },
                inputDescription: {
                    required: true,
                    minlength: 15,
                    maxlength: 144
                }
            },
            messages: {
                inputTopic: {
                    required: "You must enter a topic",
                    minlength: "Your topic must have a minimum length of 5 characters"
                },
                inputDescription: {
                    required: "You must enter a description",
                    minlength: "Your description must have a minimum length of 15 characters"
                }
            }
        };


    }]);