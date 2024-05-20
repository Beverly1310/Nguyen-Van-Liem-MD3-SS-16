package com.ra.btss16.dao.impl;

import com.ra.btss16.dao.IEmployee;
import com.ra.btss16.model.dto.EmployeeDTO;
import com.ra.btss16.model.Employee;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements IEmployee {
    @Autowired
    private SessionFactory sessionFactory;
private static final String uploadPath = "C:\\Users\\nliem\\OneDrive\\Documents\\It\\MD3\\BTSS16\\src\\main\\webapp\\image";
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
    public void save(EmployeeDTO employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Employee employeeDTO = Employee.builder().
                    id(employee.getId()).
                    name(employee.getName())
                    .address(employee.getAddress())
                    .dateOfBirth(employee.getDateOfBirth())
                    .phone(employee.getPhone())
                    .build();
            MultipartFile file = employee.getImgFile();
            String fileName = new Date().getTime()+"-"+file.getOriginalFilename();
            File uploadFile = new File(uploadPath+File.separator+fileName);
            try {
                InputStream inputStream = file.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(uploadFile);
                IOUtils.copy(inputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            employeeDTO.setImageURL("img/"+fileName);
            session.save(employeeDTO);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();

        }
    }

    @Override
    public void update(EmployeeDTO employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Employee employeeDTO = Employee.builder().
                    id(employee.getId()).
                    name(employee.getName())
                    .address(employee.getAddress())
                    .dateOfBirth(employee.getDateOfBirth())
                    .phone(employee.getPhone())
                    .build();
            MultipartFile file = employee.getImgFile();
            if (file != null && !file.isEmpty()) {
            String fileName = new Date().getTime() +"-"+file.getOriginalFilename();
            File uploadFile = new File(uploadPath+File.separator+fileName);
            try {
                InputStream inputStream = file.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(uploadFile);
                IOUtils.copy(inputStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            employeeDTO.setImageURL("img/"+fileName);}
            else {
                employeeDTO.setImageURL(findById(employee.getId()).getImageURL());
            }
            session.update(employeeDTO);
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
            session.createQuery("delete from Employee where id=:id").setParameter("id", id).executeUpdate();
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
