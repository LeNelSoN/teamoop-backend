package com.edj.teamoop.service;

import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private  ProjectRepository projectRepository;


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(String name, String description)
    {
        Project p = new Project(name, description);
        return projectRepository.save(p);
    }

    public void deleteProjectById(Long id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Le projet avec l'id " + id + " n'existe pas.");
        }
    }

    public Optional<Project> getProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public List<Project> getActiveProjects() {
        return projectRepository.findByActiveTrue();
    }
}