package com.vfa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vfa.enums.PlayerStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Player extends AbstractEntity{

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private PlayerStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Team team;

    public Player() {
        this.status = PlayerStatus.ACTIVE;
    }


    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(player.height, height) == 0 && weight == player.weight && status == player.status && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, weight, status, team);
    }

    @Override
    public String toString() {
        return "Player{" +
                "height=" + height +
                ", weight=" + weight +
                ", status=" + status +
                ", team=" + team +
                '}';
    }
}
