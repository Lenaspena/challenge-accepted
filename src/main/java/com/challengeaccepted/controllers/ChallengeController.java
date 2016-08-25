package com.challengeaccepted.controllers;

import com.challengeaccepted.loggers.HerokuLogger;
import com.challengeaccepted.loggers.HerokuLoggerException;
import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.models.UserModel;
import com.challengeaccepted.models.enums.Action;
import com.challengeaccepted.models.wrappers.NotificationInfo;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationController notificationController;

    @CrossOrigin
    @RequestMapping(value = "/challenge/create/challenge-creator/{challengeCreatorId}", method = RequestMethod.POST)
    public ResponseEntity createChallenge(@RequestBody ChallengeModel challengeModel, @PathVariable Long challengeCreatorId) throws HerokuLoggerException {

        if (challengeModel.getTopic().equals("") || challengeModel.getDescription().equals("")) {
            new HerokuLogger().writeToErrorLog("Frontend validation failed when creating a challenge with empty fields.");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        UserModel challengeCreator = userService.getUserFromDatabase(challengeCreatorId);
        challengeModel.setChallengeCreator(challengeCreator);

        challengeService.saveChallengeToDatabase(challengeModel);
        createAndSaveNotification(challengeCreator, challengeModel, new NotificationInfo(Action.CREATECHALLENGE));

        new HerokuLogger().writeToInfoLog("A challenge with id " + challengeModel.getId() + " has been created.");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/user/{loggedInUserId}", method = RequestMethod.GET)
    public ResponseEntity<ChallengeModel> readChallenge(@PathVariable Long id, @PathVariable Long loggedInUserId) {

        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        UserModel userModelFromDatabase = userService.getUserFromDatabase(loggedInUserId);

        return validateUserRestrictions(challenge, userModelFromDatabase);
    }

    private ResponseEntity<ChallengeModel> validateUserRestrictions(ChallengeModel challenge, UserModel userModelFromDatabase) {

        if (isChallengeUnavailableForUserNotSignedIn(challenge, userModelFromDatabase)) {
            return new ResponseEntity<ChallengeModel>(HttpStatus.BAD_REQUEST);
        }

        if (userModelFromDatabase == null && challenge.getChallengeCompleted()) {
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
        }

        if(challenge.getChallengeClaimed()) {
            if (isLoggedInUserTheCreatorAndIsVideoUploaded(challenge, userModelFromDatabase)) {
                return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
            }
            if (isLoggedInUserNotClaimerAndChallengeNotCompleted(challenge, userModelFromDatabase)) {
                return new ResponseEntity<ChallengeModel>(HttpStatus.BAD_REQUEST);
            }

        }

        if (challenge == null) {
            System.out.println("felhantering: challenge null");
            return new ResponseEntity<ChallengeModel>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("felhantering: allt okej");
            System.out.println("CHALLENGE ID: " + challenge.getId());
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
        }

    }

    private boolean isLoggedInUserNotClaimerAndChallengeNotCompleted(ChallengeModel challenge, UserModel userModelFromDatabase) {
        if (userModelFromDatabase.getId() != challenge.getChallengeClaimer().getId() && !challenge.getChallengeCompleted()) {
            System.out.println("felhantering: usermodel finns och är inte claimer");
            return true;
        }
        return false;
    }

    private boolean isLoggedInUserTheCreatorAndIsVideoUploaded(ChallengeModel challenge, UserModel userModelFromDatabase) {
        if ((userModelFromDatabase.getId() == challenge.getChallengeCreator().getId()) && challenge.getYoutubeVideoUploaded()) {
            return true;
        }
        return false;
    }

    private boolean isChallengeUnavailableForUserNotSignedIn(ChallengeModel challenge, UserModel userModelFromDatabase) {
        if (userModelFromDatabase == null && challenge.getChallengeClaimed() && !challenge.getChallengeCompleted()) {
            System.out.println("felhantering: usermodel null");
            return true;
        }
        return false;
    }


    @CrossOrigin
    @RequestMapping(value = "/challenges/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/completed/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllCompletedChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllCompletedChallengesFromDatabase(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenges/unapproved/", method = RequestMethod.GET)
    public ResponseEntity<List<ChallengeModel>> readAllUnapprovedChallenges() {
        return new ResponseEntity<List<ChallengeModel>>(challengeService.getAllUnapprovedChallengesFromDatabase(), HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/challenge/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> updateChallenge(@RequestBody ChallengeModel challengeModel) {
        challengeService.updateChallengeInDatabase(challengeModel);
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/update-challenge-claimer/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> updateChallengeClaimer(@PathVariable Long id, @RequestBody UserModel userModel) throws HerokuLoggerException {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);

        if (isChallengeCreatorSameAsChallengeClaimer(userModel, challengeModel)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        challengeModel.setChallengeClaimer(userModel);
        challengeModel.setChallengeClaimed(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        createAndSaveNotification(userModel, challengeModel, new NotificationInfo(Action.CLAIMCHALLENGE));
        new HerokuLogger().writeToInfoLog("The challenge with id " + challengeModel.getId() + " has successfully been claimed by the user with id " + userModel.getId());
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> addYoutubeUrlToChallenge(@PathVariable Long id, @RequestBody String youtubeUrl) throws HerokuLoggerException {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        challengeModel.setYoutubeURL(youtubeUrl);
        challengeModel.setYoutubeUrlProvided(true);
        challengeService.updateChallengeInDatabase(challengeModel);
        new HerokuLogger().writeToInfoLog("A Youtube link has successfully been added to a claimed challenge");
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/assign-points-to-user/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> assignPointsToUser(@PathVariable Long id) throws HerokuLoggerException {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        Double pointsToDistribute = challenge.getUpvotes();
        System.out.println(pointsToDistribute);

        challengeCreator.addCreatedChallengePoints(pointsToDistribute / 2);
        challengeCompleter.addCompletedChallengePoints(pointsToDistribute);
        challenge.addPoints(pointsToDistribute);

        updateChallengeToCompleted(challenge);
        challenge.setChallengeUpvoters(new ArrayList<UserModel>());

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
        challengeService.updateChallengeInDatabase(challenge);

        new HerokuLogger().writeToInfoLog("Points have been distributed to the challengecompleter, challengecreator and the challenge successfully");

        createAndSaveNotification(challengeCompleter, challenge, new NotificationInfo(Action.PERFORMEDCHALLENGE));

        return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/disapprove-challenge/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> disapproveChallenge(@PathVariable Long id, @RequestBody String notificationMessage) throws HerokuLoggerException {
        ChallengeModel challengeModel = challengeService.getChallengeFromDatabase(id);
        UserModel userThatHasFailedPerformedChallenge = challengeModel.getChallengeClaimer();

        challengeModel.setYoutubeURL(null);
        challengeModel.setChallengeClaimed(false);
        challengeModel.setChallengeClaimer(null);
        challengeModel.setYoutubeVideoUploaded(false);
        challengeModel.setYoutubeUrlProvided(false);
        challengeModel.setChallengeDisapproved(true);

        challengeService.updateChallengeInDatabase(challengeModel);

        NotificationInfo notificationInfo = new NotificationInfo(Action.FAILEDTOPERFORMECHALLENGE, notificationMessage);
        createAndSaveNotification(userThatHasFailedPerformedChallenge, challengeModel, notificationInfo);

        new HerokuLogger().writeToInfoLog("A challenge have been disapproved by the challengecreator successfully");
        return new ResponseEntity<ChallengeModel>(challengeModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/confirm-uploaded-youtube-url/", method = RequestMethod.PUT)
    public ResponseEntity<ChallengeModel> confirmUploadedYoutubeUrl(@PathVariable Long id) throws HerokuLoggerException {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        if (challenge.getYoutubeVideoUploaded() == false) {
            challenge.setYoutubeVideoUploaded(true);
            challenge.setYoutubeUrlProvided(false);

            challengeService.updateChallengeInDatabase(challenge);
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.OK);
        } else {
            new HerokuLogger().writeToInfoLog("A challenge claimer has confirmed the uploaded Youtube video");
            return new ResponseEntity<ChallengeModel>(challenge, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-user-to-challenge-upvoters/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemoveUserToChallengeUpvoters(@PathVariable Long id, @RequestBody UserModel loggedInUser) {
        UserModel user = userService.getUserFromDatabase(loggedInUser.getId());
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);

        if (challenge.getChallengeUpvoters().contains(user.getId())) {
            challenge.removeUserModelFromChallengeUpvoters(user);
            challenge.removeUpvotes(1.0);
        } else {
            challenge.addUpvotes(1.0);
            challenge.addUserModelToChallengeUpvoters(user);

            createAndSaveNotification(user, challenge, new NotificationInfo(Action.UPVOTECHALLENGE));
        }

        challengeService.updateChallengeInDatabase(challenge);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/challenge/{id}/add-or-remove-point-to-completed-challenge/", method = RequestMethod.PUT)
    public ResponseEntity addOrRemovePointToCompletedChallenge(@PathVariable Long id, @RequestBody UserModel loggedInUser) {
        ChallengeModel challenge = challengeService.getChallengeFromDatabase(id);
        UserModel user = userService.getUserFromDatabase(loggedInUser.getId());

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

    private boolean isChallengeCreatorSameAsChallengeClaimer(UserModel userModel, ChallengeModel challengeModel) throws HerokuLoggerException {
        if (challengeModel.getChallengeCreator() != null && userModel != null) {
            if (challengeModel.getChallengeCreator().getId().equals(userModel.getId())) {
                new HerokuLogger().writeToErrorLog("The challenge creator with id " + userModel.getId() + " is trying to claim his/her own challenge");
                return true;
            }
        }
        return false;
    }

    private void updateChallengeToCompleted(ChallengeModel challenge) {
        challenge.setChallengeCompleted(true);
        challenge.setYoutubeUrlProvided(false);
        challenge.setYoutubeVideoUploaded(false);
    }

    private void removePointsFromUsers(ChallengeModel challenge) {
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.removePoints(1.0);
        challengeCompleter.removeCompletedChallengePoint(1.0);
        challengeCreator.removeCreatedChallengePoint(0.5);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

    private void addPointsToUsers(ChallengeModel challenge) {
        UserModel challengeCompleter = userService.getUserFromDatabase(challenge.getChallengeClaimer().getId());
        UserModel challengeCreator = userService.getUserFromDatabase(challenge.getChallengeCreator().getId());

        challenge.addPoints(1.0);
        challengeCompleter.addCompletedChallengePoints(1.0);
        challengeCreator.addCreatedChallengePoints(0.5);

        userService.updateUserInDatabase(challengeCompleter);
        userService.updateUserInDatabase(challengeCreator);
    }

    private void createAndSaveNotification(UserModel user, ChallengeModel challenge, NotificationInfo notificationInfo) {
        NotificationModel notificationModel = new NotificationModel(user, challenge, notificationInfo);
        notificationController.createNotification(notificationModel);
    }

}