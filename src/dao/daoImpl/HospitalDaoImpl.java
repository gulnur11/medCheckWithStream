package dao.daoImpl;

import dao.HospitalDao;
import database.Database;
import models.Hospital;
import models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class HospitalDaoImpl implements HospitalDao {


    @Override
    public String addHospital(Hospital hospital) {
        Database.hospitals.add(hospital);
        return "Added new hospital successfully ! ";
    }



    @Override
    public Hospital findHospitalById(Long id) {
        return Database.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found hospital by this Id!"));
    }




    @Override
    public List<Hospital> getAllHospital() {
       return new ArrayList<>(Database.hospitals);
    }



         @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        return Database.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(Hospital::getPatients)
                .orElseThrow(() -> new RuntimeException("Hospital by this id not found!"));
    }





    @Override
    public String deleteHospitalById(Long id) {
        Hospital hospitalToRemove = Database.hospitals.stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital by this id not found"));

        Database.hospitals.remove(hospitalToRemove);
        return "Deleted successfully";
    }




     @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        Map<String, Hospital> result = Database.hospitals.stream()
                .filter(hospital -> hospital.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toMap(Hospital::getHospitalName, hospital -> hospital));

        if (result.isEmpty()) {
            throw new RuntimeException("No hospitals found at the given address: " + address);
        }

        return result;
    }




}

