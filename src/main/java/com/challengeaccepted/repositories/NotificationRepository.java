package com.challengeaccepted.repositories;

import com.challengeaccepted.models.NotificationModel;
import com.challengeaccepted.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    ArrayList<NotificationModel> getByInteractedChallengeChallengeCreatorId(Long id);

    ArrayList<NotificationModel> getByInteractedChallengeChallengeClaimerId(Long id);

    ArrayList<NotificationModel> getByInteractedChallengeChallengeUpvotersId(Long id);

}