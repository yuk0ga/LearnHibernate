package tagbar;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import tagbar.entity.Event;
import tagbar.support.PersistenceUnitInfoImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * JPA ブートストラップ（persistence.xml なし）
 * <p/>
 * http://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#bootstrap-jpa-hibernate
 * @see PersistenceUnitInfoImpl
 */
public class N03_JpaBootstrap2 {

	public static void main(String[] args) {
		String persistenceUnitName = "section-1";

		List<String> entityClassNames = new ArrayList<>();
		entityClassNames.add(Event.class.getCanonicalName());

		Properties properties = new Properties();
		properties.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/study");
		properties.put(AvailableSettings.USER, "root");
		properties.put(AvailableSettings.PASS, "1qazxsw2");
		properties.put(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");
		properties.put(AvailableSettings.DIALECT, MySQLDialect.class);
		properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.put(AvailableSettings.SHOW_SQL, true);
		properties.put(AvailableSettings.FORMAT_SQL, true);

		PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl(
				persistenceUnitName,
				entityClassNames,
				properties
		);

		EntityManagerFactoryBuilderImpl entityManagerFactoryBuilder =
				new EntityManagerFactoryBuilderImpl(
						new PersistenceUnitInfoDescriptor(persistenceUnitInfo),
						null
				);

		EntityManagerFactory entityManagerFactory = entityManagerFactoryBuilder.build();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(new Event());
		entityManager.persist(new Event());

		entityManager.getTransaction().commit();

		Event event = entityManager.find(Event.class, 1L);
		System.out.println(event.getId());

		TypedQuery<Event> query = entityManager.createQuery("from Event", Event.class);
		List<Event> events = query.getResultList();
		for (Event e: events) {
			System.out.println(e.getId());
		}

		entityManager.close();

		entityManagerFactory.close();
	}
}
