package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Role;
import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.repository.request.RoleRepository;
import com.caci.gaia_leave.administration.repository.response.RoleResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleResponseRepository roleResponseRepository;

    /**
     * Create role.
     *
     * @param model
     * @return
     */
    public ResponseEntity<RoleResponse> createRole(Role model) {

        if (roleResponseRepository.existsByName(model.getName())) {
            throw new CustomException("Role with name `" + model.getName() + "` already exists.");
        }

        try {
            Role save = roleRepository.save(model);
            Optional<RoleResponse> result = roleResponseRepository.findById(save.getId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<RoleResponse>> read() {
        List<RoleResponse> result = roleResponseRepository.findByIdGreaterThan(1);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<RoleResponse> readById(Integer id) {

        if (id == 1) {
            throw new CustomException("Doesn't exist.");
        }

        Optional<RoleResponse> result = roleResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, Role model) {

        if (!roleRepository.existsById(id)) {
            throw new CustomException("Role with id `" + id + "` does not exists.");
        }

        Optional<RoleResponse> unique = roleResponseRepository.findByName(model.getName());
        if (unique.isPresent() && !unique.get().getId().equals(id)) {
            throw new CustomException("Role `" + model.getName() + "` already exists.");
        }

        try {
            model.setId(id);
            roleRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new CustomException("Role with id `" + id + "` does not exists.");
        }

        try {
            roleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
