package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.exceptions.AlreadyExistsException;
import com.cohort22.exceptions.TeacherNotFoundException;
import com.cohort22.exceptions.UserNotFoundException;
import com.cohort22.mappers.TeacherMapper;
import com.cohort22.utils.JwtUtil;
import com.cohort22.utils.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServicesImpl implements TeacherServices {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public TeacherResponse createTeacher(TeacherRequest teacherRequest) {
        if (teacherRepository.existsByUsername(teacherRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }
        Teacher teacher = TeacherMapper.mapToTeacher(teacherRequest);
        String encryptedPassword = passwordEncoder.encode(teacherRequest.getPassword());
        teacher.setPassword(encryptedPassword);
        teacherRepository.save(teacher);
        var jwtToken = jwtUtil.generateToken(teacher);
        return TeacherMapper.mapToTeacherResponse("Teacher Created Successfully", jwtToken);
    }
    @Override
    public TeacherResponse loginUser(TeacherRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Optional<Teacher> email = teacherRepository.findByEmail(userRequest.getEmail());
        if(email.isEmpty()){
            throw new TeacherNotFoundException("Teacher not found");
        }

        Teacher user = teacherRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid Credentials");
        }
        var jwtToken = jwtUtil.generateToken(user);
        return TeacherMapper.mapToTeacherResponse("User Found", jwtToken);
    }
    @Override
    public TeacherResponse resetPassword(ChangePasswordRequest changePasswordRequest) {
        Optional<Teacher> teacher = teacherRepository.findByEmail(changePasswordRequest.getEmail());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Student not found");
        }
        teacher.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        teacherRepository.save(teacher.get());
        return TeacherMapper.mapToTeacherResponse("Teacher Reset Password", changePasswordRequest.getOtp());
    }
    @Override
    public void sendResetLink(String email){
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Student not found");
        }
        String url = OTPGenerator.generateOtp();
        emailService.sendResetPasswordEmail(email, url);
        TeacherMapper.mapToTeacherResponse("Teacher Reset Link Sent", url);
    }

    @Override
    public TeacherResponse deleteTeacher(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        teacherRepository.delete(teacher.get());
        return TeacherMapper.mapToTeacherResponse("Teacher deleted successfully", teacher.get().getUsername());
    }

    @Override
    public TeacherResponse getTeacherByName(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        return TeacherMapper.mapToTeacherResponse("Teacher Found", teacher.get().getUsername());

    }
}
