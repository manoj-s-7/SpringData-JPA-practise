package com.manojs.hospitalmanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 50)
    private String policyNumber;

    @Column(nullable = false,length = 100)
    private String provider;

    @Column(nullable = false)
    private LocalDate validDate;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;

    @OneToOne(mappedBy = "insurance")
    private Patient patient;

}
