package com.devsawe.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "experience_detail")
public class ExperienceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   /* @Column(name = "user_Id")
    private Long userId;*/

    @NotNull
    private String start_date;

    @NotNull
    private String end_date;

    @NotNull
    private String job_title;

    @NotNull
    private String company_name;

    @NotNull
    private String job_location_city;

    @NotNull
    private String job_location_country;

    @NotNull
    private String description;

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_Id", nullable = false,insertable = false,updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_location_city() {
        return job_location_city;
    }

    public void setJob_location_city(String job_location_city) {
        this.job_location_city = job_location_city;
    }

    public String getJob_location_country() {
        return job_location_country;
    }

    public void setJob_location_country(String job_location_country) {
        this.job_location_country = job_location_country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
