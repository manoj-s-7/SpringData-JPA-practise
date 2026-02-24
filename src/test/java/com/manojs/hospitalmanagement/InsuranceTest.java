package com.manojs.hospitalmanagement;

import com.manojs.hospitalmanagement.entity.Insurance;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void addInsuranceTest(){
        Insurance insurance = Insurance.builder()
                .policyNumber("10221")
                .provider("tata")
                .validDate(LocalDate.of(2030,9,12))
                .createAt(LocalDateTime.of(2021,4,14,5,31))
                .build();
        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 1L);
        System.out.println(patient);

    }
}
