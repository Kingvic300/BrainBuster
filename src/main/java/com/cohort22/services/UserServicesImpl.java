package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.data.models.Student;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.models.User;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.data.repositories.UserRepository;
import com.cohort22.exceptions.StudentNotFoundException;
import com.cohort22.exceptions.TeacherNotFoundException;
import com.cohort22.exceptions.UserNotFoundException;
import com.cohort22.mappers.StudentMapper;
import com.cohort22.mappers.TeacherMapper;
import com.cohort22.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public UserResponse createUser(User user) {
        userRepository.save(user);
        return UserMapper.mapToUserResponse("User Created Successfully", user);
    }

    @Override
    public void deleteUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }
        userRepository.delete(user.get());
    }

    @Override
    public UserResponse getUserById(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return UserMapper.mapToUserResponse("User Found", user.get());
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
    public TeacherResponse getTeacherByName(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        return TeacherMapper.mapToTeacherResponse("Teacher Found", teacher.get());    }
}
