package com.cohort22.mappers;

import com.cohort22.dtos.request.OptionsRequest;
import com.cohort22.dtos.response.OptionsResponse;
import com.cohort22.data.models.Options;

public class OptionsMapper {
    public static Options mapToOptions(OptionsRequest optionsRequest) {
        Options options = new Options();
        options.setText(optionsRequest.getText());
        options.setIsCorrect(optionsRequest.getIsCorrect());
        options.setQuestionId(optionsRequest.getQuestionId());
        return options;
    }
    public static OptionsResponse mapToOptionsResponse(String message, String text) {
        OptionsResponse optionsResponse = new OptionsResponse();
        optionsResponse.setMessage(message);
        optionsResponse.setAnswerText(text);
        return optionsResponse;
    }
}
