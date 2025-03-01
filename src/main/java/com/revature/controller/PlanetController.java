package com.revature.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;

import io.javalin.http.Context;
import io.javalin.validation.Validator;

public class PlanetController {
	
	private PlanetService pService = new PlanetService();

	public static Logger logger = LoggerFactory.getLogger(PlanetController.class);

	public void getAllPlanets(Context ctx) {
		try{
			ctx.json(pService.getAllPlanets()).status(200);
		} catch(SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void getPlanetByName(Context ctx) {
		
		User u = ctx.sessionAttribute("user");
		String planetName = ctx.pathParam("name");
		
		Planet p = pService.getPlanetByName(u.getUsername(), planetName);
		
		ctx.json(p).status(200);
	}

	public void getPlanetByID(Context ctx) {
		
		User u = ctx.sessionAttribute("user");
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		Planet p = pService.getPlanetById(u.getUsername(), planetId);
		
		ctx.json(p).status(200);
	}


	public void createPlanet(Context ctx) {
		
		Planet planetToBeCreated = ctx.bodyAsClass(Planet.class);
		User u = ctx.sessionAttribute("user");
		
		Planet createdPlanet = pService.createPlanet(u.getUsername(),planetToBeCreated);
		
		ctx.json(createdPlanet).status(201);
	}

	public void deletePlanet(Context ctx) {
		
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		pService.deletePlanetById(planetId);
		ctx.json("Planet successfully deleted").status(202);
	}
}
