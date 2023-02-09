package com.moonbox.beneficiaries.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private LocalDateTime createdBy;

    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;

    @LastModifiedBy
    private LocalDateTime lastUpdatedBy;
}
