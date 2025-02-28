package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Menu;
import com.caci.gaia_leave.administration.repository.request.MenuRepository;
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

    public ResponseEntity<String> create(Menu model) {
        Optional<Menu> check = menuRepository.findByName(model.getName());

        if (check.isPresent()) {
            throw new CustomException("Menu with name `" + model.getName() + "` already exists.");
        }
        try{
            menuRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<Menu>> read(){
        List<Menu> result = AllHelpers.listConverter(menuRepository.findAll());
        return result.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<Menu> readById(Integer id) {
        Optional<Menu> result = menuRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, Menu model) {
        if(menuRepository.existsById(id)) {
            throw new CustomException("Menu with id `" + id + "` already exists.");
        }
        Optional<Menu> unique = menuRepository.findById(id);
        if (unique.isPresent() && unique.get().getId().equals(id)) {
            throw new CustomException("Menu with id `" + id + "` already exists.");
        }
        try{
            model.setId(id);
            menuRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        if(!menuRepository.existsById(id)) {
            throw new CustomException("Menu with id `" + id + "` does not exist.");
        }
        try{
        menuRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }
}
