package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Languages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagesRepository extends CrudRepository<Languages, Integer> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

}
