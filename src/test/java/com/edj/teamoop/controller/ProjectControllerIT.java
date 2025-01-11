package com.edj.teamoop.controller;

import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class ProjectControllerIT {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        projectRepository.deleteAll();
    }

    @Test
    void testGetAllProjects_ReturnsProjects() throws Exception {
        projectRepository.save(new Project(1L, "Project Alpha", "Description Alpha", LocalDate.now(), LocalDate.now().plusDays(10), true));
        projectRepository.save(new Project(2L, "Project Beta", "Description Beta", LocalDate.now(), LocalDate.now().plusDays(20), true));

        mockMvc.perform(get("/api/project")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value("Project Alpha"))
                .andExpect(jsonPath("$.content[1].name").value("Project Beta"));

        assertThat(cacheManager.getCache("projects").get("page:0-size:10")).isNotNull();

        mockMvc.perform(get("/api/project")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        assertThat(cacheManager.getCache("projects").get("page:0-size:10")).isNotNull();
    }

    @Test
    void testGetAllProjects_NoContent() throws Exception {
        mockMvc.perform(get("/api/project")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

