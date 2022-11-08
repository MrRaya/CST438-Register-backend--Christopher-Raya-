package com.cst438;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

public class EndToEndAddStudent {

	public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/Chris/desktop/chromedriver";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";

	public static final int TEST_COURSE_ID = 40443; 
	public static final String TEST_Name = "Raya"; 

	public static final String TEST_SEMESTER = "2021 Fall";

	public static final int SLEEP_DURATION = 1000; // 1 second.

	/*
	 * When running in @SpringBootTest environment, database repositories can be used
	 * with the actual database.
	 */
	
	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	public void addStudentTest() throws Exception {
	     Student x = null;
	        do {
	            x = studentRepository.findByEmail(TEST_USER_EMAIL);
	            if (x != null)
	                studentRepository.delete(x);
	        } while (x != null);
	        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
			WebDriver driver = new ChromeDriver();
			// Puts an Implicit wait for 10 seconds before throwing exception
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			try {

				driver.get(URL);
				Thread.sleep(SLEEP_DURATION);

				// selects the Add student button.
				
				WebElement we = driver.findElement(By.xpath("/html/body/div/div/div/div/a[2]"));
				we.click();




				// enter course no and click Add button
				
				driver.findElement(By.xpath("//input[@name='studName']")).sendKeys(TEST_Name);
				Thread.sleep(SLEEP_DURATION);
				
				driver.findElement(By.xpath("//input[@name='studEmail']")).sendKeys(TEST_USER_EMAIL);
				Thread.sleep(SLEEP_DURATION);
				
				driver.findElement(By.xpath("//input[@name='statCode']")).sendKeys("0");
				Thread.sleep(SLEEP_DURATION);

				/*
				* verify that new course shows in schedule.
				* get the title of all courses listed in schedule
				*/ 
			
				Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
				
				boolean found = false;
				
				assertNotNull(student, "student not found in database.");
				
				// verify that enrollment row has been inserted to database.
				
			} catch (Exception ex) {
				throw ex;
			} finally {

				// clean up database.
				
				Student s = studentRepository.findByEmail(TEST_USER_EMAIL);
				if (s != null)
					studentRepository.delete(s);
		
				driver.quit();
			}

	       
	}


}
