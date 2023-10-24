package com.eiben.Blog.App.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Entity
@Table(name = "p_like")
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date created_at;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date updated_at;

    @PrePersist
    protected void prePersist() {
        if (this.created_at == null) created_at = new Date();
        if (this.updated_at == null) updated_at = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updated_at = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updated_at = new Date();
    }
}
