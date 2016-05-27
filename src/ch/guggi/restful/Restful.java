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


	//	@POST
	//	@Path("/app")
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Response createApp(App app){
	//		Session session = SessionFactoryService.getSessionFactory().openSession();
	//		org.hibernate.Transaction tx;
	//		try {
	//			tx = session.beginTransaction();
	//			session.save(app);
	//			tx.commit();
	//		}
	//		catch (Exception e) {
	//			System.out.println(e);
	//			return Response.status(201).entity("{\"status\":\"failed\"}").build();
	//		}
	//		finally {
	//			session.close();
	//		}
	//		return Response.status(201).entity("{\"status\":\"ok\"}").build();
	//	}
	@POST
	@Path("/app")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(App app){
		Session sess = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		try {
			tx = sess.beginTransaction();
			sess.save(app);
			tx.commit();
			return Response.status(201).entity("{\"status\":\"ok\"}").build();
		}
		catch (Exception e) {
			System.out.println(e);
			return Response.status(201).entity("{\"status\":\"failed\"}").build();
		}
		finally {
			sess.close();
		}
	}



	/*
	 * Updated die App. Erwartet wir die App ID im Request
	 * 
	 * Beispiel: {   "appID": 22, "appName":5}
	 * URL: http://localhost:8080/RatingAppF/rest/Restful/app
	 */
//	@PUT
//	@Path("/app")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateApp(App tmp){
//		App app;
//		Session session = SessionFactoryService.getSessionFactory().openSession();
//		org.hibernate.Transaction tx;
//		try {
//			tx = session.beginTransaction();
//			app = (App) session.load(App.class, tmp.getAppId());
//			app = tmp;
//			System.out.println("Name: " + app.getAppName());
//			session.update(app);
//			tx.commit();
//		}
//		catch (Exception e) {
//			System.out.println(e);
//			return Response.status(500).entity("{\"failed\":\"unknown!\"}").build();
//		}
//		finally {
//			session.close();
//		}
//		return Response.status(200).entity("{\"status\":\"ok\"}").build();
//	}
	@PUT
	@Path("/app")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// uNIQUE!!!!!!!!!!!!!!!!!
	public Response App(App tmp){
		Session sess = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		App app = (App) sess.load(App.class, tmp.getAppId());
		
		app.setAppName(tmp.getAppName());
		tx = sess.beginTransaction();
		try {
			sess.save(app);
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(500).entity("{\"failed\":\"unknown!\"}").build();
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
	public Response DelApp(App tmp){
		Session sess = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx;
		tx = sess.beginTransaction();
		try {
			sess.delete(tmp);
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(500).entity("{\"failed\":\"unknown!\"}").build();
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
	@Path("apps2")
	public List<App> getApps() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<App>  apps = session.createCriteria(App.class).list();

		for (App a: apps) {
			System.out.println("ID: " + a.getAppId());
			for (Rating r: a.getRatings()) {
				System.out.println("rating id: " + r.getRatingID());
				System.out.println("user: " + r.getUser());
			}
			System.out.println("amount of ratings: " + a.getRatings().size());
		}

		return apps;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("apps5")
	public List<App> getApps2() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<App>  apps = session.createCriteria(App.class).list();

		for (App a: apps) {
			System.out.println("ID: " + a.getAppId());
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

			System.out.println("App: " + app.getAppId());

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
			app = (App) session.load(App.class, tmp.getAppId());

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

		Collections.sort(apps,Collections.reverseOrder());
		System.out.println("Sortiert: ");

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
				return Response.status(201).entity("{\"userId\":" +user.getUserID() +",\"status\":\"ok\"}").build();
			}
			catch (Exception e) {
				System.out.println(e);
				return Response.status(201).entity("{\"status\":\"failed\"}").build();
			}
			finally {
				sess.close();
			}
		}
		else return Response.status(404).entity("{\"status\":\"failed\"}").build();
	}
	@PUT
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(UpdateUser updateUser){
		Session sess = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Transaction tx;

		User user = (User) sess.get(User.class, updateUser.getUserID());
		user.setUserName(updateUser.getUserName());
		if (updateUser.getAppID()!=null){
			System.out.println("appID ist " + updateUser.getAppID());
			App app = (App) sess.get(App.class, updateUser.getAppID());
			user.setApp(app);
		}
		tx = sess.beginTransaction();
		try {
			sess.save(user);
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(500).entity("{\"failed\":\"unknown!\"}").build();
		}
		return Response.status(201).entity("{\"ok\":\"Updated!\"}").build();
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("apps")
	public Set<RatedApp> getRatedApps() {
		RatedApp myApp = new RatedApp();
		myApp.setAppId(80);
		myApp.setAppName("Stefan");
		myApp.setNumberOfRatings(1);
		myApp.setRatingAvg(4.5);
		RatedApp myApp2 = new RatedApp();
		myApp2.setAppId(77);
		myApp2.setAppName("Patrick");
		myApp2.setNumberOfRatings(1);
		myApp2.setRatingAvg(4.5);
		Set<RatedApp> ratedApp = new HashSet<RatedApp>();
		ratedApp.add(myApp);
		ratedApp.add(myApp2);
			

		return ratedApp;
	}

}