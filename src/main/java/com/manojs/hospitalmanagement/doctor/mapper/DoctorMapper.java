package com.manojs.hospitalmanagement.doctor.mapper;


import com.manojs.hospitalmanagement.doctor.dto.DoctorRequestDto;
import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.doctor.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(DoctorRequestDto doctorRequestDto);

    @Mapping(source = "departments" ,target = "departments")
    DoctorResponseDto toDto(Doctor doctor);

    void updateDoctor(@MappingTarget Doctor doctor,DoctorRequestDto doctorRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateDoctor(@MappingTarget Doctor doctor,DoctorRequestDto doctorRequestDto);

}
