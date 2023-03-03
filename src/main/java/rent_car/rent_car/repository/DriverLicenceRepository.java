package rentcar.rentcar.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.DriverLicence;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class DriverLicenceRepository {

    public void createDriverLicence(DriverLicence driverLicence) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(driverLicence);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void updateDriverLicence(DriverLicence driverLicence) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(driverLicence);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteDriverLicence(DriverLicence driverLicence) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(driverLicence);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public DriverLicence getDriverLicenceById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        DriverLicence driverLicence = session.get(DriverLicence.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return driverLicence;
    }

    public ArrayList<DriverLicence> getAllDriverLicence() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from DriverLicence ");
        ArrayList<DriverLicence> list = (ArrayList<DriverLicence>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return list;
    }
}
