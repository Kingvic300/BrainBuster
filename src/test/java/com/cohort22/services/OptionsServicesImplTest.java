package com.cohort22.services;

import com.cohort22.dtos.request.OptionsRequest;
import com.cohort22.dtos.response.OptionsResponse;
import com.cohort22.data.models.Options;
import com.cohort22.data.repositories.OptionsRepository;
import com.cohort22.exceptions.OptionsNotFoundException;
import org.junit.jupiter.api.AfterEach;
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
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);
        OptionsRequest request = new OptionsRequest();
        request.setQuestionId("123");
        request.setIsCorrect(true);
        request.setNewText("yes");
        OptionsResponse response = optionsServices.createOption(request);

        assertNotNull(response);
        assertEquals("Options Created Successfully", response.getMessage());
    }

    @Test
    void testGetOptionById() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest optionsRequest = new OptionsRequest();
        optionsRequest.setQuestionId(options.getQuestionId());
        optionsRequest.setNewText(options.getText());
        optionsRequest.setIsCorrect(options.getIsCorrect());
        OptionsResponse response = optionsServices.getOptionById(optionsRequest);

        assertNotNull(response);
        assertEquals("Options Found", response.getMessage());
    }

    @Test
    void testGetOptionByIdNotFound() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest request = new OptionsRequest();
        request.setQuestionId("3333");
        assertThrows(OptionsNotFoundException.class, () -> optionsServices.getOptionById(request));
    }

    @Test
    void testUpdateOption() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest updateRequest = new OptionsRequest();
        updateRequest.setQuestionId(options.getQuestionId());
        updateRequest.setNewText("no");
        updateRequest.setIsCorrect(false);

        OptionsResponse response = optionsServices.updateOption(updateRequest);

        assertNotNull(response);
        assertEquals("Options Updated Successfully", response.getMessage());

    }

    @Test
    void testUpdateOptionNotFound() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest updateRequest = new OptionsRequest();
        updateRequest.setQuestionId("3333");

       Exception exception = assertThrows(OptionsNotFoundException.class, () -> optionsServices.updateOption(updateRequest));
       assertEquals("Option not found", exception.getMessage());
    }

    @Test
    void testDeleteOption() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("1234");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest deleteRequest = new OptionsRequest();
        deleteRequest.setQuestionId(options.getQuestionId());
        deleteRequest.setNewText("yes");
        deleteRequest.setIsCorrect(true);

        optionsServices.deleteOption(deleteRequest);

        assertFalse(optionsRepository.existsById(options.getId()));
    }

    @Test
    void testDeleteOptionNotFound() {
        Options options = new Options();
        options.setText("yes");
        options.setQuestionId("123");
        options.setIsCorrect(true);
        optionsRepository.save(options);

        OptionsRequest deleteRequest = new OptionsRequest();
        deleteRequest.setQuestionId("3333");
        assertThrows(OptionsNotFoundException.class, () -> optionsServices.deleteOption(deleteRequest));
    }
    @AfterEach
    public void tearDown(){
        optionsRepository.deleteAll();

    }

}