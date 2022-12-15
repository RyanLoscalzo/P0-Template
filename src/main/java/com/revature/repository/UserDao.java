package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

public class UserDao {
    
    public User getUserByUsername(String username){
        try(Connection connection = ConnectionUtil.createConnection()) {
            // Step 1: Create query as string
            String sql = "select * from users where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            // Step 2: Provide relvant info
            ps.setString(1, username);
            // Step 3: Execute the query
            ResultSet rs = ps.executeQuery();
            // Step 4: Handle the responses
            rs.next();
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new User();
        }
    }

    public User createUser(UsernamePasswordAuthentication registerRequest){
        try(Connection connection = ConnectionUtil.createConnection()) {
            // Step 1: Create query as string
            String sql = "insert into users values(default, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Step 2: Provide relvant info
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            // Step 3: Execute the query
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            // Step 4: Handle the responses
            User newUser = new User();
            rs.next();
            int newId = rs.getInt("id");
            newUser.setId(newId);
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            return newUser;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new User();
        }
    }

}
