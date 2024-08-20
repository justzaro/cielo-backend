package com.example.cielobackend.service.implementation;

import com.example.cielobackend.exception.ResourceDoesNotExistException;
import com.example.cielobackend.service.DataValidationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.cielobackend.common.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
public class DataValidationServiceImpl implements DataValidationService {
    private final ApplicationContext applicationContext;
    private final Map<String, JpaRepository<?, Long>> repositories = new HashMap<>();

    @PostConstruct
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void initializeBeans() {
        Map<String, JpaRepository> map = applicationContext.getBeansOfType(JpaRepository.class);

        for (Map.Entry<String, JpaRepository> entry : map.entrySet()) {
            repositories.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public <T> T getResourceOrThrow(Class<T> entityClass, long id) {
        JpaRepository<T, Long> entityClassRepository = getRepository(entityClass);
        if (entityClassRepository != null) {
            return checkIfResourceExistsOrThrow(entityClassRepository.findById(id), RESOURCE_DOES_NOT_EXIST);
        }
        return null;
    }

    @Override
    public <T> T getResourceOrThrow(Class<T> entityClass, long id, String errorMessage) {
        JpaRepository<T, Long> entityClassRepository = getRepository(entityClass);
        if (entityClassRepository != null) {
            return checkIfResourceExistsOrThrow(entityClassRepository.findById(id), errorMessage);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> JpaRepository<T, Long> getRepository(Class<T> entityClass) {
        String entityClassName = entityClass.getSimpleName();
        String repositoryBeanName = entityClassName.substring(0, 1).toLowerCase() +
                                    entityClassName.substring(1) + "Repository";

        return (JpaRepository<T, Long>) repositories.get(repositoryBeanName);
    }

    private <T> T checkIfResourceExistsOrThrow(Optional<T> optional, String errorMessage) {
        return optional.orElseThrow(() -> new ResourceDoesNotExistException(errorMessage));
    }
}
