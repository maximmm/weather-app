package com.sotnim.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@OptimisticLocking
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "entity_created", updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "entity_updated")
    private LocalDateTime updated;

    @Version
    @Column(name = "entity_version")
    private Long version;

}