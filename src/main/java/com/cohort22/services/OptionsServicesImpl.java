package com.cohort22.services;

import com.cohort22.dtos.request.OptionsRequest;
import com.cohort22.dtos.response.OptionsResponse;
import com.cohort22.data.models.Options;
import com.cohort22.data.repositories.OptionsRepository;
import com.cohort22.exceptions.OptionsNotFoundException;
import com.cohort22.mappers.OptionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OptionsServicesImpl implements OptionsServices {

    private final OptionsRepository optionRepository;

    @Override
    public OptionsResponse createOption(OptionsRequest optionsRequest) {
        Options options = OptionsMapper.mapToOptions(optionsRequest);
        options.setId(UUID.randomUUID().toString());
        optionRepository.save(options);
        return OptionsMapper.mapToOptionsResponse("Options Created Successfully",options.toString());
    }

    @Override
    public OptionsResponse updateOption(OptionsRequest optionsRequest) {
       Optional<Options> existingOptions = optionRepository.findOptionsByText(optionsRequest.getText());
       if (existingOptions.isEmpty()) {
           throw new OptionsNotFoundException("Option Not Found");
       }
        Options options = existingOptions.get();
        options.setText(optionsRequest.getText());
        options.setIsCorrect(optionsRequest.getIsCorrect());
        optionRepository.save(options);
        return OptionsMapper.mapToOptionsResponse("Options Updated Successfully",options.getText());
    }

    @Override
    public OptionsResponse deleteOption(OptionsRequest optionsRequest) {
       Optional<Options> options = optionRepository.findOptionsByText(optionsRequest.getText());
       if(options.isEmpty()){
           throw new OptionsNotFoundException("Option not found with id: ");
       }
       optionRepository.delete(options.get());
       return OptionsMapper.mapToOptionsResponse("Options Deleted Successfully",options.get().getText());
    }
}
