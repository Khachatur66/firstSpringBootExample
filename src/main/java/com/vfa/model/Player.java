package com.vfa.model;

import com.vfa.enums.PlayerStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Player extends AbstractEntity {

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private PlayerStatus status;

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

}


