package com.ilp.casestudy.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ilp.casestudy.models.MasterPolicy;

@Service
public class MasterPolicyService {
    private List<MasterPolicy> masterPolicies;

    public MasterPolicyService() {
        masterPolicies = List.of(
                new MasterPolicy("1", "Term Insurance", 500000, 300, 8),
                new MasterPolicy("2", "Health Insurance", 100000, 200, 5),
                new MasterPolicy("3", "Car Insurance", 100000, 100, 3),
                new MasterPolicy("4", "Home Insurance", 200000, 200, 5),
                new MasterPolicy("5", "Travel Insurance", 50000, 100, 2));
    }

    public List<MasterPolicy> getMasterPolicies() {
        return masterPolicies;
    }

    public MasterPolicy getMasterPolicyById(String id) {
        return masterPolicies.stream().filter(policy -> policy.getId().equals(id)).findFirst().orElse(null);
    }

}
