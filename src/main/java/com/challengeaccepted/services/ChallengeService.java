package com.challengeaccepted.services;

import com.challengeaccepted.models.Challenge;
import com.challengeaccepted.repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public void saveChallengeToDatabase(Challenge challenge) {
        challengeRepository.saveAndFlush(challenge);
    }

    public Challenge getChallengeFromDatabase(Long id) {
        return challengeRepository.findOne(id);
    }

    public List<Challenge> getAllChallengesFromDatabase() {
        return challengeRepository.findAll();
    }

    public void updateChallengeInDatabase(Challenge challengeToUpdate) {
        challengeRepository.save(challengeToUpdate);
    }

    public List<Challenge> getAllCompletedChallengesFromDatabase() {
        return challengeRepository.getByIsChallengeCompleted();
    }

    public List<Challenge> getAllUnapprovedChallengesFromDatabase() {
        return challengeRepository.getByIsYoutubeVideoUploaded();
    }
}