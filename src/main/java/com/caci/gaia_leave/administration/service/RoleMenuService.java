package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.RoleMenu;
import com.caci.gaia_leave.administration.model.response.RoleMenuResponse;
import com.caci.gaia_leave.administration.repository.request.MenuRepository;
import com.caci.gaia_leave.administration.repository.request.RoleMenuRepository;
import com.caci.gaia_leave.administration.repository.request.RoleRepository;
import com.caci.gaia_leave.administration.repository.response.RoleMenuResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleMenuService {

    private final RoleMenuRepository roleMenuRepository;
    private final RoleMenuResponseRepository roleMenuResponseRepository;
    private final RoleRepository roleRepository;
    private final MenuRepository menuRepository;

    public ResponseEntity<String> create(List<RoleMenu> models){
        // TODO Optimise this (with other list, or maybe query)
        models.forEach(model -> {
            if (!roleRepository.existsById(model.getRoleId())) {
                throw new CustomException("Role with id: `" + model.getRoleId() + "` not found");
            }

            if(!menuRepository.existsById(model.getMenuId())) {
                throw new CustomException("Menu with id: `" + model.getMenuId() + "` not found");

            }
        } );

        try{
            roleMenuRepository.saveAll(models);
            return ResponseEntity.ok().body("Successfully created");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<List<RoleMenuResponse>> read(){
        List<RoleMenuResponse> result = AllHelpers.listConverter(roleMenuResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<RoleMenuResponse> readById(Integer roleId){
        Optional<RoleMenuResponse> result = roleMenuResponseRepository.findById(roleId);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, RoleMenu model){
        if(!roleMenuRepository.existsById(id)) {
            throw new CustomException("RoleMenu with id: `" + id + "` not found");
        }

        try{
            model.setId(id);
            roleMenuRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id){
        if(!roleMenuRepository.existsById(id)) {
            throw new CustomException("RoleMenu with id: `" + id + "` not found");
        }
        try{
            roleMenuRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }
}
