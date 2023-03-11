package com.moonbox.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@MappedSuperclass
public class BaseEntity {

    private final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @CreatedDate
    @JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @JsonFormat(shape = STRING)
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @JsonFormat(shape = STRING, pattern = DATE_TIME_PATTERN)
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @LastModifiedBy
    @JsonFormat(shape = STRING)
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
}
