package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.exceptions.StudentNotFoundException;
import com.cohort22.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServicesImpl implements StudentServices {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public StudentResponse addNewStudent(Student student) {
        studentRepository.save(student);
        return StudentMapper.mapToStudentResponse("Student added successfully", student);
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
        return StudentMapper.mapToStudentResponse("Student updated successfully", student.get());
    }

    @Override
    public void deleteStudent(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.delete(student.get());
    }

    @Override
    public StudentResponse getStudentByName(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        return StudentMapper.mapToStudentResponse("Student Found", student.get());
    }

    @Override
    public StudentResponse findStudentInGameById(StudentRequest studentRequest) {
        Optional<Game> game = gameRepository.findById(studentRequest.getGameId());
        if (game.isEmpty()) {
            throw new StudentNotFoundException("Game not found");
        }
        for(Student student : game.get().getStudents()) {
            if(student.getUsername().equals(studentRequest.getUsername())) {
                return StudentMapper.mapToStudentResponse("Student Found", student);
            }
        }
        throw new StudentNotFoundException("Student not found");
    }

}
