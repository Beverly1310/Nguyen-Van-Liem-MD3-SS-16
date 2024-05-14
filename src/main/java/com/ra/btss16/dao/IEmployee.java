package com.ra.btss16.dao;

import com.ra.btss16.model.Employee;

import java.util.List;

public interface IEmployee {
    List<Employee> findAll();
    void save(Employee employee);
    void update(Employee employee);
    void delete(Integer id);
    Employee findById(Integer id);
}
