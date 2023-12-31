package com.wroclawroutes.routes.entity;

import com.wroclawroutes.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(	name = "users_liked_locations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_user_location_liked",
                        columnNames = {"user_id", "location_id"}
                )
        })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserLocationLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @NotNull
    private Boolean isLiked;
    @CreationTimestamp
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLocationLiked that = (UserLocationLiked) o;
        return Objects.equals(id, that.id) && Objects.equals(isLiked, that.isLiked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isLiked);
    }
}
