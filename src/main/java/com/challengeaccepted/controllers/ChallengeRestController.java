package com.challengeaccepted.controllers;

import com.challengeaccepted.models.Challenge;
import com.challengeaccepted.models.User;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChallengeRestController {

    private final ChallengeService challengeService;
    private final UserService userService;

    @Autowired
    public ChallengeRestController(ChallengeService challengeService, UserService userService) {
        this.challengeService = challengeService;
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/create/challenge-creator/{challengeCreatorId}", method = RequestMethod.POST)
    public ResponseEntity createChallenge(@RequestBody Challenge challenge, @PathVariable Long challengeCreatorId) {

        if(challenge.getTopic() != null || challenge.getDescription() != null) {
            if (!challenge.getTopic().isEmpty() || !challenge.getDescription().isEmpty()) {
                User challengeCreator = userService.getUserFromDatabase(challengeCreatorId);
                challenge.setChallengeCreator(challengeCreator);

                challengeService.saveChallengeToDatabase(challenge);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/user/{loggedInUserId}", method = RequestMethod.GET)
    public ResponseEntity<Challenge> readChallenge(@PathVariable Long id, @PathVariable Long loggedInUserId) {

        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        User userFromDatabase = userService.getUserFromDatabase(loggedInUserId);

        return validateUserRestrictions(challenge, userFromDatabase);
    }

    private ResponseEntity<Challenge> validateUserRestrictions(Challenge challenge, User userFromDatabase) {

        if (isChallengeUnavailableForUserNotSignedIn(challenge, userFromDatabase)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userFromDatabase == null && challenge.getChallengeCompleted()) {
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        }

        if(challenge.getChallengeClaimed()) {
            if (isLoggedInUserTheCreatorAndIsVideoUploaded(challenge, userFromDatabase)) {
                return new ResponseEntity<>(challenge, HttpStatus.OK);
            }
            if (isLoggedInUserNotClaimerAndChallengeNotCompleted(challenge, userFromDatabase)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

        return new ResponseEntity<>(challenge, HttpStatus.OK);

    }

    private boolean isLoggedInUserNotClaimerAndChallengeNotCompleted(Challenge challenge, User userFromDatabase) {
        if (!userFromDatabase.getId().equals(challenge.getChallengeClaimer().getId()) && !challenge.getChallengeCompleted()) {
            return true;
        }
        return false;
    }

    private boolean isLoggedInUserTheCreatorAndIsVideoUploaded(Challenge challenge, User userFromDatabase) {
        return (userFromDatabase.getId().equals(challenge.getChallengeCreator().getId())) && challenge.getYoutubeVideoUploaded();
    }

    private boolean isChallengeUnavailableForUserNotSignedIn(Challenge challenge, User userFromDatabase) {
        if (userFromDatabase == null && challenge.getChallengeClaimed() && !challenge.getChallengeCompleted()) {
            return true;
        }
        return false;
    }


    @CrossOrigin
    @RequestMapping(value = "/challenges/", method = RequestMethod.GET)
    public ResponseEntity<List<Challenge>> readAllChallenges() {
        return new ResponseEntity<>(challengeService.getAllChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/completed/", method = RequestMethod.GET)
    public ResponseEntity<List<Challenge>> readAllCompletedChallenges() {
        return new ResponseEntity<>(challengeService.getAllCompletedChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/unapproved/", method = RequestMethod.GET)
    public ResponseEntity<List<Challenge>> readAllUnapprovedChallenges() {
        return new ResponseEntity<>(challengeService.getAllUnapprovedChallengesFromDatabase(), HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/challenge/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> updateChallenge(@RequestBody Challenge challenge) {
        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/update-challenge-claimer/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> updateChallengeClaimer(@PathVariable Long id, @RequestBody User user) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);

        if (isChallengeCreatorSameAsChallengeClaimer(user, challenge)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        challenge.setChallengeClaimer(user);
        challenge.setChallengeClaimed(true);
        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> addYoutubeUrlToChallenge(@PathVariable Long id, @RequestBody String youtubeUrl) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        challenge.setYoutubeURL(youtubeUrl);
        challenge.setYoutubeUrlProvided(true);
        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/assign-points-to-user/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> assignPointsToUser(@PathVariable Long id) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        User challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        User challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        Double pointsToDistribute = challenge.getUpvotes();

        challengeCreator.addCreatedChallengePoints(pointsToDistribute / 2);
        challengeCompleter.addCompletedChallengePoints(pointsToDistribute);
        challenge.addPoints(pointsToDistribute);

        updateChallengeToCompleted(challenge);
        challenge.setChallengeUpvoters(new ArrayList<>());

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
        challengeService.updateChallengeInDatabase(challenge);

        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/disapprove-challenge/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> disapproveChallenge(@PathVariable Long id) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        User userThatHasFailedPerformedChallenge = challenge.getChallengeClaimer();

        challenge.setYoutubeURL(null);
        challenge.setChallengeClaimed(false);
        challenge.setChallengeClaimer(null);
        challenge.setYoutubeVideoUploaded(false);
        challenge.setYoutubeUrlProvided(false);
        challenge.setChallengeDisapproved(true);

        challengeService.updateChallengeInDatabase(challenge);

        return new ResponseEntity<>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/confirm-uploaded-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<Challenge> confirmUploadedYoutubeUrl(@PathVariable Long id) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        if (!challenge.getYoutubeVideoUploaded()) {
            challenge.setYoutubeVideoUploaded(true);
            challenge.setYoutubeUrlProvided(false);

            challengeService.updateChallengeInDatabase(challenge);
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(challenge, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-user-to-challenge-upvoters/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemoveUserToChallengeUpvoters(@PathVariable Long id, @RequestBody User loggedInUser) {
        User user = userService.getUserFromDatabase(loggedInUser.getId());
        Challenge challenge = challengeService.getChallengeFromDatabase(id);

        if (challenge.getChallengeUpvoters().contains(user.getId())) {
            challenge.removeUserModelFromChallengeUpvoters(user);
            challenge.removeUpvotes(1.0);
        } else {
            challenge.addUpvotes(1.0);
            challenge.addUserModelToChallengeUpvoters(user);
        }

        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-point-to-completed-challenge/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemovePointToCompletedChallenge(@PathVariable Long id, @RequestBody User loggedInUser) {
        Challenge challenge = challengeService.getChallengeFromDatabase(id);
        User user = userService.getUserFromDatabase(loggedInUser.getId());

        if (challenge.getChallengeUpvoters().contains(user.getId())) {
            challenge.removeUserModelFromChallengeUpvoters(user);
            removePointsFromUsers(challenge);
        } else {
            challenge.addUserModelToChallengeUpvoters(user);
            addPointsToUsers(challenge);
        }

        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean isChallengeCreatorSameAsChallengeClaimer(User user, Challenge challenge) {
        if (challenge.getChallengeCreator() != null && user != null) {
            if (challenge.getChallengeCreator().getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    private void updateChallengeToCompleted(Challenge challenge) {
        challenge.setChallengeCompleted(true);
        challenge.setYoutubeUrlProvided(false);
        challenge.setYoutubeVideoUploaded(false);
    }

    private void removePointsFromUsers(Challenge challenge) {
        User challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        User challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.removePoints(1.0);
        challengeCompleter.removeCompletedChallengePoint(1.0);
        challengeCreator.removeCreatedChallengePoint(0.5);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

    private void addPointsToUsers(Challenge challenge) {
        User challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        User challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.addPoints(1.0);
        challengeCompleter.addCompletedChallengePoints(1.0);
        challengeCreator.addCreatedChallengePoints(0.5);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

}