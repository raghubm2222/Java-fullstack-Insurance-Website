package com.ilp.casestudy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ilp.casestudy.models.MasterPolicy;

@Service
public class MasterPoliciesDatabase {

    private Connection connection = null;

    public MasterPoliciesDatabase(){
        try{
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            createMasterPoliciesTable();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMasterPoliciesTable() throws Exception {
        String cmd = "CREATE TABLE masterpolicies (id int, name varchar(255), sumAssured double, premium double, tenure int)";
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


    //get all master policies from db

    public List<MasterPolicy> getMasterPolicies() throws Exception {
        String cmd = "SELECT * FROM masterpolicies";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            List<MasterPolicy> masterPolicies = new ArrayList<>();
            while(resultSet.next()){
                MasterPolicy masterPolicy = new MasterPolicy(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("sumAssured"),
                        resultSet.getDouble("premium"),
                        resultSet.getInt("tenure")
                );
               masterPolicies.add(masterPolicy);
            }
            return masterPolicies;
        }catch (Exception e) {
            throw e;
        }
    }


    public MasterPolicy getMasterPolicyById(String id) throws Exception {
        String cmd = "SELECT * FROM masterpolicies WHERE id = "+id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                MasterPolicy masterPolicy = new MasterPolicy(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("sumAssured"),
                        resultSet.getDouble("premium"),
                        resultSet.getInt("tenure")
                );
                return masterPolicy;
            }
            return null;
        }catch (Exception e) {
            throw e;
        }
    }


    public void addMasterPolicy(MasterPolicy masterPolicy) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO masterpolicies VALUES (?,?,?,?,?)");
        preparedStatement.setInt(1, masterPolicy.getId());
        preparedStatement.setString(2, masterPolicy.getName());
        preparedStatement.setDouble(3, masterPolicy.getSumAssured());
        preparedStatement.setDouble(4, masterPolicy.getPremium());
        preparedStatement.setInt(5, masterPolicy.getTenure());
        preparedStatement.execute();
    }

}