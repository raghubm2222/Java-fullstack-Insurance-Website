package com.ilp.casestudy.db;

import com.ilp.casestudy.models.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Service
public class UsersDatabase {

   private  Connection connection = null;

    public UsersDatabase(){
        try{
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            createUsersTable();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createUsersTable() throws Exception {
        String cmd = "CREATE TABLE users (id varchar(255), name varchar(255), email varchar(255), password varchar(255), address varchar(255), phone varchar(255), nominee varchar(255), relationship varchar(255))";
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

    public void addUser(User user) throws Exception {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getNominee());
            preparedStatement.setString(8, user.getRelationship());
            preparedStatement.execute();
    }

    public List<User>  getUsers()  {
        String cmd = "SELECT * FROM users";
        try {
            //read cmd in derby and return list of users
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("nominee"),
                        resultSet.getString("relationship")
                );
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Error while adding user");
            return List.of();
        }
    }

    //get user by email
    public  User getUserByEmail(String email) {
        String cmd = "SELECT * FROM users WHERE email = '"+email+"'";
        try {
            //read cmd in derby and return list of users
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            if(resultSet.next()){
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("nominee"),
                        resultSet.getString("relationship")
                );
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error while adding user");
            return null;
        }
    }


    //get user by id
    public  User getUserById(String id) {
        String cmd = "SELECT * FROM users WHERE id = '"+id+"'";
        try {
            //read cmd in derby and return list of users
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            if(resultSet.next()){
                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("nominee"),
                        resultSet.getString("relationship")
                );
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error while adding user");
            return null;
        }
    }
    
    public String generateId() {
        String cmd = "SELECT COUNT(*) FROM users";
        try {
            //read cmd in derby and return list of users
            ResultSet resultSet = connection.createStatement().executeQuery(cmd);
            if(resultSet.next()){
                return "CUST" + String.format("%06d", resultSet.getInt(1) + 1);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error while adding user");
            return null;
        }
    }
}