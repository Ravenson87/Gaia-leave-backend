package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.RoleMenu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends CrudRepository<RoleMenu, Integer> {
    boolean existsByRoleIdAndMenuId(Integer roleId, Integer menuId);
}
