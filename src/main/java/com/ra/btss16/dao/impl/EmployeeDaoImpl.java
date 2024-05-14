package com.ra.btss16.dao.impl;

import com.ra.btss16.dao.IEmployee;
import com.ra.btss16.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements IEmployee {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Employee ").list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void save(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();

        }
    }

    @Override
    public void update(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(findById(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Employee findById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            Employee employee = session.get(Employee.class, id);
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
