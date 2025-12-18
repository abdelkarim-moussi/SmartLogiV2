package com.app.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn("user_id"),
            inverseJoinColumns = @JoinColumn("role_id"))
    private Set<Role> roles = new HashSet<>();
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

}
