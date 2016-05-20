package ch.guggi.restful;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.google.gson.Gson;

import ch.guggi.models.App;
import ch.guggi.models.User;
import ch.guggi.services.SessionFactoryService;

/*
 * Hilfe im Web: JSon / POJOs: http://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
 * *
 */

@Path("/Restful")
public class Restful {

	static HashMap<Integer, App> apps =
			new HashMap<Integer, App>();
	private List<App> appList = new ArrayList<App>();

/***********************************************************************************************
 * 
 * 
 * Schnittstelle zum App Model
 * 
 * 
 ************************************************************************************************/
	
		
	/*
	 * Speichert eine neue APP
	 * 
	 * Test String: {"appID":"12345","appName":"Name"}
	 * Hilfe: http://www.journaldev.com/3481/hibernate-save-vs-saveorupdate-vs-persist-vs-merge-vs-update-explanation-with-examples
	 * URL: http://localhost:8080/RatingAppF/rest/Restful/app
	 * 
	 */
	@POST
	@Path("/app")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createApp(App app){
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			session.save(app);
			tx.commit();
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.status(201).entity("{\"status\":\"failed\"}").build();
		}
		finally {
			session.close();
		}
		return Response.status(201).entity("{\"status\":\"ok\"}").build();
	}
	
	
	/*
	 * Updated die App. Erwartet wir die App ID im Request
	 * 
	 * Beispiel: {   "appID": 22, "appScore":5}
	 * URL: http://localhost:8080/RatingAppF/rest/Restful/app
	 */
	
	@PUT
	@Path("/app")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateApp(App tmp){
		App app;
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			app = (App) session.load(App.class, tmp.getAppID());
			app = tmp;
			System.out.println("Name: " + app.getAppName());
			session.update(app);
			tx.commit();
			}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			session.close();
		}
		return Response.status(200).entity("{\"status\":\"ok\"}").build();
	}
	/*
	 * Deleted eine neue APP
	 * 
	 * Hilfe: http://www.journaldev.com/3481/hibernate-save-vs-saveorupdate-vs-persist-vs-merge-vs-update-explanation-with-examples
	 * URL: 
	 * 
	 */
	@DELETE
	@Path("/app")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delApp(App app){
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			session.delete(app);
			tx.commit();
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"status\":\"failed\"}").build();
		}
		finally {
			session.close();
		}
		return Response.status(200).entity("{\"status\":\"ok\"}").build();
	}
	
		

	/*
	 * Speichert die Punktzahlen. Erwartet wir die App ID im Request
	 * 
	 * Test URL: http://localhost:8080/RatingAppF/rest/Restful/saveScore
	 * URL: http://group-fitness.ch/RatingAppF/rest/Restful/saveScore
	 * Beispiel: {   "appID": 22, "appScore":5}
	 */
	
	@PUT
	@Path("/saveScore")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveScore(App tmp){
		App app;
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			app = (App) session.load(App.class, tmp.getAppID());
			app.setAppScore(tmp.getAppScore()+app.getAppScore());
			session.save(app);
			tx.commit();
			}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			session.close();
		}
		return Response.status(200).entity("{\"status\":\"ok\"}").build();
	}
	
		
	
	/*
	 * Testet die Verbindung (Spielplatz)
	 * 
	 * http://localhost:8080/RatingAppF/rest/Restful/say
	 */
	@GET // Test Connection
	@Produces(MediaType.TEXT_PLAIN)
	@Path("say")
	public String say() {
		return "Hello World RESTful Jersey!";
	}


	/*
	 * Gibt die Liste alle gespeicherten Apps zurück.
	 * 
	 * List of App
	 * 
	 * Beispiel:
	 * Test String: http://localhost:8080/RatingAppF/rest/Restful/getAppNames
	 * 
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAppNames")
	public String getAppNames() {
		Gson gson = new Gson();
		Session session = SessionFactoryService.getSessionFactory().openSession();
		List<App> apps = session.createQuery("FROM App").list();
		session.close();
		String json = gson.toJson(apps);
		return json;
	}


	/*
	 * Liefert eine sortierte Liste mit der besten App am Anfang
	 * 
	 * Test String: http://localhost:8080/RatingAppF/rest/Restful/getBestApp
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getBestApp")
	public String getRanking() {
		Gson gson = new Gson();
		Session session = SessionFactoryService.getSessionFactory().openSession();
		List<App> apps = session.createQuery("FROM App").list();
		session.close();
		System.out.println("Unsortiert: ");
		for (App app : apps) {
			System.out.println(app.getAppScore());
		}
		Collections.sort(apps,Collections.reverseOrder());
		System.out.println("Sortiert: ");
		for (App app : apps) {
			System.out.println(app.getAppScore());
		}
		String json = gson.toJson(apps);
		return json;
	}


	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}
	
	
	/***********************************************************************************************
	 * 
	 * 
	 * Schnittstelle zum User Model
	 * 
	 * *********************************************************************************************
	 */
	/*
	 * Speichert eine neue User
	 * 
	 * Test String: {"appName":"Name", "userOwnApp:"bla bla"}
	 * Hilfe: http://www.journaldev.com/3481/hibernate-save-vs-saveorupdate-vs-persist-vs-merge-vs-update-explanation-with-examples
	 * URL: 
	 */
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user){
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			session.save(user);
			System.out.println("**************************");System.out.println("**************************");System.out.println("**************************");System.out.println("**************************");System.out.println("**************************");System.out.println("**************************");
			System.out.println(user.getUserName());
			tx.commit();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			session.close();
		}
		return Response.status(201).entity(user.getUserName()+" erstellt").build();
	}

}