package com.edj.teamoop.service;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.exception.ProjectNotFoundException;
import com.edj.teamoop.mapper.ProjectMapper;
import com.edj.teamoop.model.Project;
import com.edj.teamoop.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;  // Mock du repository

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;  // Service à tester

    @Test
    void testGetAllProjects_OK() {
        List<Project> projects = List.of(
                new Project(1L, "Project Alpha", "Description Alpha", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), true),
                new Project(2L, "Project Beta", "Description Beta", LocalDate.of(2023, 5, 1), LocalDate.of(2024, 6, 1), false)
        );
        Page<Project> projectPage = new PageImpl<>(projects);

        when(projectRepository.findAll(PageRequest.of(0, 10))).thenReturn(projectPage);

        when(projectMapper.toDTO(any(Project.class))).thenAnswer(invocation -> {
            Project project = invocation.getArgument(0);
            return new ProjectDTO(
                    project.getId(),
                    project.getName(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getActive()
            );
        });

        Page<ProjectDTO> result = projectService.getAllProjects(0, 10);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Project Alpha");
        verify(projectRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetAllProjects_EmptyPage() {
        Page<Project> emptyPage = Page.empty(PageRequest.of(1, 10));  // Page vide

        when(projectRepository.findAll(PageRequest.of(1, 10))).thenReturn(emptyPage);

        Page<ProjectDTO> result = projectService.getAllProjects(1, 10);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void testGetAllProjects_NegativePage() {
        assertThrows(IllegalArgumentException.class, () -> projectService.getAllProjects(-1, 10),
                "Invalid pagination parameters: Page index must be >= 0 and size must be > 0");
    }

    @Test
    void testGetAllProjects_ZeroSize() {
        assertThrows(IllegalArgumentException.class, () -> projectService.getAllProjects(0, 0),
                "Invalid pagination parameters: Page index must be >= 0 and size must be > 0");
    }

    @Test
    void testGetAllProjets_MaxSizeLimit() {
        List<Project> projects = List.of(new Project(1L, "Project Alpha", "Description Alpha", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), true));
        Page<Project> projectPage = new PageImpl<>(projects);

        when(projectRepository.findAll(PageRequest.of(0, 50))).thenReturn(projectPage);

        Page<ProjectDTO> result = projectService.getAllProjects(0, 100);

        assertThat(result.getSize()).isLessThanOrEqualTo(50);
        verify(projectRepository, times(1)).findAll(PageRequest.of(0, 50));
    }

    @Test
    void testGetProjectById_OK() {
        Project project = new Project(1L, "Project Alpha", "Description Alpha", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), true);
        ProjectDTO projectDTO =  new ProjectDTO(1L, "Project Alpha DTO", "Description Alpha", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), true);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toDTO(project)).thenReturn(projectDTO);

        ProjectDTO projectResult = projectService.getProjectById(1L);

        assertNotNull(projectResult);
        assertEquals(1L, projectResult.getId());
        assertEquals("Project Alpha DTO", projectResult.getName());

        verify(projectRepository, times(1)).findById(1L);
        verify(projectMapper, times(1)).toDTO(project);
    }

    @Test
    void testGetProjectById_whenProjectDoesNotExist() {
        when(projectRepository.findById(2L)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.getProjectById(2L);
        });

        assertEquals("The project with ID 2 does not exist.", exception.getMessage());
        verify(projectRepository, times(1)).findById(2L);
        verify(projectMapper, never()).toDTO(any());
    }
}
