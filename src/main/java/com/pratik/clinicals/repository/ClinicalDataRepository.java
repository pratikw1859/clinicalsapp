package com.pratik.clinicals.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratik.clinicals.model.ClinicalData;

@Repository
public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Long> {
	
	public Optional<ClinicalData> findByPatientIdAndId(Long patientId, Long clinicalId);
	
	public List<ClinicalData> findAllByPatientIdAndComponentName(Long patientId, String componentName);
}
