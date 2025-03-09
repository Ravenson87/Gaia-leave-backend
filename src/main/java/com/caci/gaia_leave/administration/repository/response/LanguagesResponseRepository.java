package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.LanguagesResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguagesResponseRepository extends CrudRepository<LanguagesResponse, Integer> {
    boolean existsById(Integer id);

    Optional<LanguagesResponse> findByNameIgnoreCase(String name);

    Optional<LanguagesResponse> findByCodeIgnoreCase(String code);
}
