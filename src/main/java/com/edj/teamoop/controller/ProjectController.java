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
        }
        return ResponseEntity.ok(projectPage);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") Long projectId) {
        ProjectDTO projectDTO = projectCacheService.getCachedEntity(projectId);
        return ResponseEntity.ok(projectDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO createdProject = projectCacheService.createProject(projectDTO);
        return ResponseEntity.ok(createdProject);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable("id") Long projectId) {
        ProjectDTO deletedProject = projectCacheService.deleteProject(projectId);
        return ResponseEntity.ok(deletedProject);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") Long projectId, @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectCacheService.updateProject(projectId, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

}
