package com.cohort22.services;

import com.cohort22.DTOS.request.OptionsRequest;
import com.cohort22.DTOS.response.OptionsResponse;
import com.cohort22.data.models.Options;
import com.cohort22.data.repositories.OptionsRepository;
import com.cohort22.exceptions.OptionsNotFoundException;
import com.cohort22.mappers.OptionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OptionsServicesImpl implements OptionsServices {
    @Autowired
    private OptionsRepository optionRepository;

    @Override
    public OptionsResponse createOption(OptionsRequest optionsRequest) {
        Options options = new Options();
        options.setQuestionId(optionsRequest.getQuestionId());
        options.setText(optionsRequest.getNewText());
        options.setIsCorrect(optionsRequest.getIsCorrect());
        optionRepository.save(options);
        return OptionsMapper.mapToOptionsResponse("Options Created Successfully",options);
    }

    @Override
    public OptionsResponse getOptionById(OptionsRequest optionsRequest) {
        Optional<Options> options = optionRepository.findByQuestionId(optionsRequest.getQuestionId());
        if(options.isEmpty()){
            throw new OptionsNotFoundException("Option not found with id");
        }
        return OptionsMapper.mapToOptionsResponse("Options Found",options.get());
    }

    @Override
    public OptionsResponse updateOption(OptionsRequest optionsRequest) {
        Optional<Options> existingOptions = optionRepository.findByQuestionId(optionsRequest.getQuestionId());
        if(existingOptions.isEmpty()){
            throw new OptionsNotFoundException("Option not found");
        }
        Options options = existingOptions.get();
        options.setText(optionsRequest.getNewText());
        options.setIsCorrect(optionsRequest.getIsCorrect());
        optionRepository.save(options);
        return OptionsMapper.mapToOptionsResponse("Options Updated Successfully",options);
    }

    @Override
    public OptionsResponse deleteOption(OptionsRequest optionsRequest) {
       Optional<Options> options = optionRepository.findByQuestionId(optionsRequest.getQuestionId());
       if(options.isEmpty()){
           throw new OptionsNotFoundException("Option not found with id: ");
       }
       optionRepository.delete(options.get());
       return OptionsMapper.mapToOptionsResponse("Options Deleted Successfully",options.get());
    }
}
