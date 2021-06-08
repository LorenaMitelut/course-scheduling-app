package com.endava.courseschedulingapp.services;

import com.endava.courseschedulingapp.entities.Course;
import com.endava.courseschedulingapp.entities.User;
import com.endava.courseschedulingapp.repositories.CourseRepository;
import com.endava.courseschedulingapp.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @Description("Should return a course by id")
    void shouldReturnACourseById() {
        long courseId = 66;

        Course courseMock = new Course();
        courseMock.setId(courseId);
        courseMock.setName("Course Test");
        courseMock.setDescription("Description Test");
        courseMock.setDate(LocalDateTime.now());
        courseMock.setLength(2D);
        courseMock.setUser(List.of(new User()));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseMock));

        Course course = courseService.getCourseById(courseId);

        assertNotNull(course);
        assertEquals(courseId, course.getId());
        assertEquals(courseMock, course);
    }

    @Test
    @Description("Should return the name of the course")
    void shouldReturnACourseByName() {
        String courseName = "Course Name";

        Course courseMock = new Course();
        courseMock.setName("Course Name 1");
        courseMock.setId(21l);
        courseMock.setDescription("Description Test2");
        courseMock.setDate(LocalDateTime.now());
        courseMock.setLength(3D);
        courseMock.setUser(List.of(new User()));

        Course courseMock2 = new Course();
        courseMock2.setName("Course Name 2");
        courseMock2.setId(22l);
        courseMock2.setDescription("Description Test2");
        courseMock2.setDate(LocalDateTime.now());
        courseMock2.setLength(3D);
        courseMock2.setUser(List.of(new User()));

        when(courseRepository.findByNameContainingIgnoreCase(courseName)).thenReturn(List.of(courseMock, courseMock2));

        List<Course> courseList = courseService.getCourseByName(courseName);

        assertNotNull(courseList);
        assertEquals(2, courseList.size());
    }
}
