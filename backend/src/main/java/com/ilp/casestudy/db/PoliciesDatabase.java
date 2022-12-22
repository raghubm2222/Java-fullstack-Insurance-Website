package com.ilp.casestudy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ilp.casestudy.models.Policy;

@Service
public class PoliciesDatabase {

    private Connection connection = null;

    public PoliciesDatabase(){
        try{
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            createPoliciesTable();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPoliciesTable() throws Exception {
        String cmd = "CREATE TABLE policies (policyId varchar(255), policyName varchar(255), sumAssured double, premium double, tenure int, nextDue varchar(255), customerId varchar(255))";
        try{
            connection.createStatement().execute(cmd);
        }catch (Exception e) {
            if(e.getMessage().contains("already exists")){
                System.out.println("Table already exists");
                return;
            }
            throw e;
        }
    }


    public void addPolicy(Policy policy) throws Exception {
        String cmd = "INSERT INTO policies VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(cmd);
        preparedStatement.setString(1, policy.getPolicyId());
        preparedStatement.setString(2, policy.getPolicyName());
        preparedStatement.setDouble(3, policy.getSumAssured());
        preparedStatement.setDouble(4, policy.getPremium());
        preparedStatement.setInt(5, policy.getTenure());
        preparedStatement.setString(6, policy.getNextDue());
        preparedStatement.setString(7, policy.getCustomerId());
        preparedStatement.execute();
    }


    //get all policies from db by customer id
    public List<Policy> getPoliciesByCustomerId(String customerId) {
        String cmd = "SELECT * FROM policies WHERE customerId = ?";
        List<Policy> policies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Policy policy = new Policy(
                        resultSet.getString("policyId"),
                        resultSet.getString("policyName"),
                        resultSet.getDouble("sumAssured"),
                        resultSet.getDouble("premium"),
                        resultSet.getInt("tenure"),
                        resultSet.getString("nextDue"),
                        resultSet.getString("customerId")
                );
                policies.add(policy);
            }
            return policies;
        }catch (Exception e) {
            return policies;
        }
    }


    public String generateId() {
        String cmd = "SELECT COUNT(*) FROM policies";
        try {
            //read cmd in derby and return list of users
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            if(resultSet.next()){
                return "POL" + String.format("%07d", resultSet.getInt(1) + 1);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error while adding policies");
            return null;
        }
    }

}
