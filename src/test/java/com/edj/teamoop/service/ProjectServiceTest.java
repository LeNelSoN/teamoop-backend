package com.edj.teamoop.service;

import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    static final String PROJECT_NAME = "Project 2";
    @BeforeAll
    void populateProjects() {
        Project p1 = new Project();
        p1.setName("Project 1");
        p1.setDescription("Description 1");
        p1.setActive(false);
        p1.setStartDate(LocalDate.now());
        p1.setEndDate(LocalDate.now());
        projectRepository.save(p1);

        Project p2 = new Project();
        p2.setName(PROJECT_NAME);
        p2.setDescription("Description 2");
        p2.setActive(true);
        p2.setStartDate(LocalDate.now());
        p2.setEndDate(LocalDate.now());
        projectRepository.save(p2);
    }

    @Test
    void testGetAllProjects() {
        List<Project> result = projectService.getAllProjects();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProjectByName() {
        try {
            Project result = projectService.getProjectByName(PROJECT_NAME);
            assertEquals(PROJECT_NAME, result.getName());
        } catch (Exception e) {
            fail("Le project est introuvable");
        }
    }
}