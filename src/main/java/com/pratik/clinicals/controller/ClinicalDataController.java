package com.pratik.clinicals.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratik.clinicals.exceptions.ResourceNotFoundException;
import com.pratik.clinicals.model.ClinicalData;
import com.pratik.clinicals.model.Patient;
import com.pratik.clinicals.repository.ClinicalDataRepository;
import com.pratik.clinicals.repository.PatientRepository;

@RestController
@RequestMapping(ClinicalDataController.URL)
@CrossOrigin
public class ClinicalDataController {
	
	public static final String URL = "/api/patient";
	
	private ClinicalDataRepository clinicalDataRepository;
	
	private PatientRepository patientRepo;;

	public ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepo) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepo = patientRepo;
	}

	@PostMapping("/{id}/clinicalData")
	public ResponseEntity<?> save(@PathVariable("id")Long patientId,@RequestBody ClinicalData clinicalData) {
		if(patientRepo.existsById(patientId)) {
			Patient patient = patientRepo.findById(patientId).get();
			clinicalData.setPatient(patient);
			ClinicalData savedInDb = clinicalDataRepository.save(clinicalData);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cId}").buildAndExpand(savedInDb.getId()).toUri();
			return ResponseEntity.created(location).body(savedInDb);
		}
		else {
			throw new ResourceNotFoundException("Patient Not Found");
		}
	}
	
	@GetMapping("/{patientId}/clinicalData/{clinicalId}")
	public ClinicalData findByPatientIdAndClinicalId(@PathVariable("patientId")Long patientId, @PathVariable("clinicalId")Long clinicalId) {
		if(!patientRepo.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient Not Found");
		}
		return clinicalDataRepository.findByPatientIdAndId(patientId, clinicalId).get();
	}
	
	@GetMapping("/{id}/clinicalData/componentName/{name}")
	public List<ClinicalData> findAllByPatientIdAndComponentName(@PathVariable("id")Long patientId, @PathVariable("name")String componentName) {
		if(!patientRepo.existsById(patientId)) {
			throw new ResourceNotFoundException("Patient Not Found");
		}
		return clinicalDataRepository.findAllByPatientIdAndComponentName(patientId, componentName);
	}
}