package com.manojs.hospitalmanagement.insurance.mapper;


import com.manojs.hospitalmanagement.insurance.dto.InsuranceRequestDto;
import com.manojs.hospitalmanagement.insurance.dto.InsuranceResponseDto;
import com.manojs.hospitalmanagement.insurance.entity.Insurance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface InsuranceMapper {
    Insurance toEntity(InsuranceRequestDto dto);

    InsuranceResponseDto toDto(Insurance insurance);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(@MappingTarget Insurance insurance, InsuranceRequestDto insuranceRequestDto);
}
