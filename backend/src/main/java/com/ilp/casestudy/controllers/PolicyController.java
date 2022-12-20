package com.ilp.casestudy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilp.casestudy.db.MasterPolicyService;
import com.ilp.casestudy.db.PoliciesService;
import com.ilp.casestudy.db.UsersService;
import com.ilp.casestudy.models.MasterPolicy;
import com.ilp.casestudy.models.Policy;
import com.ilp.casestudy.models.User;

@RestController
@CrossOrigin
public class PolicyController {

    @Autowired
    private PoliciesService policyService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MasterPolicyService masterPolicyService;

    @GetMapping("/my_policies")
    public ResponseEntity<Object> getMyPolicies(@RequestParam String customerid) {
        List<Policy> policies = policyService.getPolicyByCustomerId(customerid);
        if (policies.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "No policies found");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/get_policy")
    public ResponseEntity<Object> getPolicy(@RequestParam String policyid) {
        Policy policy = policyService.getPolicyByPolicyId(policyid);
        if (policy == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Policy not found");
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(policy);
        }
    }

    @PostMapping("/add_policy")
    public ResponseEntity<Object> addPolicy(@RequestParam String customerid, @RequestParam String masterpolicyid) {
        Map<String, String> response = new HashMap<>();
        try {
            User user = usersService.getUserbyId(customerid);
            if (user == null) {
                response.put("message", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            MasterPolicy masterPolicy = masterPolicyService.getMasterPolicyById(masterpolicyid);
            Policy policy = new Policy(policyService.generatePolicyId(), masterPolicy.getName(),
                    masterPolicy.getSumAssured(), masterPolicy.getPremium(), masterPolicy.getTenure(), "01/12/2023",
                    user.getId());
            policyService.addPolicy(policy);
            response.put("message", "Policy added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/get_master_policies")
    public ResponseEntity<Object> getMasterPolicies() {
        return ResponseEntity.ok().body(masterPolicyService.getMasterPolicies());
    }

    @GetMapping("/get_master_policy")
    public ResponseEntity<Object> getMasterPolicy(@RequestParam String id) {
        MasterPolicy masterPolicy = masterPolicyService.getMasterPolicyById(id);
        if (masterPolicy == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Master policy not found");
            return ResponseEntity.ok().body(map);
        }
        return ResponseEntity.ok().body(masterPolicy);
    }
}
