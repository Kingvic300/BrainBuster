//package com.cohort22.services;
//
//import com.cohort22.DTOS.request.OptionsRequest;
//import com.cohort22.DTOS.response.OptionsResponse;
//import com.cohort22.data.models.Options;
//import com.cohort22.data.repositories.OptionsRepository;
//import com.cohort22.exceptions.OptionsNotFoundException;
//import com.cohort22.mappers.OptionsMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class OptionsServicesImpl implements OptionsServices {
//    @Autowired
//    private OptionsRepository optionRepository;
//
//    @Override
//    public OptionsResponse createOption(Options options) {
//        optionRepository.save(options);
//        return OptionsMapper.mapToOptionsResponse("Options Created Successfully",options);
//    }
//
//    @Override
//    public OptionsResponse getOptionById(Long id) {
//        Optional<Options> options = optionRepository.findById(id);
//        if(options.isEmpty()){
//            throw new OptionsNotFoundException("Option not found with id: " + id);
//        }
//        return OptionsMapper.mapToOptionsResponse("Options Found",options.get());
//    }
//
//    @Override
//    public OptionsResponse updateOption(OptionsRequest optionsRequest) {
//        Optional<Options> options= optionRepository.findById(optionsRequest.getId());
//        if(options.isEmpty()){
//            throw new OptionsNotFoundException("Option not found with id: " + optionsRequest.getId());
//        }
//        options.get().setText(optionsRequest.getText());
//        options.get().setIsCorrect(optionsRequest.getIsCorrect());
//        optionRepository.save(options.get());
//        return OptionsMapper.mapToOptionsResponse("Options Updated Successfully",options.get());
//    }
//
//    @Override
//    public void deleteOption(Long id) {
//       Optional<Options> options = optionRepository.findById(id);
//       if(options.isEmpty()){
//           throw new OptionsNotFoundException("Option not found with id: " + id);
//       }
//       optionRepository.delete(options.get());
//    }
//}
