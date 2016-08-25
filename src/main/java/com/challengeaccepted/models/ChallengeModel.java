package com.challengeaccepted.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenges")
public class ChallengeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topic;
    private String description;
    private String youtubeURL;
    private LocalDateTime creationDate;
    private Double upvotes;
    private Double points;
    private Boolean isChallengeClaimed;
    private Boolean isYoutubeVideoUploaded;
    private Boolean isYoutubeUrlProvided;
    private Boolean isChallengeCompleted;
    private Boolean isChallengeDisapproved;
    @ManyToMany
    @JoinColumn(name = "challenge_upvoters_id")
    private List<UserModel> challengeUpvoters;
    @OneToOne
    @JoinColumn(name = "challenge_claimer_id")
    private UserModel challengeClaimer;
    @OneToOne
    @JoinColumn(name = "challenge_creator_id")
    private UserModel challengeCreator;

    public ChallengeModel() {
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Double upvotes) {
        this.upvotes = upvotes;
    }

    public void addUpvotes(Double upvotes) {
        if (this.upvotes == null) {
            this.upvotes = 0.0;
        }
        this.upvotes += upvotes;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public void addPoints(Double points) {
        if (this.points == null) {
            this.points = 0.0;
        }
        this.points += points;
    }

    public void removePoints(Double points) {
        this.points -= points;
    }

    public void removeUpvotes(Double upvotes) {
        this.upvotes -= upvotes;
    }

    public void addUserModelToChallengeUpvoters(UserModel userModel) {
        if (challengeUpvoters == null) {
            challengeUpvoters = new ArrayList<UserModel>();
        }
        challengeUpvoters.add(userModel);
    }

    public List<Long> getChallengeUpvoters() {
        List<Long> listOfChallengeUpvotersId = new ArrayList<Long>();
        for (UserModel user : challengeUpvoters) {
            listOfChallengeUpvotersId.add(user.getId());
        }
        return listOfChallengeUpvotersId;
    }

    public void setChallengeUpvoters(List<UserModel> challengeUpvoters) {
        this.challengeUpvoters = challengeUpvoters;
    }

    public void removeUserModelFromChallengeUpvoters(UserModel userModel) {
        challengeUpvoters.remove(userModel);
    }

    public Boolean getChallengeClaimed() {
        return isChallengeClaimed;
    }

    public void setChallengeClaimed(Boolean challengeClaimed) {
        isChallengeClaimed = challengeClaimed;
    }

    public Boolean getYoutubeVideoUploaded() {
        return isYoutubeVideoUploaded;
    }

    public void setYoutubeVideoUploaded(Boolean youtubeVideoUploaded) {
        isYoutubeVideoUploaded = youtubeVideoUploaded;
    }

    public Boolean isYoutubeUrlProvided() {
        return isYoutubeUrlProvided;
    }

    public void setYoutubeUrlProvided(Boolean youtubeUrlProvided) {
        isYoutubeUrlProvided = youtubeUrlProvided;
    }

    public Boolean getChallengeCompleted() {
        return isChallengeCompleted;
    }

    public void setChallengeCompleted(Boolean challengeCompleted) {
        isChallengeCompleted = challengeCompleted;
    }

    public Boolean getChallengeDisapproved() {
        return isChallengeDisapproved;
    }

    public void setChallengeDisapproved(Boolean challengeDisapproved) {
        isChallengeDisapproved = challengeDisapproved;
    }

    public UserModel getChallengeClaimer() {
        return challengeClaimer;
    }

    public void setChallengeClaimer(UserModel challengeClaimer) {
        this.challengeClaimer = challengeClaimer;
    }

    public UserModel getChallengeCreator() {
        return challengeCreator;
    }

    public void setChallengeCreator(UserModel challengeCreator) {
        this.challengeCreator = challengeCreator;
    }




}