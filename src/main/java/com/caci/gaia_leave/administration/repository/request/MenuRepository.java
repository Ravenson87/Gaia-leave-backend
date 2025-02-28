package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Integer> {
    Optional<Menu> findById(Integer id);
    Optional<Menu> findByName(String name);
    Optional<Menu> findByMenuNumber(Integer MenuNumber);
    boolean existsByName(String name);
    boolean existsByMenuNumber(Integer menuNumber);
}
