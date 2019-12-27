package com.pratik.clinicals.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pratik.clinicals.model.Patient;
import com.pratik.clinicals.repository.PatientRepository;
import com.pratik.clinicals.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

	private PatientRepository patientRepo;

	public PatientServiceImpl(PatientRepository patientRepo) {
		super();
		this.patientRepo = patientRepo;
	}

	@Override
	public Patient save(Patient patient) {
		return patientRepo.save(patient);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepo.findAll();
	}

	@Override
	public Optional<Patient> findById(Long id) {
		return patientRepo.findById(id);
	}
}
