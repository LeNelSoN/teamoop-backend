package com.edj.teamoop.controller;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.service.cache.ProjectCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectCacheService projectCacheService;

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProject(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<ProjectDTO> projectPage = projectCacheService.getCachedPage(page, size);
        if(projectPage.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(projectPage);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") Long projectId) {
        ProjectDTO projectDTO = projectCacheService.getCachedEntity(projectId);
        return ResponseEntity.ok(projectDTO);
    }
}
