package ru.mch.todo.entity;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.time.ZonedDateTime;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user", "categories"})
@EqualsAndHashCode(of = "id")
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_category",
            joinColumns = {@JoinColumn(name = "task")},
            inverseJoinColumns = {@JoinColumn(name = "category")}
    )
    private Set<Category> categories = new HashSet<>();

    public LocalDateTime getCreatedTimeZone(String userZone) {
        String defaultTimeZone = ZoneId.systemDefault().toString();
        ZonedDateTime utcTimeZone = ZonedDateTime.of(created, ZoneId.of(defaultTimeZone));
        return utcTimeZone.withZoneSameInstant(ZoneId.of(userZone)).toLocalDateTime();
    }
}