package com.challengeaccepted.controllers;

import com.challengeaccepted.models.Challenge;
import com.challengeaccepted.models.User;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChallengeControllerTest {

    private ChallengeService challengeService;
    private UserService userService;
    private Challenge challenge;
    private User user;
    private ArrayList<Long> userList;
    private User challengeCreator;

    @InjectMocks
    private ChallengeController challengeController;

    @Before
    public void setUp() throws Exception {
        challengeService = mock(ChallengeService.class);
        userService = mock(UserService.class);
        challenge = mock(Challenge.class);
        user = mock(User.class);
        userList = new ArrayList<>();
        challengeCreator = mock(User.class);

        when(challengeService.getChallengeFromDatabase(1L)).thenReturn(challenge);
        when(userService.getUserFromDatabase(1L)).thenReturn(user);
        when(challenge.getChallengeUpvoters()).thenReturn(userList);
        when(userService.getUserFromDatabase(user.getId())).thenReturn(user);
        when(challenge.getChallengeClaimer()).thenReturn(user);
        when(challenge.getChallengeCreator()).thenReturn(challengeCreator);
    }

    @Test
    public void testCreateChallenge() throws Exception {
        when(challenge.getTopic()).thenReturn("");
        when(challenge.getDescription()).thenReturn("");

        assertEquals(HttpStatus.NO_CONTENT, challengeController.createChallenge(challenge, new Random().nextLong()).getStatusCode());

        when(challenge.getTopic()).thenReturn("Topic");
        when(challenge.getDescription()).thenReturn("Description");

        assertEquals(HttpStatus.CREATED, challengeController.createChallenge(challenge, new Random().nextLong()).getStatusCode());
    }

    @Test
    public void testReadChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.readChallenge(1L, user.getId()).getStatusCode());

        when(challenge.getChallengeClaimed()).thenReturn(true);
    }

    @Test
    public void testReadChallenge_Should_Return_Status_Code_400() throws Exception {
        when(user.getId()).thenReturn(1L);
        when(user.getId()).thenReturn(2L);
        when(challenge.getChallengeCompleted()).thenReturn(false);
        when(challenge.getYoutubeVideoUploaded()).thenReturn(false);
        when(challenge.getChallengeClaimed()).thenReturn(true);

        assertEquals(HttpStatus.BAD_REQUEST, challengeController.readChallenge(1L, user.getId()).getStatusCode());
    }

    @Test
    public void testReadAllChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.readAllChallenges().getStatusCode());
    }

    @Test
    public void testReadAllCompletedChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.readAllCompletedChallenges().getStatusCode());
    }

    @Test
    public void testReadAllUnapprovedChallenges_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.readAllUnapprovedChallenges().getStatusCode());
    }

    @Test
    public void testUpdateChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.updateChallenge(new Challenge()).getStatusCode());
    }

    @Test
    public void testUpdateChallengeClaimer() throws Exception {
        when(user.getId()).thenReturn(1L);
        assertEquals(HttpStatus.OK, challengeController.updateChallengeClaimer(1L, user).getStatusCode());
        // Set the two mocked users to the same id, should return a bad request http status.

        when(challenge.getChallengeCreator().getId()).thenReturn(1L);
        assertEquals(HttpStatus.BAD_REQUEST, challengeController.updateChallengeClaimer(1L, user).getStatusCode());
    }

    @Test
    public void testAddYoutubeUrlToChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.addYoutubeUrlToChallenge(1L, "YouTubeURL").getStatusCode());
    }

    @Test
    public void testAssignPointsToUser_Should_Return_Status_Code_200() throws Exception {
        when(userService.getUserFromDatabase(challenge.getChallengeCreator().getId())).thenReturn(user);
        assertEquals(HttpStatus.OK, challengeController.assignPointsToUser(1L).getStatusCode());
    }

    @Test
    public void testDisapproveChallenge_Should_Return_Status_Code_200() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.disapproveChallenge(1L).getStatusCode());
    }

    @Test
    public void testConfirmUploadedYoutubeUrl() throws Exception {
        assertEquals(HttpStatus.OK, challengeController.confirmUploadedYoutubeUrl(1L).getStatusCode());
        when(challenge.getYoutubeVideoUploaded()).thenReturn(true);

        assertEquals(HttpStatus.BAD_REQUEST, challengeController.confirmUploadedYoutubeUrl(1L).getStatusCode());
    }

    @Test
    public void testAddOrRemovePointToCompletedChallenge_Should_Return_Status_Code_200() throws Exception {
        when(userService.getUserFromDatabase(user.getId())).thenReturn(user);
        when(challengeService.getChallengeFromDatabase(1L)).thenReturn(challenge);

        assertEquals(HttpStatus.OK, challengeController.addOrRemoveUserToChallengeUpvoters(1L, user).getStatusCode());
    }

    @Test()
    public void testAddOrRemoveUserToChallengeUpvoters_Should_Return_Status_Code_200() throws Exception {
        when(user.getId()).thenReturn(1L);
        when(userService.getUserFromDatabase(1L)).thenReturn(new User());

        assertEquals(HttpStatus.OK, challengeController.addOrRemovePointToCompletedChallenge(1L, user).getStatusCode());
    }

}