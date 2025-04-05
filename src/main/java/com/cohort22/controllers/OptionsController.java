package com.cohort22.controllers;

import com.cohort22.DTOS.request.OptionsRequest;
import com.cohort22.DTOS.response.OptionsResponse;
import com.cohort22.data.models.Options;
import com.cohort22.services.OptionsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/options")
public class OptionsController {
    @Autowired
    private OptionsServices optionsServices;

    @PostMapping("/create-option")
    public ResponseEntity<OptionsResponse> createOption(@RequestBody OptionsRequest optionsRequest) {
        return ResponseEntity.ok(optionsServices.createOption(optionsRequest));

    }
    @GetMapping("/get-option")
    public ResponseEntity<OptionsResponse> getOption(@RequestBody OptionsRequest request) {
        return ResponseEntity.ok(optionsServices.getOptionById(request));
    }
    @PostMapping("/update-option")
    public ResponseEntity<OptionsResponse> updateOption(@RequestBody OptionsRequest request) {
        return ResponseEntity.ok(optionsServices.updateOption(request));
    }
    @DeleteMapping("/delete-option")
    public ResponseEntity<OptionsResponse> deleteOption(@RequestBody OptionsRequest request) {
        return ResponseEntity.ok(optionsServices.deleteOption(request));
    }
}
