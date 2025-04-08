package com.cohort22.services;

import com.cohort22.dtos.request.OptionsRequest;
import com.cohort22.dtos.response.OptionsResponse;

public interface OptionsServices {
    OptionsResponse createOption(OptionsRequest optionsRequest);

    OptionsResponse updateOption(OptionsRequest optionsRequest);

    OptionsResponse deleteOption(OptionsRequest optionsRequest);
}
