package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.response.StudentResponse;
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

@Service
public class StudentServicesImpl implements StudentServices {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public StudentResponse addNewStudent(StudentRequest studentRequest) {
        Student student = StudentMapper.mapToStudent(studentRequest);
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
    public StudentResponse deleteStudent(StudentRequest studentRequest) {
        Optional<Student> student = studentRepository.findByUsername(studentRequest.getUsername());
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.delete(student.get());
        return StudentMapper.mapToStudentResponse("deleted Successfully",student.get());
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
        return StudentMapper.mapToStudentResponse("Student Found", student.get());
    }

    @Override
    public StudentResponse findStudentInGameById(StudentRequest studentRequest) {
        Student student = studentRepository.findByUsername(studentRequest.getUsername())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        List<Game> studentGames = gameRepository.findByStudentsContaining(student);
        if (studentGames.isEmpty()) {
            throw new GameNotActiveException("Game not found");
        }
        if(studentGames.getFirst().getStatus() != GameStatus.IN_PROGRESS){
            throw new GameNotActiveException("Game may have been completed");
        }
        for(Student students : studentGames.getFirst().getStudents()) {
            if(students.getUsername().equals(studentRequest.getUsername())) {
                return StudentMapper.mapToStudentResponse("Student Found", students);
            }
        }
        throw new StudentNotFoundException("Student not found");
    }

}
