package com.edj.teamoop.service.cache;

import java.util.Optional;

public interface EntityCacheService<T, ID> {
    Optional<T> getCachedEntity(ID id);
    T saveAndEvictCache(T entity);
    void deleteAndEvictCache(ID id);
    void clearAllCache();
}
