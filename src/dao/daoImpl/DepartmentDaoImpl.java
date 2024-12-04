package dao.daoImpl;

import dao.DepartmentDao;
import dao.GenericDao;
import database.Database;
import models.Department;
import models.Hospital;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao, GenericDao<Department> {

     @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return Database.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(Hospital::getDepartments)
                .orElseThrow(() -> new RuntimeException("Hospital by this ID not found!"));
    }




     @Override
    public Department findDepartmentByName(String name) {
        return Database.departments.stream()
                .filter(department -> department.getDepartmentName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department by this name not found!"));
    }





     @Override
    public String add(Long hospitalId, Department department) {
        Hospital hospital = Database.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital by this ID not found!"));

        hospital.getDepartments().add(department);
        Database.departments.add(department);
        return "Department added successfully to hospital with ID: " + hospitalId;
    }






    @Override
    public void removeById(Long id) {
        Department departmentToRemove = Database.departments.stream()
                .filter(department -> department.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department by this ID not found!"));

        Database.departments.remove(departmentToRemove);
        Database.hospitals.forEach(hospital -> hospital.getDepartments().remove(departmentToRemove));
        System.out.println("Department removed successfully.");
    }




     @Override
    public String updateById(Long id, Department updatedDepartment) {
        return Database.departments.stream()
                .filter(department -> department.getId().equals(id))
                .findFirst()
                .map(department -> {
                    department.setDepartmentName(updatedDepartment.getDepartmentName());
                    return "Department updated successfully.";
                })
                .orElseThrow(() -> new RuntimeException("Department by this ID not found!"));
    }





}
