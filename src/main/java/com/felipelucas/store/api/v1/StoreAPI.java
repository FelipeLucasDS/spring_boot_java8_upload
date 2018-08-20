package com.felipelucas.store.api.v1;

import com.felipelucas.commons.api.BaseRestController;
import com.felipelucas.store.api.dto.StoreDTO;
import com.felipelucas.store.service.StoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StoreAPI extends BaseRestController{

    @Autowired
    private StoreService storeService;

    @GetMapping(value="/store/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(storeService.getById(id));
    }

    @GetMapping(value="/store")
    public ResponseEntity<List<StoreDTO>> getStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @PutMapping(value="/store/{id}")
    public ResponseEntity updateStore(@PathVariable(value = "id") Long id,
                                      @RequestBody StoreDTO storeDTO) {
        return ResponseEntity.ok(storeService.updateStore(id, storeDTO));

    }

    @PostMapping(value="/store")
    public ResponseEntity<Long> createSingleStore(@PathVariable(value = "id") Long id,
                                                        @RequestBody StoreDTO storeDTO) {
        Long idSaved = storeService.createSingleStore(storeDTO);
        return ResponseEntity.ok(idSaved);
    }

    @PostMapping(value="/store/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity receiveFile(@RequestParam("file") MultipartFile file) {
        storeService.createFromFile(file);
        return ResponseEntity.ok().build();
    }
}