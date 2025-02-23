package com.polylvst.simplepos.domain.entities;

import com.polylvst.simplepos.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "cashier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;  // ADMIN | KASIR

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private UUID createdBy;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private UUID updatedBy;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role && Objects.equals(createdAt, user.createdAt) && Objects.equals(createdBy, user.createdBy) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(updatedBy, user.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, createdAt, createdBy, updatedAt, updatedBy);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
