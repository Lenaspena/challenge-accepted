package com.challengeaccepted.repositories;

import com.challengeaccepted.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    ArrayList<Notification> getByInteractedChallengeChallengeCreatorId(Long id);

    ArrayList<Notification> getByInteractedChallengeChallengeClaimerId(Long id);

    ArrayList<Notification> getByInteractedChallengeChallengeUpvotersId(Long id);

}