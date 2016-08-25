package com.challengeaccepted.repositories;

import com.challengeaccepted.models.ChallengeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<ChallengeModel, Long> {

    List<ChallengeModel> getByIsChallengeCompleted(boolean isChallengeCompleted);

    List<ChallengeModel> getByIsYoutubeVideoUploaded(boolean isYoutubeVideoUploaded);

}