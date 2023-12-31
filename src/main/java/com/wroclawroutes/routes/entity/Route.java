package com.wroclawroutes.routes.entity;

import com.wroclawroutes.users.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Size(min = 5, max = 250)
    private String description;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Integer distanceInMeters;
    private Integer timeInSeconds;
    private Boolean isPublic;
    @ManyToMany
    @JoinTable(
            name = "route_tags",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UserRouteSaved> usersSavedRoutes;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<UserRouteRating> ratings;
    @OneToMany(mappedBy = "route", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<RouteStep> steps;
}