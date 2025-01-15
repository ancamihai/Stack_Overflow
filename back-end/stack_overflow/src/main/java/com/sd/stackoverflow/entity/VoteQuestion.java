package com.sd.stackoverflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name="vote_questions")
public class VoteQuestion {

    @Id
    @Column(name="vq_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteQuestionId;

    @ManyToOne()
    @JoinColumn(name = "q_id")
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="up_or_down")
    private int upOrDown;

    public VoteQuestion() {

    }

    public Long getVoteQuestionId() {
        return voteQuestionId;
    }

    public void setVoteQuestionId(Long voteQuestionId) {
        this.voteQuestionId = voteQuestionId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public VoteQuestion(Long voteQuestionId, Question question, User user, int upOrDown) {
        this.voteQuestionId = voteQuestionId;
        this.question = question;
        this.user = user;
        this.upOrDown = upOrDown;
    }
}
