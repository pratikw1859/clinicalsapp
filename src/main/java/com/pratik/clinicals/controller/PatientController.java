package com.pratik.clinicals.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratik.clinicals.model.Patient;
import com.pratik.clinicals.service.IPatientService;

@RestController
@RequestMapping(PatientController.URI)
@CrossOrigin
public class PatientController {
	
	public static final String URI = "/api/patient";
	
	private IPatientService patientService;

	public PatientController(IPatientService patientService) {
		this.patientService = patientService;
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Patient patient) {
		Patient savedInDb = patientService.save(patient);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedInDb.getId()).toUri();
		return ResponseEntity.created(location).body(savedInDb);
	}
	
	@GetMapping
	public List<Patient> getAllPatients() {
		return patientService.getAllPatients();
	}
	
	@GetMapping("/{id}")
	public Patient findById(@PathVariable("id")Long id) {
		return patientService.findById(id).get();
	}
	
	@GetMapping("/analize/{id}")
	public Patient analizePatientById(@PathVariable("id")Long id) {
		Patient patient = patientService.findById(id).get();
		patient.getClinicalDatas().stream().map(clinicalData -> {
			
			if(clinicalData.getComponentName().equalsIgnoreCase("hw")) {

				String[] heightAndWeight= clinicalData.getComponentValue().split("/");
				float heightInMeter = Float.parseFloat(heightAndWeight[0])*0.0254f;
				float bmi = Float.parseFloat(heightAndWeight[1]) / (heightInMeter * heightInMeter);
				
				clinicalData.setComponentName("BMI");
				clinicalData.setComponentValue(Float.toString(bmi));
			}
			return clinicalData;
		}).collect(Collectors.toSet());
		return patient;
	}
}
