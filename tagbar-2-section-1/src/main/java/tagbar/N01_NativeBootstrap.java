package tagbar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQLDialect;
import tagbar.entity.Event;

/**
 * Hibernate ネイティブのブートストラップ
 *
 * http://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#bootstrap-native
 */
public class N01_NativeBootstrap {

	public static void main(String[] args) {
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
				.applySetting(AvailableSettings.URL, "jdbc:mysql://localhost:3306/study")
				.applySetting(AvailableSettings.USER, "root")
				.applySetting(AvailableSettings.PASS, "1qazxsw2")
				.applySetting(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver")
				.applySetting(AvailableSettings.DIALECT, MySQLDialect.class)
				.applySetting(AvailableSettings.HBM2DDL_AUTO, "update")
				.applySetting(AvailableSettings.SHOW_SQL, true)
				.applySetting(AvailableSettings.FORMAT_SQL, true)
				.build();

		Metadata metadata = new MetadataSources(standardRegistry)
				.addAnnotatedClass(Event.class)
				.getMetadataBuilder()
				.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
				.build();

		SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();
		SessionFactory sessionFactory = sessionFactoryBuilder.build();

		Session session = sessionFactory.openSession();
		session.getTransaction().begin();

		session.save(new Event());
		session.save(new Event());

		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}
}
