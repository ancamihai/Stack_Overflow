package com.sd.stackoverflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question {

    @Id
    @Column(name="q_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="text", nullable = false)
    private String text;

    @Column(name="creation_date")
    private LocalDateTime creationDateTime;

    @Column(name="picture")
    private String picture;

    @ManyToMany
    @JoinTable(
            name = "questions_tags",
            joinColumns = @JoinColumn(name = "q_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Question() {
    }

    public Question(Long questionId, User user, String title, String text, LocalDateTime creationDateTime, String picture, Set<Tag> tags) {
        this.questionId = questionId;
        this.user = user;
        this.title = title;
        this.text = text;
        this.picture = picture;
        this.tags = tags;

        if (creationDateTime != null) {
            this.creationDateTime = creationDateTime;
        } else {
            prePersist();
        }
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @PrePersist
    public void prePersist() {
        this.creationDateTime = LocalDateTime.now();
    }
}
