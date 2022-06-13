package com.vfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Coach extends AbstractEntity{

    @JoinColumn(nullable = false)
    @OneToOne
    private Team team;

    @Column(nullable = false)
    private int coachExperience;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDate created;

    public Coach() {
        super();
        this.status = true;
        this.created = LocalDate.now();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public int getCoachExperience() {
        return coachExperience;
    }

    public void setCoachExperience(int coachExperience) {
        this.coachExperience = coachExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return coachExperience == coach.coachExperience && status == coach.status && Objects.equals(team, coach.team) && Objects.equals(created, coach.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, coachExperience, status, created);
    }

    @Override
    public String toString() {
        return "Coach{" +
                "team=" + team +
                ", coachExperience=" + coachExperience +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}
