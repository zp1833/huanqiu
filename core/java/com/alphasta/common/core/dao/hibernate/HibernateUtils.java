package com.alphasta.common.core.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory factory;
	
	static {
		Configuration cfg = new AnnotationConfiguration().configure();
		factory = cfg.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
	
	public static Session getSession() {
		return factory.openSession();
	}
	
	public static void close(Session session) {
		if(session != null && session.isOpen()) {
			session.close();
		}
	}
}
