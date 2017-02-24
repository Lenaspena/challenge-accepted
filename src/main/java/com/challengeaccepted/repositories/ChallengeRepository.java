package com.challengeaccepted.repositories;

import com.challengeaccepted.models.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> getByIsChallengeCompleted();

    List<Challenge> getByIsYoutubeVideoUploaded();

}