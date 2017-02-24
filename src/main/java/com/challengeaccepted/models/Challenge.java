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
public class Challenge implements Serializable {

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
    private List<User> challengeUpvoters;
    @OneToOne
    @JoinColumn(name = "challenge_claimer_id")
    private User challengeClaimer;
    @OneToOne
    @JoinColumn(name = "challenge_creator_id")
    private User challengeCreator;

    public Challenge() {
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

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)

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

    public void addUserModelToChallengeUpvoters(User user) {
        if (challengeUpvoters == null) {
            challengeUpvoters = new ArrayList<User>();
        }
        challengeUpvoters.add(user);
    }

    public List<Long> getChallengeUpvoters() {
        List<Long> listOfChallengeUpvotersId = new ArrayList<Long>();
        for (User user : challengeUpvoters) {
            listOfChallengeUpvotersId.add(user.getId());
        }
        return listOfChallengeUpvotersId;
    }

    public void setChallengeUpvoters(List<User> challengeUpvoters) {
        this.challengeUpvoters = challengeUpvoters;
    }

    public void removeUserModelFromChallengeUpvoters(User user) {
        challengeUpvoters.remove(user);
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

    public User getChallengeClaimer() {
        return challengeClaimer;
    }

    public void setChallengeClaimer(User challengeClaimer) {
        this.challengeClaimer = challengeClaimer;
    }

    public User getChallengeCreator() {
        return challengeCreator;
    }

    public void setChallengeCreator(User challengeCreator) {
        this.challengeCreator = challengeCreator;
    }




}