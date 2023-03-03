package rentcar.rentcar.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.Car;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public class CarRepository {

    public void createCar(Car car) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void updateCar(Car car) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(car);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteCar(Car car) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(car);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public Car getCarById(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Car car = session.get(Car.class, id);
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return car;
    }

    public ArrayList<Car> getAllCar() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Car ");
        ArrayList<Car> list = (ArrayList<Car>) query.getResultList();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
        return list;
    }
}
