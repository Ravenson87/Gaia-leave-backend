package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Menu;
import com.caci.gaia_leave.administration.model.response.MenuResponse;
import com.caci.gaia_leave.administration.repository.request.MenuRepository;
import com.caci.gaia_leave.administration.repository.response.MenuResponseRepository;
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
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuResponseRepository menuResponseRepository;

    /**
     * Create Menu in database
     *
     * @param models Menu
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> create(List<Menu> models) {
        if(models == null || models.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        models.forEach(model -> {
        if (menuRepository.existsByName(model.getName())) {
            throw new CustomException("Menu with name `" + model.getName() + "` already exists.");
        }

        if (menuRepository.existsByMenuNumber(model.getMenuNumber())) {
            throw new CustomException("Menu number `" + model.getMenuNumber() + "` already exists.");
        }
        });
        try {
            menuRepository.saveAll(models);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Read Menu from database
     *
     * @return ResponseEntity<List<MenuResponse>>
     */
    public ResponseEntity<List<MenuResponse>> read() {
        List<MenuResponse> result = AllHelpers.listConverter(menuResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read Menu by id in database
     *
     * @param id Integer
     * @return ResponseEntity<MenuResponse>
     */
    public ResponseEntity<MenuResponse> readById(Integer id) {
        Optional<MenuResponse> result = menuResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Update Menu in database
     *
     * @param id Integer
     * @param model Menu
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> update(Integer id, Menu model) {

        if (!menuRepository.existsById(id)) {
            throw new CustomException("Menu with id `" + id + "` doesn't exists.");
        }

        Optional<Menu> uniqueName = menuRepository.findByName(model.getName());
        if (uniqueName.isPresent() && !uniqueName.get().getId().equals(id)) {
            throw new CustomException("Menu with name `" + model.getName() + "` already exists.");
        }

        Optional<Menu> uniqueMenuNumber = menuRepository.findByMenuNumber(model.getMenuNumber());
        if (uniqueMenuNumber.isPresent() && !uniqueMenuNumber.get().getId().equals(id)) {
            throw new CustomException("Menu with menu_number `" + model.getMenuNumber() + "` already exists.");
        }


        try {
            model.setId(id);
            menuRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Delete Menu by id in database
     *
     * @param id Integer
     * @return ResponseEntity<HttpStatus>
     */
    public ResponseEntity<HttpStatus> delete(Integer id) {

        if (!menuRepository.existsById(id)) {
            throw new CustomException("Menu with id `" + id + "` does not exist.");
        }

        try {
            menuRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }
}
