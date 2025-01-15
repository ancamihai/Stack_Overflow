package com.sd.stackoverflow.entity;

public class UserScoreDTO {

    private Long userId;
    private Float score;

    public UserScoreDTO() {
    }

    public UserScoreDTO(Long userId, Float score) {
        this.userId = userId;
        this.score = score;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
