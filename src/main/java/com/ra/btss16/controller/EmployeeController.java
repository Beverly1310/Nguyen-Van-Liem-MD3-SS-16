package com.ra.btss16.controller;

import com.ra.btss16.dao.IEmployee;
import com.ra.btss16.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Controller
public class EmployeeController {
    @Autowired
    private IEmployee iEmployee;

    @RequestMapping("/")
    public String home(Model model) {
        List<Employee> employeeList = iEmployee.findAll();
        model.addAttribute("employeeList", employeeList);
        return "home";
    }

    @GetMapping("/add")
    public String handleAdd(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            return "add";
        }else {
            iEmployee.save(employee);
            return "redirect:/";
        }

    }

    @GetMapping("/edit/{id}")
    public String handEdit(@PathVariable("id") Integer id, Model model) {
        Employee employee = iEmployee.findById(id);
        model.addAttribute("employee", employee);
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid@ModelAttribute("employee") Employee employee,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            return "edit";
        }else {
        iEmployee.update(employee);
        return "redirect:/";}
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        iEmployee.delete(id);
        return "redirect:/";
    }
}
