package com.felipelucas.store.api.v1;

import com.felipelucas.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoreAPI {

    @Autowired
    private StoreService storeService;

    @GetMapping(value="/store/{id}")
    public ResponseEntity createSingleStore(@PathVariable(value = "id") Long id) {
        storeService.createSingleStore();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value="/store/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity receiveFile(@RequestParam("file") MultipartFile file,
                                      @PathVariable(value = "id") Long id) {
        storeService.readSingleStore(file);
        return ResponseEntity.ok().build();
    }
}