package ch.guggi.restful;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.Session;
import com.google.gson.Gson;

import ch.guggi.models.App;
import ch.guggi.models.Rating;
import ch.guggi.models.Test;
import ch.guggi.models.User;
import ch.guggi.services.HibernateUtil;
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
	 * Schnittstelle zum App Model.
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
	
	@POST
	@Path("/app2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public App createApp2(App app){
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			session.save(app);
			tx.commit();
		}
		catch (Exception e) {
			System.out.println(e);
			return app;
		}
		finally {
			session.close();
		}
		return app;
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
		String json = gson.toJson(apps);
		session.close();
		return json;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("appTest")
	public List<App> getApp() {
		App app = new App();
		
		app.setAppID(7);
		app.setAppName("guggiapp");
		app.setAppScore(93);
		
		Rating rating = new Rating();
		rating.setRatingID(2);
		rating.setRatingNegComment("neg");
		rating.setRatingPosComment("pos");
		rating.setRatingScore(93);
		
		User user = new User();
		user.setUserID(1);
		user.setUserName("stefan");
		
		rating.setUser(user);
		
		Set<Rating> ratings = new HashSet<Rating>();
		ratings.add(rating);
		
		app.setRatings(ratings);
		
		List<App> apps = new ArrayList<App>();
		apps.add(app);
		apps.add(app);
		
		return apps;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("apps")
	public List<App> getApps() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<App>  apps = session.createCriteria(App.class).list();
		
		for (App a: apps) {
			System.out.println("ID: " + a.getAppID());
			for (Rating r: a.getRatings()) {
				System.out.println("rating id: " + r.getRatingID());
				System.out.println("user: " + r.getUser());
			}
			System.out.println("amount of ratings: " + a.getRatings().size());
		}
		
		return apps;
	}
	
	/***********************************************************************************************
	 * 
	 * 
	 * Schnittstelle zum Rating Model.
	 * 
	 * 
	 ************************************************************************************************/
	
	/*
	 * Speichert eine neues Rating
	 * 
	 * Test String: {"appName":"Name", "userOwnApp:"bla bla"}
	 * Hilfe: http://www.journaldev.com/3481/hibernate-save-vs-saveorupdate-vs-persist-vs-merge-vs-update-explanation-with-examples
	 * URL: 
	 */
	@POST
	@Path("/rating")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRating(RatingRequest rr){
		Session session = SessionFactoryService.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = session.beginTransaction();
			
			Session sess = HibernateUtil.getSessionFactory().openSession();
            User user =  (User) sess.get(User.class, rr.getUserId());
            
            System.out.println("User: " + user.getUserID());
			
			App app = (App) sess.get(App.class, rr.getAppId());
			
			System.out.println("App: " + app.getAppID());
			
			Rating rating = new Rating();
			rating.setUser(user);
			rating.setApp(app);
			rating.setRatingPosComment("Super");
			rating.setRatingScore(100);
			
			session.save(rating);
			tx.commit();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			session.close();
			System.out.println("ups!");
		}
		return Response.status(201).entity(" erstellt").build();
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
	 * Test String: 
	 * 
	 * URL: http://localhost:8080/RatingAppF/rest/Restful/user
	 * {"userName":"Stefan"}
	 */
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user){
		
		Session sess = HibernateUtil.getSessionFactory().openSession();
		Query query = sess.getNamedQuery("findUserByName");
		query.setString("custName", user.getUserName());
		int size = query.list().size();
		System.out.println("user with name: " + user.getUserName() + " exists " + size + "  times");
		if (size == 0) {
			org.hibernate.Transaction tx;
			try {
				tx = sess.beginTransaction();
				sess.save(user);
				tx.commit();
				return Response.status(201).entity("{\"ok\":\"User wurde eingefügt\"}").build();
			}
			catch (Exception e) {
				System.out.println(e);
				return Response.status(201).entity("{\"status\":\"failed\"}").build();
			}
			finally {
				sess.close();
			}
		}
		else return Response.status(404).entity("{\"failed\":\"User bereits vorhanden\"}").build();
	}
	@PUT
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User user){
		Session sess = HibernateUtil.getSessionFactory().openSession();
		
		
		
		Query query = sess.getNamedQuery("findUserByName");
		query.setString("custName", user.getUserName());
		
		
		int size = query.list().size();
		System.out.println("user with name: " + user.getUserName() + " exists " + size + "  times");
		if (size == 0) {
			org.hibernate.Transaction tx;
			try {
				tx = sess.beginTransaction();
				sess.save(user);
				tx.commit();
				return Response.status(201).entity("{\"ok\":\"User wurde eingefügt\"}").build();
			}
			catch (Exception e) {
				System.out.println(e);
				return Response.status(201).entity("{\"status\":\"failed\"}").build();
			}
			finally {
				sess.close();
			}
			
		}
		return Response.status(201).entity("{\"ok\":\"User wurde eingefügt\"}").build();
	}

	/***********************************************************************************************
	 * 
	 * 
	 * Test
	 * 
	 * http://localhost:8080/RatingAppF/rest/Restful/say
	 ************************************************************************************************/

	@GET // Test Connection
	@Produces(MediaType.APPLICATION_JSON)
	@Path("test")
	public Test test() {
		Test test = new Test();
		test.setFoo(7);
		test.setBar("bar");
		return test;
	}
	
	
	@GET // Test Connection
	@Produces(MediaType.TEXT_PLAIN)
	@Path("say")
	public String say() {
		return "Hello World RESTful Jersey!";
	}

}