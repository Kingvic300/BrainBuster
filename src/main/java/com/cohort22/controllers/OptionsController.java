package com.cohort22.controllers;

import com.cohort22.dtos.request.OptionsRequest;
import com.cohort22.dtos.response.OptionsResponse;
import com.cohort22.services.OptionsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
public class OptionsController {

    private final OptionsServices optionsServices;

    @PostMapping("/create-option")
    public ResponseEntity<OptionsResponse> createOption(@RequestBody OptionsRequest optionsRequest) {
        return ResponseEntity.ok(optionsServices.createOption(optionsRequest));

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
