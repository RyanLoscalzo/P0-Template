package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() throws SQLException {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// Step 1: Create query as string
			String sql = "select * from planets";
			Statement statement = connection.createStatement();
			// Step 3: Execute the query
			ResultSet rs = statement.executeQuery(sql);
			// Step 4: Handle the responses
			List<Planet> planets = new ArrayList<>();
			while(rs.next()){
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: may need to do join to user owner
			
			// Step 1: Create query as string
			String sql = "select * from planets where name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
            ps.setString(1, planetName);
			
			
			// Step 3: Execute the query
            ResultSet rs = ps.executeQuery();
			// Step 4: Handle the responses
            rs.next();
            Planet planet = new Planet();
            planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
            return planet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Planet();
		}
	}

	public Planet getPlanetById(String username, int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: may need to do join to user owner
			
			// Step 1: Create query as string
			String sql = "select * from planets where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
            ps.setInt(1, planetId);
			
			
			// Step 3: Execute the query
            ResultSet rs = ps.executeQuery();
			// Step 4: Handle the responses
            rs.next();
            Planet planet = new Planet();
            planet.setId(rs.getInt(1));
			planet.setName(rs.getString(2));
			planet.setOwnerId(rs.getInt(3));
            return planet;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Planet();
		}
	}

	public Planet createPlanet(String username, Planet p) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// NOTE TO SELF: figure out use/point of username string
			
			// Step 1: Create query as string
			String sql ="insert into planets values(?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
			ps.setInt(1, p.getId());
			ps.setString(2, p.getName());
			ps.setInt(3, p.getOwnerId());
			// Step 3: Execute the query
			ps.execute();
			// Step 4: Handle the responses
			Planet newPlanet = new Planet();
			newPlanet.setId(p.getId());
			newPlanet.setName(p.getName());
			newPlanet.setOwnerId(p.getOwnerId());
			return newPlanet;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
            return new Planet();
		}

	}

	public void deletePlanetById(int planetId) {
		try (Connection connection = ConnectionUtil.createConnection()) {
			// Step 1: Create query as string
			String sql = "delete from planets where id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// Step 2: Provide relvant info
			ps.setInt(1, planetId);
			// Step 3: Execute the query
			int rowsAffected = ps.executeUpdate();
			// Step 4: Handle the responses
			System.out.println("Rows Affected: " + rowsAffected);
		} catch (SQLException e) {
			System.out.println(e.getMessage()); //good spot to add some logging
		}
	}
}
