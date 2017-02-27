package com.challengeaccepted.controllers;

import com.challengeaccepted.models.Challenge;
import com.challengeaccepted.models.User;
import com.challengeaccepted.services.ChallengeService;
import com.challengeaccepted.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private User challengeCreator;
    private ChallengeRestController unitUnderTest;

    @Before
    public void before() throws Exception {
        challengeService = mock(ChallengeService.class);
        userService = mock(UserService.class);
        challenge = mock(Challenge.class);
        user = mock(User.class);
        challengeCreator = mock(User.class);
        unitUnderTest = new ChallengeRestController(challengeService, userService);
    }

    @Test
    public void createChallenge_Success() throws Exception {
        when(challenge.getTopic()).thenReturn("Topic");
        when(challenge.getDescription()).thenReturn("Description");

        ResponseEntity responseEntity = unitUnderTest.createChallenge(challenge, challengeCreator.getId());
        HttpStatus status = responseEntity.getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    public void createChallenge_Fail() {
        when(challenge.getDescription()).thenReturn(null);
        when(challenge.getTopic()).thenReturn(null);

        ResponseEntity responseEntity = unitUnderTest.createChallenge(challenge, challengeCreator.getId());
        HttpStatus status = responseEntity.getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, status);
    }

    @Test
    public void readChallenge_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.readChallenge(1L, user.getId()).getStatusCode());

        when(challenge.getChallengeClaimed()).thenReturn(true);
    }

    @Test
    public void readChallenge_ShouldReturn400() throws Exception {
        when(user.getId()).thenReturn(1L);
        when(user.getId()).thenReturn(2L);
        when(challenge.getChallengeCompleted()).thenReturn(false);
        when(challenge.getYoutubeVideoUploaded()).thenReturn(false);
        when(challenge.getChallengeClaimed()).thenReturn(true);

        assertEquals(HttpStatus.BAD_REQUEST, unitUnderTest.readChallenge(1L, user.getId()).getStatusCode());
    }

    @Test
    public void readAllChallenges_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.readAllChallenges().getStatusCode());
    }

    @Test
    public void readAllCompletedChallenges_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.readAllCompletedChallenges().getStatusCode());
    }

    @Test
    public void readAllUnapprovedChallenges_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.readAllUnapprovedChallenges().getStatusCode());
    }

    @Test
    public void updateChallenge_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.updateChallenge(new Challenge()).getStatusCode());
    }

    @Test
    public void updateChallengeClaimer() throws Exception {
        when(user.getId()).thenReturn(1L);
        assertEquals(HttpStatus.OK, unitUnderTest.updateChallengeClaimer(1L, user).getStatusCode());

        when(challenge.getChallengeCreator().getId()).thenReturn(1L);
        assertEquals(HttpStatus.BAD_REQUEST, unitUnderTest.updateChallengeClaimer(1L, user).getStatusCode());
    }

    @Test
    public void addYoutubeUrlToChallenge_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.addYoutubeUrlToChallenge(1L, "YouTubeURL").getStatusCode());
    }

    @Test
    public void assignPointsToUser_ShouldReturn200() throws Exception {
        when(userService.getUserFromDatabase(challenge.getChallengeCreator().getId())).thenReturn(user);
        assertEquals(HttpStatus.OK, unitUnderTest.assignPointsToUser(1L).getStatusCode());
    }

    @Test
    public void disapproveChallenge_ShouldReturn200() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.disapproveChallenge(1L).getStatusCode());
    }

    @Test
    public void confirmUploadedYoutubeUrl() throws Exception {
        assertEquals(HttpStatus.OK, unitUnderTest.confirmUploadedYoutubeUrl(1L).getStatusCode());
        when(challenge.getYoutubeVideoUploaded()).thenReturn(true);

        assertEquals(HttpStatus.BAD_REQUEST, unitUnderTest.confirmUploadedYoutubeUrl(1L).getStatusCode());
    }

    @Test
    public void addOrRemovePointToCompletedChallenge_ShouldReturn200() throws Exception {
        when(userService.getUserFromDatabase(user.getId())).thenReturn(user);
        when(challengeService.getChallengeFromDatabase(1L)).thenReturn(challenge);

        assertEquals(HttpStatus.OK, unitUnderTest.addOrRemoveUserToChallengeUpvoters(1L, user).getStatusCode());
    }

    @Test()
    public void addOrRemoveUserToChallengeUpvoters_ShouldReturn200() throws Exception {
        when(user.getId()).thenReturn(1L);
        when(userService.getUserFromDatabase(1L)).thenReturn(new User());

        assertEquals(HttpStatus.OK, unitUnderTest.addOrRemovePointToCompletedChallenge(1L, user).getStatusCode());
    }

}