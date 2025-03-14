package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.RoleEndpoint;
import com.caci.gaia_leave.administration.model.response.RoleEndpointResponse;
import com.caci.gaia_leave.administration.repository.request.EndpointRepository;
import com.caci.gaia_leave.administration.repository.request.RoleEndpointRepository;
import com.caci.gaia_leave.administration.repository.request.RoleRepository;
import com.caci.gaia_leave.administration.repository.response.RoleEndpointResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleEndpointService {
    private final RoleEndpointRepository roleEndpointRepository;
    private final RoleEndpointResponseRepository roleEndpointResponseRepository;
    private final RoleRepository roleRepository;
    private final EndpointRepository endpointRepository;

    public ResponseEntity<String> create(List<RoleEndpoint> models) {
        models.forEach(model -> {
            if (!roleRepository.existsById(model.getRoleId())) {
                throw new CustomException("Role with id: `" + model.getRoleId() + "` not found");
            }
            if (!endpointRepository.existsById(model.getEndpointId())) {
                throw new CustomException("Endpoint with id: `" + model.getEndpointId() + "` not found");
            }

            if (roleEndpointRepository.existsByRoleIdAndEndpointId(model.getRoleId(), model.getEndpointId())) {
                throw new CustomException("Model with role id: `" + model.getRoleId() + "` and endpoint id `" + model.getEndpointId() + "` already exists");
            }

        });

        try {
            roleEndpointRepository.saveAll(models);
            return ResponseEntity.ok().body("Successfully created");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<List<RoleEndpointResponse>> read() {
        List<RoleEndpointResponse> result = AllHelpers.listConverter(roleEndpointResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<RoleEndpointResponse> readById(Integer id) {
        Optional<RoleEndpointResponse> result = roleEndpointResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, RoleEndpoint model) {
        if (!roleEndpointRepository.existsById(id)) {
            throw new CustomException("RoleEndpoint with id: `" + id + "` not found");
        }
        try {
            model.setId(id);
            roleEndpointRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!roleEndpointRepository.existsById(id)) {
            throw new CustomException("RoleEndpoint with id: `" + id + "` not found");
        }
        try {
            roleEndpointRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteByIds(List<Integer> roleIds) {
       try{
           roleEndpointRepository.deleteAllById(roleIds);
           return ResponseEntity.status(HttpStatus.OK).build();
       } catch (Exception e) {
           throw new CustomException(e.getMessage());
       }
    }
}
