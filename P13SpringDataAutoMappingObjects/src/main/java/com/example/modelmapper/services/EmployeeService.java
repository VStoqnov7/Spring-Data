package com.example.modelmapper.services;

import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.example.modelmapper.entities.dtos.EmployeeDTO;
import com.example.modelmapper.entities.dtos.EmployeeNameAndSalaryDTO;
import com.example.modelmapper.entities.dtos.EmployeeNamesDTO;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();

    EmployeeNamesDTO findNamesById(long id);

    EmployeeNameAndSalaryDTO findNameAndSalaryById(long id);
}