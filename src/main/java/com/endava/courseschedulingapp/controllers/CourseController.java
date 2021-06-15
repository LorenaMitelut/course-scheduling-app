package com.endava.courseschedulingapp.controllers;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.CourseStatus;
import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.services.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@Api(tags = "Courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * POST request to add new courses
     **/
    @PostMapping("/{userName}")
    @ApiOperation(value = "Add a new course")
    public ResponseEntity<Course> addCourse(@PathVariable String userName, @RequestBody Course course) {
        Course savedCourse = courseService.addCourse(userName, course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    /**
     * GET request to return all courses
     **/
    @GetMapping
    @ApiOperation(value = "Get all registered courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courseList = courseService.getAllCourses();
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    /**
     * GET request to return specific courses
     **/
    @GetMapping("/{id}")
    @ApiOperation(value = "Get a Course by Id")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course courseById = courseService.getCourseById(id);
        return new ResponseEntity<>(courseById, HttpStatus.OK);
    }

    /**
     * GET request to return specific courses based on name
     **/
    @GetMapping("name/{name}")
    @ApiOperation(value = "Get all courses by name")
    public ResponseEntity<List<Course>> getCourse(@PathVariable String name) {
        List<Course> courseByName = courseService.getCourseByName(name);
        return new ResponseEntity<>(courseByName, HttpStatus.OK);
    }

    /**
     * PUT request to update courses
     **/
    @PutMapping("/")
    @ApiOperation(value = "Update Course")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    /**
     * DELETE request to delete specific courses
     **/
    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete a Course by Id")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/available")
    @ApiOperation(value = "Get all available courses (status Open)")
    public ResponseEntity<List<Course>> allOpenCourses() {
        List<Course> courseList = courseService.getAllCourses()
                .stream()
                .filter(course -> course.getStatus().equals(CourseStatus.OPEN.toString()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @PutMapping("/user/{courseId}")
    @ApiOperation(value = "Assign Logged User to Course")
    public ResponseEntity<Course> assignUserToCourse(@PathVariable Long courseId) {
        Course course = courseService.assignLoggedUserToCourse(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/user/{courseId}/{userName}")
    @ApiOperation(value = "Assign an User to a Course by Username")
    public ResponseEntity<Course> assignUserToCourseByUsername(@PathVariable Long courseId, @PathVariable String userName) {
        Course course = courseService.assignUserToCourse(courseId, userName);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/users/{courseId}")
    @ApiOperation(value = "Get all assigned users to course")
    public ResponseEntity<List<User>> assignedUsersToCourse(@PathVariable Long courseId) {
        List<User> userList = courseService.assignedUsersToCourse(courseId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
