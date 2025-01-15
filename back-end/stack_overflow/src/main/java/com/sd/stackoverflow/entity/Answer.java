package com.sd.stackoverflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @Column(name="a_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne()
    @JoinColumn(name = "q_id")
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="text", nullable = false)
    private String text;

    @Column(name="creation_date")
    private LocalDateTime creationDateTime;

    @Column(name="picture")
    private String picture;

    public Answer() {
    }

    public Answer(Long answerId, Question question, User user, String text, LocalDateTime creationDateTime, String picture) {
        this.answerId = answerId;
        this.question = question;
        this.user = user;
        this.text = text;
        this.creationDateTime = creationDateTime;
        this.picture = picture;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @PrePersist
    public void prePersist() {
        this.creationDateTime = LocalDateTime.now();
    }
}
