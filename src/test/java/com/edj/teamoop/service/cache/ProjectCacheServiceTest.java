package com.edj.teamoop.service.cache;

import com.edj.teamoop.dto.ProjectDTO;
import com.edj.teamoop.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectCacheServiceTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectCacheService projectCacheService;

    @BeforeEach
    void setUpCache() {
        CacheManager cacheManager = new ConcurrentMapCacheManager("projects");
        cacheManager.getCache("projects").clear();
    }

    @Test
    void testGetCachedPage_CacheMiss() {
        Page<ProjectDTO> mockPage = new PageImpl<>(List.of(
                new ProjectDTO(1L, "Project Alpha", "Description Alpha", null, null, true)
        ));

        when(projectService.getAllProjects(0, 10)).thenReturn(mockPage);

        Page<ProjectDTO> result1 = projectCacheService.getCachedPage(0, 10);
        assertThat(result1.getContent().size()).isEqualTo(1);
        assertThat(result1.getContent().get(0).getName()).isEqualTo("Project Alpha");

        Page<ProjectDTO> result2 = projectCacheService.getCachedPage(0, 10);
        assertThat(result2.getContent().size()).isEqualTo(1);

        verify(projectService, times(1)).getAllProjects(0, 10);
    }

    @Test
    void testGetCachedPage_CacheEviction() {
        Page<ProjectDTO> mockPage1 = new PageImpl<>(List.of(
                new ProjectDTO(1L, "Project Alpha", "Description Alpha", null, null, true)
        ));
        Page<ProjectDTO> mockPage2 = new PageImpl<>(List.of(
                new ProjectDTO(2L, "Project Beta", "Description Beta", null, null, true)
        ));

        when(projectService.getAllProjects(0, 10)).thenReturn(mockPage1).thenReturn(mockPage2);

        Page<ProjectDTO> result1 = projectCacheService.getCachedPage(0, 10);
        assertThat(result1.getContent().get(0).getName()).isEqualTo("Project Alpha");

        Page<ProjectDTO> result2 = projectCacheService.getCachedPage(0, 10);
        assertThat(result2.getContent().get(0).getName()).isEqualTo("Project Alpha");

        verify(projectService, times(1)).getAllProjects(0, 10);
    }
}

