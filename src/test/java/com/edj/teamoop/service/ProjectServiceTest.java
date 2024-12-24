package com.edj.teamoop.service;

import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ProjectServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @BeforeAll
    void populateProjects() {
        projectService.createProject("Project 1", "Description 1");
        projectService.createProject("Project 2", "Description 2");
    }

    @Test
    void testGetAllProjects() {
        List<Project> result = projectService.getAllProjects();
        // Assert
        assertEquals(2, result.size());
    }


    @Test
    void testGetProjectByName() {
        String projectName = "Project 2";

        Optional<Project> result = projectService.getProjectByName(projectName);

        assertTrue(result.isPresent());
        assertEquals(projectName, result.get().getName());
    }
}