package dao.daoImpl;

import dao.GenericDao;
import dao.PatientDao;
import database.Database;
import models.Hospital;
import models.Patient;

import java.util.*;
import java.util.stream.Collectors;


public class PatientDaoImpl implements PatientDao, GenericDao<Patient> {


    @Override
    public String add(Long hospitalId, Patient patient) {
        Hospital hospital = Database.hospitals.stream()
                .filter(h -> h.getId().equals(hospitalId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital with ID " + hospitalId + " not found!"));

        hospital.getPatients().add(patient);
        Database.patients.add(patient);
        return "Patient added successfully to hospital!";
    }





    @Override
    public void removeById(Long id) {
        Patient patientToRemove = Database.patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient with ID " + id + " not found!"));

        Database.patients.remove(patientToRemove);
        Database.hospitals.forEach(hospital -> hospital.getPatients().remove(patientToRemove));

        System.out.println("Patient removed successfully!");
    }





    @Override
    public String updateById(Long id, Patient patient) {
        return Database.patients.stream()
                .filter(existingPatient -> existingPatient.getId().equals(id))
                .findFirst()
                .map(existingPatient -> {
                    existingPatient.setFirstName(patient.getFirstName());
                    existingPatient.setLastName(patient.getLastName());
                    existingPatient.setAge(patient.getAge());
                    existingPatient.setGender(patient.getGender());
                    return "Patient updated successfully!";
                })
                .orElseThrow(() -> new RuntimeException("Patient with ID " + id + " not found!"));
    }




    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        Hospital hospital = Database.hospitals.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital with ID " + id + " not found!"));

        hospital.getPatients().addAll(patients);
        Database.patients.addAll(patients);

        return "Patients added to hospital successfully!";
    }





    @Override
    public Patient getPatientById(Long id) {
        return Database.patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient with ID " + id + " not found!"));
    }




    @Override
    public Map<Integer, Patient> getPatientByAge() {
        return Database.patients.stream()
                .collect(Collectors.toMap(Patient::getAge, patient -> patient, (existing, replacement) -> existing));
    }





    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        return Database.patients.stream()
                .sorted(ascOrDesc.equalsIgnoreCase("asc")
                        ? Comparator.comparingInt(Patient::getAge)
                        : Comparator.comparingInt(Patient::getAge).reversed())
                .collect(Collectors.toList());
    }





}





