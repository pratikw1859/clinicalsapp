package com.pratik.clinicals.service;

import java.util.List;
import java.util.Optional;

import com.pratik.clinicals.model.Patient;

public interface IPatientService {
	
	public Patient save(Patient patient);
	
	public List<Patient> getAllPatients();
	
	public Optional<Patient> findById(Long id);
}
