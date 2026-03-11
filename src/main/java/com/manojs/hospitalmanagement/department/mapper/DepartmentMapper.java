package com.manojs.hospitalmanagement.department.mapper;

import com.manojs.hospitalmanagement.department.dto.DepartmentRequestDto;
import com.manojs.hospitalmanagement.department.dto.DepartmentResponseDto;
import com.manojs.hospitalmanagement.department.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentRequestDto departmentRequestDto);

    @Mapping(source = "headDoctor", target = "headDoctor")
    DepartmentResponseDto toDto(Department department);

    void updateDepartment(@MappingTarget Department department,DepartmentRequestDto departmentRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateDepartment(@MappingTarget Department department,DepartmentRequestDto departmentRequestDto);

}
