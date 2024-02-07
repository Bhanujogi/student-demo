package com.student.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.Repository.StudentRepository;
import com.student.model.Student;

@Service
public class StudentServiceImpl implements StudentService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	

	@Override
	public ResponseEntity<Student> createStudent(Student student) {
		
		
		try {
			Student _student= studentRepository.save(student);
			LOGGER.info("saving the created student with name: {}", _student.getFirstName());
			
			return new ResponseEntity<Student>(_student, HttpStatus.CREATED);
			
		}catch(Exception e)
		{	
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
	}
	



	@Override
	public Optional<Student> getStudentById(long id) {

		Optional<Student> student = studentRepository.findById(id);
		
		return student;
	}



	@Override
	public List<Student> getAllStudents() {
		
		List<Student> stuList = studentRepository.findAll();
		return stuList;
	}



	@Override
	public ResponseEntity<Student> updateStudent(long id, Student student) {

		Optional<Student> studentRecord = studentRepository.findById(id);
		
		if(studentRecord.isPresent()) {
			
			Student _student = studentRecord.get();
			
			_student.setFirstName(student.getFirstName());
			_student.setLastName(student.getLastName());
			_student.setAge(student.getAge());
			_student.setAddress(student.getAddress());
			_student.setEmailId(student.getEmailId());
			_student.setGraduation(student.getGraduation());
			_student.setBranch(student.getBranch());
			_student.setPincode(student.getPincode());
			_student.setState(student.getState());
			
			return new ResponseEntity<Student>(studentRepository.save(_student), HttpStatus.OK);
		}else{
		
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}



	@Override
	public void deleteStudent(long id) {

		studentRepository.deleteById(id);
	}
	
	
	
	
	

}
