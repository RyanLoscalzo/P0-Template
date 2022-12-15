package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() throws SQLException{
		try (Connection connection = ConnectionUtil.createConnection()){
			// Step 1: Create query as string
			String sql = "select * from moons";
			Statement statement = connection.createStatement();
			// Step 3: Execute the query
			ResultSet rs = statement.executeQuery(sql);
			// Step 4: Handle the responses
			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		}
	}

	public Moon getMoonByName(String username, String moonName) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: may need to do join to username
			
			// Step 1: Create query as string
			String sql = "select * from moons where name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
            ps.setString(1, moonName);
			
			
			// Step 3: Execute the query
            ResultSet rs = ps.executeQuery();
			// Step 4: Handle the responses
            rs.next();
            Moon moon = new Moon();
            moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
            return moon;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Moon();
		}
	}

	public Moon getMoonById(String username, int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: may need to do join to username
			
			// Step 1: Create query as string
			String sql = "select * from moons where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
            ps.setInt(1, moonId);
			
			
			// Step 3: Execute the query
            ResultSet rs = ps.executeQuery();
			// Step 4: Handle the responses
            rs.next();
            Moon moon = new Moon();
            moon.setId(rs.getInt(1));
			moon.setName(rs.getString(2));
			moon.setMyPlanetId(rs.getInt(3));
            return moon;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Moon();
		}
	}

	public Moon createMoon(String username, Moon m) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: figure out use/point of username string
			
			// Step 1: Create query as string
			String sql ="insert into moons values(?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
			ps.setInt(1, m.getId());
			ps.setString(2, m.getName());
			ps.setInt(3, m.getMyPlanetId());
			// Step 3: Execute the query
			ps.execute();
			// Step 4: Handle the responses
			Moon newMoon = new Moon();
			newMoon.setId(m.getId());
			newMoon.setName(m.getName());
			newMoon.setMyPlanetId(m.getMyPlanetId());
			return newMoon;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Moon();
		}
	}

	public void deleteMoonById(int moonId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// Step 1: Create query as string
			String sql = "delete from moons where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
			ps.setInt(1, moonId);
			// Step 3: Execute the query
			int rowsAffected = ps.executeUpdate();
			// Step 4: Handle the responses
			System.out.println("Rows Affected: " + rowsAffected);
		} catch (SQLException e) {
			System.out.println(e.getMessage()); //good spot to add some logging
		}
	}

	public List<Moon> getMoonsFromPlanet(int planetId) throws SQLException{
		try (Connection connection = ConnectionUtil.createConnection()){
			// Step 1: Create query as string
			String sql = "select * from moons where myplanetid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
			ps.setInt(1, planetId);
			// Step 3: Execute the query
			ResultSet rs = ps.executeQuery();
			// Step 4: Handle the responses
			List<Moon> moons = new ArrayList<>();
			while(rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		}
	}
}
