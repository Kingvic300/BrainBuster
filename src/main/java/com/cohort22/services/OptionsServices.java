package com.cohort22.services;

import com.cohort22.DTOS.request.OptionsRequest;
import com.cohort22.DTOS.response.OptionsResponse;
import com.cohort22.data.models.Options;
import org.springframework.stereotype.Service;

public interface OptionsServices {
    OptionsResponse createOption(Options options);
    OptionsResponse getOptionById(String id);
    OptionsResponse updateOption(OptionsRequest optionsRequest);
    void deleteOption(String id);
}
