package com.cohort22.services;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.exceptions.StudentNotFoundException;
import com.cohort22.exceptions.TeacherNotFoundException;
import com.cohort22.mappers.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServicesImpl implements TeacherServices {

    @Autowired
    private  TeacherRepository teacherRepository;

    @Override
    public TeacherResponse createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return TeacherMapper.mapToTeacherResponse("Teacher Created Successfully", teacher);
    }

    @Override
    public TeacherResponse updateTeacher(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherRequest.getId());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        teacher.get().setUsername(teacherRequest.getUsername());
        teacher.get().setEmail(teacherRequest.getEmail());
        teacherRepository.save(teacher.get());
        return TeacherMapper.mapToTeacherResponse("Teacher updated successfully", teacher.get());

    }

    @Override
    public void deleteTeacher(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherRequest.getId());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        teacherRepository.delete(teacher.get());
    }

    @Override
    public TeacherResponse getTeacherByName(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherRequest.getId());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        if(!teacher.get().getUsername().equals(teacherRequest.getUsername())){
            throw new TeacherNotFoundException("Teacher not found");
        }
        return TeacherMapper.mapToTeacherResponse("Teacher Found", teacher.get());

    }
}
