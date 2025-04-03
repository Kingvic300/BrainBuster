package com.cohort22.services;

import com.cohort22.DTOS.request.OptionsRequest;
import com.cohort22.DTOS.response.OptionsResponse;
import com.cohort22.data.models.Options;
import com.cohort22.data.repositories.OptionsRepository;
import com.cohort22.exceptions.OptionsNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OptionsServicesImplTest {

    @Autowired
    private OptionsServices optionsServices;

    @Autowired
    private OptionsRepository optionsRepository;
    @Test
    public void testThatOptionCanBeCreated() {
        Options options = new Options();
        options.setText("yes");
        options.setIsCorrect(true);

        OptionsResponse response = optionsServices.createOption(options);

        assertNotNull(response);
        assertEquals("Options Created Successfully", response.getMessage());
    }

    @Test
    void testGetOptionById() {
        Options options = new Options();
        options.setText("yes");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsResponse response = optionsServices.getOptionById(options.getId());

        assertNotNull(response);
        assertEquals("Options Found", response.getMessage());
    }

    @Test
    void testGetOptionByIdNotFound() {
        assertThrows(OptionsNotFoundException.class, () -> optionsServices.getOptionById("123"));
    }

    @Test
    void testUpdateOption() {
        Options options = new Options();
        options.setText("yes");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest updateRequest = new OptionsRequest();
        updateRequest.setId(options.getId());
        updateRequest.setNewText("no");
        updateRequest.setIsCorrect(false);

        OptionsResponse response = optionsServices.updateOption(updateRequest);

        assertNotNull(response);
        assertEquals("Options Updated Successfully", response.getMessage());

    }

    @Test
    void testUpdateOptionNotFound() {
        OptionsRequest updateRequest = new OptionsRequest();
        updateRequest.setId("12");
        updateRequest.setNewText("no");
        updateRequest.setIsCorrect(true);

       Exception exception = assertThrows(OptionsNotFoundException.class, () -> optionsServices.updateOption(updateRequest));
       assertEquals("Option not found", exception.getMessage());
    }

    @Test
    void testDeleteOption() {
        Options options = new Options();
        options.setText("yes");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        optionsServices.deleteOption(options.getId());

        assertFalse(optionsRepository.existsById(options.getId()));
    }

    @Test
    void testDeleteOptionNotFound() {
        assertThrows(OptionsNotFoundException.class, () -> optionsServices.deleteOption("123"));
    }

}