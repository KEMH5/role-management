package com.corp.springsecurityasymetricencryption.role;

import com.corp.springsecurityasymetricencryption.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.corp.springsecurityasymetricencryption.user.User;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ROLES")
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
