package com.student.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.student.model.Student;

public interface StudentService {
	
	
	public ResponseEntity<Student> createStudent(Student student);

	public Optional<Student> getStudentById(long id);
	
	public List<Student> getAllStudents();
	
	public ResponseEntity<Student> updateStudent(long id, Student student);

	public void deleteStudent(long id);

	
}
