package com.vfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Referee extends AbstractEntity{

    @Column(nullable = false)
    private int refereeExperience;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDate created;

    public Referee() {
        this.status = true;
        this.created = LocalDate.now();
    }

    public int getRefereeExperience() {
        return refereeExperience;
    }

    public void setRefereeExperience(int refereeExperience) {
        this.refereeExperience = refereeExperience;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referee referee = (Referee) o;
        return refereeExperience == referee.refereeExperience && status == referee.status && Objects.equals(country, referee.country) && Objects.equals(created, referee.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refereeExperience, country, status, created);
    }

    @Override
    public String toString() {
        return "Referee{" +
                "refereeExperience=" + refereeExperience +
                ", country='" + country + '\'' +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}
