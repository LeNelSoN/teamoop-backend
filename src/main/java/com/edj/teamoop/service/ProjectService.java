package com.edj.teamoop.service;

import com.edj.teamoop.exception.ProjectNotFoundException;
import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.mapper.ProjectMapper;
import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<ProjectDTO> getAllProjects(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters: Page index must be >= 0 and size must be > 0");
        }
        int maxSize = 50;
        PageRequest pageable = PageRequest.of(page, Math.min(size, maxSize));
        Page<Project> projectPage = projectRepository.findAll(pageable);
        return projectPage.map(projectMapper::toDTO);
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(()-> new ProjectNotFoundException(String.format("The project with ID %s does not exist.", id)));
    }

    public void deleteProjectById(Long id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new ProjectNotFoundException(String.format("The project with ID %s does not exist.", id));
        }
    }

    public Project getProjectByName(String name) {
        Optional<Project> project = projectRepository.findByName(name);
        return project.orElseThrow(()-> new ProjectNotFoundException(String.format("The project with ID %s does not exist.", name)));
    }

    public List<Project> getActiveProjects() {
        return projectRepository.findByActiveTrue();
    }
}