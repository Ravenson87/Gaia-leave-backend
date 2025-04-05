package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.FreeDayType;
import com.caci.gaia_leave.administration.model.response.FreeDayTypeResponse;
import com.caci.gaia_leave.administration.repository.request.FreeDayTypeRepository;
import com.caci.gaia_leave.administration.repository.response.FreeDayTypeResponseRepository;
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
public class FreeDayTypeService {

    private final FreeDayTypeRepository freeDayTypeRepository;
    private final FreeDayTypeResponseRepository freeDayTypeResponseRepository;

    /**
     * Create FreeDayType in database
     *
     * @param model FreeDayType
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> create(FreeDayType model) {
        if (freeDayTypeRepository.existsByType(model.getType())) {
            throw new CustomException("Free day type with type `" + model.getType() + "` already exists.");
        }
        try {
            freeDayTypeRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());

        }
    }

    /**
     * Read FreeDayType from database
     *
     * @return ResponseEntity<List<FreeDayTypeResponse>>
     */
    public ResponseEntity<List<FreeDayTypeResponse>> read() {
        List<FreeDayTypeResponse> result = AllHelpers.listConverter(freeDayTypeResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read FreeDayType by id from database
     *
     * @param id Integer
     * @return ResponseEntity<FreeDayTypeResponse>
     */
    public ResponseEntity<FreeDayTypeResponse> readById(Integer id) {
        Optional<FreeDayTypeResponse> result = freeDayTypeResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());

    }

    /**
     * Update FreeDayType by id in database
     *
     * @param id Integer
     * @param model FreeDayType
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> update(Integer id, FreeDayType model) {
        if (!freeDayTypeRepository.existsById(id)) {
            throw new CustomException("FreeDayType with id `" + id + "` doesn't exists.");
        }
        Optional<FreeDayType> uniqueName = freeDayTypeRepository.findByType(model.getType());
        if (uniqueName.isPresent() && !uniqueName.get().getId().equals(id)) {
            throw new CustomException("FreeDayType with type `" + model.getType() + "` already exists.");
        }
        try {
            model.setId(id);
            freeDayTypeRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Delete FreeDayType by id from database
     *
     * @param id Integer
     * @return ResponseEntity<HttpStatus>
     */
    public ResponseEntity<HttpStatus> delete(Integer id) {
        if (!freeDayTypeRepository.existsById(id)) {
            throw new CustomException("FreeDayType with id `" + id + "` doesn't exists.");
        }

        try{
            freeDayTypeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }


}
