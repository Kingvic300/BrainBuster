package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.exceptions.AlreadyExistsException;
import com.cohort22.exceptions.GameNotActiveException;
import com.cohort22.exceptions.StudentNotFoundException;
import com.cohort22.exceptions.UserNotFoundException;
import com.cohort22.mappers.StudentMapper;
import com.cohort22.utils.JwtUtil;
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
    private final EmailServiceImpl emailServiceImpl;

    @Override
    public StudentResponse addNewStudent(StudentRequest studentRequest) {
        if (studentRepository.existsByUsername(studentRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }
        Student student = StudentMapper.mapToStudent(studentRequest);
        String encryptedPassword = passwordEncoder.encode(studentRequest.getPassword());
        student.setPassword(encryptedPassword);
        student.setId(UUID.randomUUID().toString());
        studentRepository.save(student);
        var jwtToken = jwtUtil.generateToken(student);
        return StudentMapper.mapToStudentResponse("Student added successfully", jwtToken);
    }
    @Override
    public StudentResponse loginUser(StudentRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Optional<Student> email = studentRepository.findByEmail(userRequest.getEmail());
        if (email.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        Student user = studentRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid Credentials");
        }
        var jwtToken = jwtUtil.generateToken(user);
        return StudentMapper.mapToStudentResponse("User Found",  jwtToken);
    }
    
    @Override
    public StudentResponse resetPassword(ChangePasswordRequest changePasswordRequest) {
       String email = jwtUtil.validateResetToken(changePasswordRequest.getToken());
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        student.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        studentRepository.save(student.get());
        return StudentMapper.mapToStudentResponse("Student Reset Password", changePasswordRequest.getToken());
    }
    @Override
    public void sendResetLink(String email){
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        String token = jwtUtil.generateResetToken(email);
        String restLink = "http://localhost:8080/student/reset-password?token=" + token;
        emailServiceImpl.sendResetPasswordEmail(email, restLink);
        StudentMapper.mapToStudentResponse("Sent successfully", token);
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
