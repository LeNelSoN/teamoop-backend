package com.edj.teamoop.service.cache;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectCacheService {
    @Autowired
    private ProjectService projectService;

    @Cacheable(value = "projects", key = "'page:' + #page + '-size:' + #size")
    public Page<ProjectDTO> getCachedPage(int page, int size) {
        return projectService.getAllProjects(page, size);
    }

    @Cacheable(value = "projects", key = "'projectId:' + #id")
    public ProjectDTO getCachedEntity(Long id) {
        return projectService.getProjectById(id);
    }

    public ProjectDTO createProject(ProjectDTO project) {
        return projectService.createProject(project);
    }

    public ProjectDTO deleteProject(Long projectId) {
        ProjectDTO project = projectService.getProjectById(projectId);
        projectService.deleteProjectById(projectId);
        return project;
    }

    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        projectDTO.setId(projectId);
        return projectService.updateProject(projectDTO);
    }
}
