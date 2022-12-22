package com.ilp.casestudy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ilp.casestudy.db.UsersDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilp.casestudy.db.MasterPoliciesDatabase;
import com.ilp.casestudy.db.PoliciesDatabase;
import com.ilp.casestudy.models.MasterPolicy;
import com.ilp.casestudy.models.Policy;
import com.ilp.casestudy.models.User;

@RestController
@CrossOrigin
public class PolicyController {

    @Autowired
    private PoliciesDatabase policyService;

    @Autowired
    private MasterPoliciesDatabase masterPoliciesDatabase;

    @Autowired
    private UsersDatabase usersDatabase;


    @GetMapping("/my_policies")
    public ResponseEntity<Object> getMyPolicies(@RequestParam String customerid) {
        List<Policy> policies = policyService.getPoliciesByCustomerId(customerid);
        if (policies.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "No policies found");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(policies);
    }


    @PostMapping("/add_policy")
    public ResponseEntity<Object> addPolicy(@RequestParam String customerid, @RequestParam String masterpolicyid) {
        Map<String, String> response = new HashMap<>();
        try {
            User user = usersDatabase.getUserById(customerid);
            if (user == null) {
                response.put("message", "User not found");
                return ResponseEntity.badRequest().body(response);
            }
            MasterPolicy masterPolicy = masterPoliciesDatabase.getMasterPolicyById(masterpolicyid);
            Policy policy = new Policy(policyService.generateId(), masterPolicy.getName(),
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
        try {
            List<MasterPolicy> masterPolicies = masterPoliciesDatabase.getMasterPolicies();
            if(masterPolicies.isEmpty()){
                Map<String, Object> response = new HashMap<>();
                response.put("message", "No master policies found");
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok().body(masterPolicies);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_master_policy")
    public ResponseEntity<Object> getMasterPolicy(@RequestParam String id) {
        try {
            MasterPolicy masterPolicy = masterPoliciesDatabase.getMasterPolicyById(id);
            if(masterPolicy == null){
                Map<String, Object> response = new HashMap<>();
                response.put("message", "No master policy found with id " + id);
                return ResponseEntity.badRequest().body(response);
            }
            return ResponseEntity.ok().body(masterPolicy);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add_master_policy")
    public ResponseEntity<Object> addMasterPolicy(@RequestParam int id,@RequestParam String name, @RequestParam double sumAssured, @RequestParam double premium, @RequestParam int tenure) {
        try {
            MasterPolicy masterPolicy = new MasterPolicy(id,name, sumAssured, premium, tenure);
            masterPoliciesDatabase.addMasterPolicy(masterPolicy);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Master Policy added successfully");
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
