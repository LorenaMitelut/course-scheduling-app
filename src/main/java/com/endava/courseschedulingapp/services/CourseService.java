package com.endava.courseschedulingapp.services;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.User;

import java.util.List;

public interface CourseService {
    Course addCourse(String userName, Course course);

    Course getCourseById(long id);

    List<Course> getCourseByName(String name);

    List<Course> getAllCourses();

    Course updateCourse(Course course);

    void deleteCourse(long id);

    Course assignLoggedUserToCourse(Long courseId);

    Course assignUserToCourse(Long courseId, String userName);

    List<User> assignedUsersToCourse(Long courseId);
}
