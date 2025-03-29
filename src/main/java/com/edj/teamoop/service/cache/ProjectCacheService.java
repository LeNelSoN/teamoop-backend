package com.edj.teamoop.service.cache;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

    @CachePut(value = "projects", key = "'projectId:' + #result.id")
    public ProjectDTO createProject(ProjectDTO project) {
        return projectService.createProject(project);
    }

    @CacheEvict(value = "projects", key = "'projectId:' + #projectId")
    public void deleteProject(Long projectId) {
        projectService.deleteProjectById(projectId);
    }

    @CachePut(value = "projects", key = "'projectId:' + #projectDTO.id")
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        return projectService.updateProject(projectDTO);
    }
}
