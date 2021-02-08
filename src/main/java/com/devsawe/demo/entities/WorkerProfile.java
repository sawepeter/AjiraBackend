package com.devsawe.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "worker_profile")
public class WorkerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "worker_id")
    private Long workerId;

    @NotNull
    private String location;

    @NotNull
    private String age;

    @NotNull
    private String skill_name;

    @NotNull
    private String phone_number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker_id", nullable = false,insertable = false,updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "workerprofile", fetch = FetchType.LAZY)
    private Set<UserRatingModel> reviews;

    public WorkerProfile(Long workerId) {
    }

    public WorkerProfile(Long id, Long workerId, @NotNull String location, @NotNull String age, @NotNull String skill_name, @NotNull String phone_number, User user, Set<UserRatingModel> reviews) {
        this.id = id;
        this.workerId = workerId;
        this.location = location;
        this.age = age;
        this.skill_name = skill_name;
        this.phone_number = phone_number;
        this.user = user;
        this.reviews = reviews;
    }

    public Set<UserRatingModel> getReviews() {
        return reviews;
    }

    public void setReviews(Set<UserRatingModel> reviews) {
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
