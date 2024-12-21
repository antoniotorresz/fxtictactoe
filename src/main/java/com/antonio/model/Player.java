package com.antonio.model;

public class Player {

    private String name;
    private String role;
    private Integer score;
    
    public Player() {
        this.score = 0;
    }
    
    public Player(String name, String role, Integer score) {
        this.name = name;
        this.role = role;
        this.score = score;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player{");
        sb.append("name=").append(name);
        sb.append(", role=").append(role);
        sb.append(", score=").append(score);
        sb.append('}');
        return sb.toString();
    }


}
