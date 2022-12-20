package com.ilp.casestudy.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ilp.casestudy.models.Policy;

@Service
public class PoliciesService {

    List<Policy> policies;

    public PoliciesService() {
        policies = new ArrayList<>();
    }

    public void addPolicy(Policy policy) {
        policies.add(policy);
    }

    public Policy getPolicyByPolicyId(String id) {
        return policies.stream().filter(policy -> policy.getPolicyId().equals(id)).findFirst().orElse(null);
    }

    // get policy by customer id
    public List<Policy> getPolicyByCustomerId(String id) {
        return policies.stream().filter(policy -> policy.getCustomerId().equals(id)).toList();
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public String generatePolicyId() {
        return "POL" + String.format("%06d", policies.size() + 1);
    }
}
