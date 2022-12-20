package com.ilp.casestudy.models;

public class MasterPolicy {

    private String id;
    private String name;
    private double sumAssured;
    private double premium;
    private int tenure;

    public MasterPolicy(String id, String name, double sumAssured, double premium, int tenure) {
        this.id = id;
        this.name = name;
        this.sumAssured = sumAssured;
        this.premium = premium;
        this.tenure = tenure;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public double getPremium() {
        return premium;
    }

    public int getTenure() {
        return tenure;
    }

}
