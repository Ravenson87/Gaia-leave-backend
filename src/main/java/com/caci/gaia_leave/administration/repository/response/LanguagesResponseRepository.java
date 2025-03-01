package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.request.Languages;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguagesResponseRepository extends CrudRepository<Languages, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByCodeIgnoreCase(String code);
    boolean existsById(Integer id);
    Optional<Languages> findByNameIgnoreCase(String name);
    Optional<Languages> findByCodeIgnoreCase(String code);
}
