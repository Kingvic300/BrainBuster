package com.cohort22.services;

import com.cohort22.data.models.OTP;
import com.cohort22.data.repositories.OTPRepository;
import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.LoginRequest;
import com.cohort22.dtos.request.ResetPasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.StudentMapper;
import com.cohort22.utils.JwtUtil;
import com.cohort22.utils.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServicesImpl implements StudentServices {

    private final StudentRepository studentRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OTPRepository otpRepository;
    private static final int EXPIRATION_MINUTES = 30;

    @Override
    public StudentResponse addNewStudent(StudentRequest studentRequest) {
        if (studentRepository.existsByUsername(studentRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }
        if(studentRepository.findByEmail(studentRequest.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already exists");
        }
        Student student = StudentMapper.mapToStudent(studentRequest);
        String encryptedPassword = passwordEncoder.encode(studentRequest.getPassword());
        student.setPassword(encryptedPassword);
        studentRepository.save(student);
        var jwtToken = jwtUtil.generateToken(student);
        return StudentMapper.mapToStudentResponse("Student added successfully", jwtToken);
    }
    @Override
    public StudentResponse loginUser(LoginRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Student user = studentRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("UserName Not Found"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new StudentNotFoundException("Invalid Credentials");
        }
        var jwtToken = jwtUtil.generateToken(user);
        return StudentMapper.mapToStudentResponse("User was successfully login",  jwtToken);
    }
    
    @Override
    public StudentResponse resetPassword(ChangePasswordRequest changePasswordRequest) {
        Optional<OTP> otp = otpRepository.findByOtp(changePasswordRequest.getOtp());
        if(otp.isEmpty()){
            throw new OTPNotFoundException("OTP not found");
        }
        Student student = otp.get().getStudent();
        if(student.getPassword().equals(changePasswordRequest.getNewPassword())){
            throw new PasswordMatchesException("Password matches old password");
        }
        student.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        studentRepository.save(student);
        return StudentMapper.mapToStudentResponse("Reset Password was successful", changePasswordRequest.getOtp());
    }
    @Override
    public StudentResponse sendResetLink(ResetPasswordRequest resetPasswordRequest){
        Optional<Student> student = studentRepository.findByEmail(resetPasswordRequest.getEmail());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        OTP otp = new OTP();
        otp.setId(UUID.randomUUID().toString());
        otp.setOtp(OTPGenerator.generateOtp());
        otp.setExpiresAt(EXPIRATION_MINUTES);
        otp.setStudent(student.get());
        otpRepository.save(otp);
        emailService.sendResetPasswordEmail(resetPasswordRequest.getEmail(), otp.getOtp());
        return StudentMapper.mapToStudentResponse("Sent successfully", null);
    }

    @Override
    public StudentResponse deleteStudent(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.delete(student.get());
        var jwtToken = jwtUtil.generateToken(student.get());
        return StudentMapper.mapToStudentResponse("deleted Successfully", jwtToken);
    }

    @Override
    public StudentResponse getStudentByName(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        if(!student.get().getUsername().equals(studentRequest.getUsername())){
            throw new StudentNotFoundException("Student not found with this username");

        }
        var jwtToken = jwtUtil.generateToken(student.get());
        return StudentMapper.mapToStudentResponse("Student Found", jwtToken);
    }

    @Override
    public StudentResponse findStudentInGameById(StudentRequest studentRequest) {
        Student student = studentRepository.findByUsername(studentRequest.getUsername())
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with username: " + studentRequest.getUsername()));

        List<Game> studentGames = gameRepository.findByStudentIdsContainingAndStatus(student.getId(), GameStatus.IN_PROGRESS);

        if (studentGames.isEmpty()) {
            boolean isInAnyGame = gameRepository.existsByStudentIdsContaining(student.getId());
            if (isInAnyGame) {
                throw new GameNotActiveException("Game is not currently in progress");
            } else {
                throw new GameNotActiveException("Student is not in any game");
            }
        }
        var jwtToken = jwtUtil.generateToken(student);
        return StudentMapper.mapToStudentResponse("Student found in active game", jwtToken);
    }

}
