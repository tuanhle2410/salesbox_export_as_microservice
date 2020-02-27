package com.salesbox.entity;

import javax.persistence.*;

/**
 * Created by tuantx on 12/4/2015.
 */
@Entity
@Table(name = "user_metadata")
public class UserMetadata extends BaseEntity
{

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserMetadata() {
    }

    public UserMetadata(User user, String key, Double value) {
        this.user = user;
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
