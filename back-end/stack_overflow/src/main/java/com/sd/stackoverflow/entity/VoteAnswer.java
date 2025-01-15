package com.sd.stackoverflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name="vote_answers")
public class VoteAnswer {

    @Id
    @Column(name="va_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteAnswerId;

    @ManyToOne()
    @JoinColumn(name = "a_id")
    private Answer answer;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="up_or_down")
    private int upOrDown;

    public VoteAnswer() {
    }

    public VoteAnswer(Long voteAnswerId, Answer answer, User user, int upOrDown) {
        this.voteAnswerId = voteAnswerId;
        this.answer = answer;
        this.user = user;
        this.upOrDown = upOrDown;
    }

    public Long getVoteAnswerId() {
        return voteAnswerId;
    }

    public void setVoteAnswerId(Long voteAnswerId) {
        this.voteAnswerId = voteAnswerId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(int upOrDown) {
        this.upOrDown = upOrDown;
    }
}
