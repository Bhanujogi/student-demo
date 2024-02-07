package com.student.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.Service.StudentService;
import com.student.model.Student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "StudentController", description = "To perform operations on students")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	// http://localhost:8080/api/students

	@Operation(
			summary = "Post operations on student",
			description = "Used to save student object in database"
	)
	@PostMapping("/students")
	public ResponseEntity<Student> createStudent(@RequestBody Student student,
			@RequestHeader(value = "Authorization") String authorizationHeader) {

		// Use authorizationHeader as needed
		logger.info("Authorization header: {}", authorizationHeader);

		logger.info("Creating student: {}", student);
		try {
				ResponseEntity<Student> responseEntity = studentService.createStudent(student);
				
				return responseEntity;
			
		} catch (Exception e) {

			logger.error("Error creating student: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	
	}
	
	@Operation(
			summary = "GET operations on student by using student id",
			description = "Used to retrive student object from database using student id"
	)
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable("id") long id,
			@RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage) {

		// Use acceptLanguage as needed
		logger.info("Accept-Language header: {}", acceptLanguage);

		logger.info("Getting student with ID: {}", id);

		Optional<Student> student = studentService.getStudentById(id);

		if (student.isPresent()) {

			return new ResponseEntity<Student>(student.get(), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(
			summary = "GET operations on student",
			description = "Used to retrive student object from database"
	)
	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {

		try {
			List<Student> studentList = studentService.getAllStudents();
			logger.info("Retrieved all students: {}", studentList);

			return new ResponseEntity<List<Student>>((List<Student>) studentList, HttpStatus.OK);
		} catch (Exception e) {

			logger.error("Error retrieving all students: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Operation(
			summary = "PUT operations on student by using student id",
			description = "Used to update student object in database using student id"
	)
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {

		try {
			ResponseEntity<Student> studentRecord = studentService.updateStudent(id, student);
			logger.info("Updated student with ID {}: {}", id, student);

			return studentRecord;

		} catch (Exception e) {

			logger.error("Error updating student with ID {}: {}", id, e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id) {

		try {

			studentService.deleteStudent(id);
			logger.info("Deleted student with ID {}: {}", id);
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Error deleting student with ID {}: {}", id, e.getMessage());
			return new ResponseEntity<Student>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
