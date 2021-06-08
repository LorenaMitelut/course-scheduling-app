package com.endava.courseschedulingapp.services.impl;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.CourseStatus;
import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.entities.UserRole;
import com.endava.courseschedulingapp.exceptions.NotFoundException;
import com.endava.courseschedulingapp.repositories.CourseRepository;
import com.endava.courseschedulingapp.repositories.UserRepository;
import com.endava.courseschedulingapp.services.CourseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Course addCourse(String userName, Course course) {
        User user = userRepository.findUserByUsername(userName)
                .filter(u -> u.getRole().equals(UserRole.TRAINER.toDBRole()))
                .orElseThrow(() -> new NotFoundException("No Trainer found for username: " + userName));
        course.setUser(List.of(user));
        course.setStatus(CourseStatus.OPEN.toString());
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Course not found"));
    }

    @Override
    public List<Course> getCourseByName(String name) {
        return courseRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course updateCourse(Course course) {
        Course savedCourse = getCourseById(course.getId());

        Optional.ofNullable(course.getDescription()).ifPresent(savedCourse::setDescription);
        Optional.ofNullable(course.getName()).ifPresent(savedCourse::setName);

        return courseRepository.save(savedCourse);
    }

    @Override
    public void deleteCourse(long id) {
        Course currentCourse = getCourseById(id);
        courseRepository.delete(currentCourse);
    }

    @Override
    public Course assignLoggedUserToCourse(Long courseId) {
        Course course = courseRepository.getById(courseId);

        String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        List<User> currentAssignedUserList = course.getUser();

        List<User> updatedList = new ArrayList<>();
        updatedList.addAll(currentAssignedUserList);
        userRepository.findUserByUsername(loggedUserName).ifPresent(updatedList::add);

        course.setUser(updatedList);
        return courseRepository.save(course);
    }

    @Override
    public Course assignUserToCourse(Long courseId, String userName) {
        Course course = courseRepository.getById(courseId);

        List<User> userList = course.getUser();

        List<User> updatedList = new ArrayList<>();
        updatedList.addAll(userList);
        userRepository.findUserByUsername(userName).ifPresent(updatedList::add);

        course.setUser(updatedList);
        return courseRepository.save(course);
    }

    @Override
    public List<User> assignedUsersToCourse(Long courseId) {
        Course course = courseRepository.getById(courseId);
        return course.getUser();
    }
}
