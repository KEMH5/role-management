package user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import role.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private String id;

    @Column(
            name = "FIRST_NAME",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "LAST_NAME",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "EMAIL",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "PHONE_NUMBER",
            nullable = false,
            unique = true
    )
    private String phoneNumber;

    @Column(
            name = "PASSWORD",
            nullable = false,
            unique = true
    )
    private String password;

    @Column(
            name = "DATE_OF_BIRTH",
            nullable = false,
            unique = true
    )
    private LocalDate dateOfBirth;

    @Column(
            name = "ENABLED"
    )
    private boolean enabled;

    @CreatedDate
    @Column(
            name = "CREATED_Date",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdDate;

    @Column(
            name = "LOCKED"
    )
    private boolean locked;

    @LastModifiedDate
    @Column(
            name = "LAST_MODIFIED_DATE",
            insertable = false
    )
    private LocalDateTime lastModifiedDate;

    @Column(
            name = "IS_CREDENTIALS_EXPIRED"
    )
    private boolean expired;

    @Column(
            name = "IS_EMAIL_VERIFIED"
    )
    private boolean emailVerified;

    @Column(
            name = "CREDENTIALS_EXPIRED"
    )
    private boolean credentialsExpired;

    @Column(
            name = "IS_PHONE_VERIFIED"
    )
    private boolean phoneVerified;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},//When I create a User if the role doesn't exist it will be created
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USERS_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLES_ID")
            }
    )
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(this.roles)) {
            return List.of();
        }
        return this.roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }
}
