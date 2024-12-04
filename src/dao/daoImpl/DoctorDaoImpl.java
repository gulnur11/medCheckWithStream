package dao.daoImpl;

import dao.DoctorDao;
import dao.GenericDao;
import database.Database;
import models.Department;
import models.Doctor;
import models.Hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorDaoImpl implements DoctorDao, GenericDao<Doctor> {


    @Override
    public Doctor findDoctorById(Long id) {
        return Database.doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Doctor with ID " + id + " not found!"));
    }



    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        Department department = Database.departments.stream()
                .filter(d -> d.getId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department with ID " + departmentId + " not found!"));

        List<Doctor> doctorsToAdd = doctorsId.stream()
                .map(this::findDoctorById)
                .collect(Collectors.toList());

        department.getDoctors().addAll(doctorsToAdd);
        return "Doctors assigned to department successfully!";
    }

    // Department‘ти id менен табып, анан Doctor‘лорду листтеги айдилери менен табып анан ошол табылган
    // Department'ге табылган Doctor'лорду кошуп коюнунуз




    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        return Database.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(Hospital::getDoctors)
                .orElseThrow(() -> new RuntimeException("Hospital with ID " + id + " not found!"));
    }






    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        return Database.departments.stream()
                .filter(department -> department.getId().equals(id))
                .findFirst()
                .map(Department::getDoctors)
                .orElseThrow(() -> new RuntimeException("Department with ID " + id + " not found!"));
    }


    @Override
    public String add(Long hospitalId, Doctor doctor) {
        Hospital hospital = Database.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital with ID " + hospitalId + " not found!"));

        hospital.getDoctors().add(doctor);
        Database.doctors.add(doctor);
        return "Doctor added successfully to hospital with ID: " + hospitalId;
    }





    @Override
    public void removeById(Long id) {
        Doctor doctorToRemove = Database.doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Doctor with ID " + id + " not found!"));

        Database.doctors.remove(doctorToRemove);
        Database.hospitals.forEach(hospital -> hospital.getDoctors().remove(doctorToRemove));
        Database.departments.forEach(department -> department.getDoctors().remove(doctorToRemove));

        System.out.println("Doctor removed successfully.");
    }





    @Override
    public String updateById(Long id, Doctor updatedDoctor) {
        return Database.doctors.stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst()
                .map(doctor -> {
                    doctor.setFirstName(updatedDoctor.getFirstName());
                    doctor.setLastName(updatedDoctor.getLastName());
                    doctor.setGender(updatedDoctor.getGender());
                    doctor.setExperienceYear(updatedDoctor.getExperienceYear());
                    return "Doctor updated successfully.";
                })
                .orElseThrow(() -> new RuntimeException("Doctor with ID " + id + " not found!"));
    }











}

