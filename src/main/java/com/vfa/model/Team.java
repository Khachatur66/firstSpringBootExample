package com.vfa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "field is specified")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private int playersAmount;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private String teamOrigin;

    @Column(nullable = false)
    private LocalDate created;

    @OneToMany
    private List<Player> players;

    public Team() {
        this.status = true;
        this.created = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String teamName) {
        this.name = teamName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlayersAmount() {
        return playersAmount;
    }

    public void setPlayersAmount(int playersAmount) {
        this.playersAmount = playersAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTeamOrigin() {
        return teamOrigin;
    }

    public void setTeamOrigin(String teamOrigin) {
        this.teamOrigin = teamOrigin;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> player) {
        this.players = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && points == team.points && playersAmount == team.playersAmount && status == team.status && Objects.equals(name, team.name) && Objects.equals(teamOrigin, team.teamOrigin) && Objects.equals(created, team.created) && Objects.equals(players, team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, points, playersAmount, status, teamOrigin, created, players);
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name='" + name + '\'' + ", points=" + points + ", playersAmount=" + playersAmount + ", status=" + status + ", teamOrigin='" + teamOrigin + '\'' + ", created=" + created + ", player=" + players + '}';
    }
}
