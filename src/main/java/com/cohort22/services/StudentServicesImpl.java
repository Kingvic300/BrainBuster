package com.cohort22.services;

import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.exceptions.GameNotActiveException;
import com.cohort22.exceptions.StudentNotFoundException;
import com.cohort22.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServicesImpl implements StudentServices {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public StudentResponse addNewStudent(StudentRequest studentRequest) {
        Student student = StudentMapper.mapToStudent(studentRequest);
        student.setId(UUID.randomUUID().toString());
        studentRepository.save(student);
        return StudentMapper.mapToStudentResponse("Student added successfully", student, student.getScore());
    }

    @Override
    public StudentResponse updateStudent(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        student.get().setUsername(studentRequest.getUsername());
        student.get().setEmail(studentRequest.getEmail());
        studentRepository.save(student.get());
        return StudentMapper.mapToStudentResponse("Student updated successfully", student.get(), student.get().getScore());
    }

    @Override
    public StudentResponse deleteStudent(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.delete(student.get());
        return StudentMapper.mapToStudentResponse("deleted Successfully",student.get(), student.get().getScore());
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
        return StudentMapper.mapToStudentResponse("Student Found", student.get(), student.get().getScore());
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

        return StudentMapper.mapToStudentResponse("Student found in active game",student, student.getScore());
    }

}
