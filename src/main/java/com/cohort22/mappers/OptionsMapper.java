package com.cohort22.mappers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.request.OptionsRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.DTOS.response.OptionsResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Options;
import com.cohort22.data.models.Question;

public class OptionsMapper {
    public static Options mapToOptions(OptionsRequest optionsRequest) {
        Options options = new Options();
        options.setText(optionsRequest.getNewText());
        options.setQuestionId(optionsRequest.getQuestionId());
        options.setIsCorrect(optionsRequest.getIsCorrect());
        return options;
    }
    public static OptionsResponse mapToOptionsResponse(String message, Options options) {
        OptionsResponse optionsResponse = new OptionsResponse();
        optionsResponse.setMessage(message);
        optionsResponse.setAnswerText(options.getText());
        return optionsResponse;
    }
}
