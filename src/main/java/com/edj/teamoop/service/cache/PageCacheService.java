package com.edj.teamoop.service.cache;

import org.springframework.data.domain.Page;

public interface PageCacheService<T> {
    Page<T> getCachedPage(int page, int size);
}
