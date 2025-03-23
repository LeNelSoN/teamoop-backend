package com.edj.teamoop.kernel;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AbstractAuditingEntity<T> implements Serializable {

    public abstract T getId();

    @CreatedDate
    @Column(updatable = false)
    private Instant created = Instant.now();

    @LastModifiedDate
    private Instant lastModified = Instant.now();
}
