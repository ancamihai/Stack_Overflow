package com.sd.stackoverflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name="salts")
public class Salt {

    @Id
    @Column(name="s_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saltId;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="salt",nullable = false)
    private String salt;

    public Salt() {
    }

    public Salt(Long saltId, User user, String salt) {
        this.saltId = saltId;
        this.user = user;
        this.salt = salt;
    }

    public Long getSaltId() {
        return saltId;
    }

    public void setSaltId(Long saltId) {
        this.saltId = saltId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
