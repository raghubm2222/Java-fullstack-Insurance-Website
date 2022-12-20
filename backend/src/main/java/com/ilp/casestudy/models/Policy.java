package com.ilp.casestudy.models;

public class Policy {
    private String policyId;
    private String policyName;
    private double sumAssured;
    private double premium;
    private int tenure;
    private String nextDue;
    private String customerId;

    public Policy(String policyId, String policyName, double sumAssured, double premium, int tenure, String nextDue,
            String customerId) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.sumAssured = sumAssured;
        this.premium = premium;
        this.tenure = tenure;
        this.nextDue = nextDue;
        this.customerId = customerId;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public String getNextDue() {
        return nextDue;
    }

    public void setNextDue(String nextDue) {
        this.nextDue = nextDue;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
