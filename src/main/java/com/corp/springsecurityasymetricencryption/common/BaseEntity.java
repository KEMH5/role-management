package com.corp.springsecurityasymetricencryption.common;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private String id;

    @CreatedDate
    @Column(
            name = "CREATED_Date",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdDate;


    @LastModifiedDate
    @Column(
            name = "LAST_MODIFIED_DATE",
            insertable = false
    )
    private LocalDateTime lastModifiedDate;

    @Column(
            name = "CREATED_BY",
            nullable = false,
            updatable = false
    )
    private String createdBy;

    @Column(
            name = "LAST_MODIFIED_BY",
            insertable = false
    )
    private String lastModifiedBy;
}
