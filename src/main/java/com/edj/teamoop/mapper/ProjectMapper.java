package com.edj.teamoop.mapper;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDTO toDTO(Project project);

    Project toEntity(ProjectDTO projectDTO);
}
