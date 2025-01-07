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

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()) {
            return project.get();
        } else {
            throw new IllegalArgumentException("Le projet avec  l'id " + id + " n'existe pas.");
        }
    }

    public void deleteProjectById(Long id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Le projet avec  l'id " + id + " n'existe pas.");
        }
    }

    public Project getProjectByName(String name) {
        Optional<Project> project = projectRepository.findByName(name);
        if(project.isPresent()) {
            return project.get();
        } else {
            throw new IllegalArgumentException("Le projet avec  le nom " + name + " n'existe pas.");
        }
    }

    public List<Project> getActiveProjects() {
        return projectRepository.findByActiveTrue();
    }
}