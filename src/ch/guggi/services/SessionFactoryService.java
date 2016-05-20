package ch.guggi.services;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/*
 * 
 * Service für die Connection zur DB.
 * 
 * Konfig unter hibernate.cfg.xml
 * 
 * Singelton
 * 
 * 
 */

public class SessionFactoryService {

	private static final SessionFactoryService INSTANCE = new SessionFactoryService(); 

	/*
	 * Darf nicht von extern isntanziert werden
	 */
	private SessionFactoryService(){

		// Empty
	}

	/*
	 * Liefert die Instance (Singelton)
	 * 
	 */
	public static SessionFactoryService getInstance(){
		return INSTANCE;
	}

	/* 
	 * Holt die Session
	 */
	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(builder.build());
		return sessionFactory;
	}


}
