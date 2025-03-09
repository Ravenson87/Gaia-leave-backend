package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Languages;
import com.caci.gaia_leave.administration.model.response.LanguagesResponse;
import com.caci.gaia_leave.administration.repository.request.LanguagesRepository;
import com.caci.gaia_leave.administration.repository.response.LanguagesResponseRepository;
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
public class LanguagesService {

    private final LanguagesRepository languagesRepository;
    private final LanguagesResponseRepository languagesResponseRepository;

    public ResponseEntity<String> create(Languages model) {

        if (languagesRepository.existsByNameIgnoreCase(model.getName())) {
            throw new CustomException("Language name already exists");
        }

        if (languagesRepository.existsByCodeIgnoreCase(model.getCode())) {
            throw new CustomException("Language code already exists");
        }

        try {
            languagesRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created.");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<LanguagesResponse>> read() {
        List<LanguagesResponse> res = AllHelpers.listConverter(languagesResponseRepository.findAll());
        return res.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(res);
    }

    public ResponseEntity<String> update(Integer id, Languages model) {

        if (!languagesResponseRepository.existsById(id)) {
            throw new CustomException("Language id does not exist");
        }

        Optional<LanguagesResponse> name = languagesResponseRepository.findByNameIgnoreCase(model.getName());
        if (name.isPresent() && !name.get().getId().equals(id)) {
            throw new CustomException("Language with name ` " + model.getName() + " `already exists");
        }

        Optional<LanguagesResponse> code = languagesResponseRepository.findByCodeIgnoreCase(model.getCode());
        if (code.isPresent() && !code.get().getId().equals(id)) {
            throw new CustomException("Language with code `" + model.getCode() + " `already exists");
        }

        try {
            model.setId(id);
            languagesRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated.");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!languagesResponseRepository.existsById(id)) {
            throw new CustomException("Language with id doesn't exists");
        }

        try {
            languagesRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted.");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
