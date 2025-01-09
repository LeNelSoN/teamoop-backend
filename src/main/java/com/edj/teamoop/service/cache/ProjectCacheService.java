package com.edj.teamoop.service.cache;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProjectCacheService implements PageCacheService{
    @Autowired
    private ProjectService projectService;

    @Override
    @Cacheable(value = "projects", key = "'page:' + #page + '-size:' + #size")
    public Page<ProjectDTO> getCachedPage(int page, int size) {
        return projectService.getAllProjects(page, size);
    }

}
