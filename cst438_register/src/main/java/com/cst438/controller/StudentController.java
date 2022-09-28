package com.cst438.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;

import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	GradebookService gradebookService;
	
	@GetMapping("/student")
	public Student getStudent(@RequestParam("name") String name ) {
		System.out.println("/student called.");
		Student student = studentRepository.findByName(name);
		return student;
	}
	@PostMapping("/student")
	@Transactional
    public Student addStudent(@RequestBody Student student) {
		String email = student.getEmail();
		Student find= studentRepository.findByEmail(email);
		if(find == null) {
			studentRepository.save(student);
			return student;
		}else {
			System.out.println("/n student is in database" + email);
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student in database");
		}
    }
	@PutMapping("/student/{id}")
	@Transactional
	public void updateStatus(@RequestBody Student newstudent, @PathVariable("id") Integer student_id) {
		Student student = studentRepository.findById(student_id).orElse(null);
		if(student == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student not in database: "+ student_id);
		}

		student.setStatus(newstudent.getStatus());
		studentRepository.save(student);
		if(student.getStatus().equals("Hold")) {
			student.setStatusCode(1);
		}else if(student.getStatus().equals("Open")){
			student.setStatusCode(0);
		}
			
		
	}
	
}
