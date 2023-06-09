package com.alex.courses.service;

import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.entity.Course;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AdminRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.service.admin.AdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // for work annotations @Mock and @InjectMocks
public class AdminServiceImplTest {

    @InjectMocks //i.e. upload this mocks (adminRepository and modelMapper)
    // to the service (adminService)
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;

    // Test for the methods getAllAdmins() and getAdmin(Long id)
    @Test
    public void shouldReturnAllAdminsOrAdminById() {
        //given
        Administrator admin1 = new Administrator(1L, "Anna"
                , "Smirnova", "anna@ya.ru");
        Administrator admin2 = new Administrator(2L, "Ivan"
                , "Ivanov", "ivan@gmail.com");
        List<Administrator> admins = List.of(admin1, admin2);
        Mockito.when(adminRepository.findAll()).thenReturn(admins);

        AdminResponseDto adminDto1 = new AdminResponseDto(1L, "Anna", "Smirnova");
        Mockito.when(modelMapper.map(admins.get(0), AdminResponseDto.class))
                .thenReturn(adminDto1);
        AdminResponseDto adminDto2 = new AdminResponseDto(2L, "Ivan", "Ivanov");
        Mockito.when(modelMapper.map(admins.get(1), AdminResponseDto.class))
                .thenReturn(adminDto2);

        List<AdminResponseDto> expectedAdminResponseDtos = List.of(adminDto1, adminDto2);

        //when
        List<AdminResponseDto> resultAdminResponseDtos = adminService.getAll();

        //then
        Assertions.assertEquals(2, resultAdminResponseDtos.size());
        Assertions.assertEquals(expectedAdminResponseDtos, resultAdminResponseDtos);
        Assertions.assertEquals(expectedAdminResponseDtos.get(0), (resultAdminResponseDtos.get(0)));
        Assertions.assertEquals(expectedAdminResponseDtos.get(1), (resultAdminResponseDtos.get(1)));
    }


    // Test for the method saveAdmin(AdminRequestDto adminDto)
    @Test
    public void shouldSavedAdmin() {
        //given
        Administrator admin = new Administrator(1L, "Petr"
                , "Petrov", "petr@ya.ru");

        AdminRequestDto expectedAdminDto = new AdminRequestDto("Petr", "Petrov", "petr@ya.ru");
        Mockito.when(modelMapper.map(admin, AdminRequestDto.class))
                .thenReturn(expectedAdminDto);

        Mockito.when(modelMapper.map(expectedAdminDto, Administrator.class)).thenReturn(admin);
        Mockito.when(adminRepository.save(admin)).thenReturn(admin);

        //when
        AdminRequestDto resultSavedAdminDto = adminService.save(expectedAdminDto);

        //then
        Assertions.assertEquals(expectedAdminDto.getName(), resultSavedAdminDto.getName());
        Assertions.assertEquals(expectedAdminDto.getSurname(), resultSavedAdminDto.getSurname());
        Assertions.assertEquals(expectedAdminDto.getEmail(), resultSavedAdminDto.getEmail());
    }

    // Test for the method deleteAdmin(Long id)
    @Test
    public void shouldDeleteAdminById() {
        //given
        Long adminId = 1L;
        Administrator admin = new Administrator(adminId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Administrator> optionalAdmin = Optional.of(admin);

        Mockito.when(adminRepository.findById(adminId)).thenReturn(optionalAdmin);

        //when
        adminService.delete(adminId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(adminRepository).findById(adminId);
        Mockito.verify(adminRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(adminId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenAdminNotFound() {
        //given
        Long nonExistentAdminId = 10L;
        Mockito.when(adminRepository.findById(nonExistentAdminId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> adminService.delete(nonExistentAdminId));

        //then
        Assertions.assertEquals("There is no admin with id = "
                + nonExistentAdminId, exception.getMessage());
    }

    // Test for the method updateAdmin(Long id, AdminUpdateDto updatedAdminDto)
    @Test
    public void shouldUpdateAdmin() {
        //given
        Long adminId = 1L;
        Administrator existingAdmin = new Administrator(adminId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Administrator> optionalAdmin = Optional.of(existingAdmin);

        Mockito.when(adminRepository.findById(adminId)).thenReturn(optionalAdmin);

        String updatedEmail = "updated_anna@ya.ru";
        AdminUpdateDto updatedAdminDto = new AdminUpdateDto();
        updatedAdminDto.setEmail(updatedEmail);

        Administrator updatedAdmin = new Administrator(adminId, "Anna"
                , "Smirnova", updatedEmail);
        Mockito.when(adminRepository.save(existingAdmin)).thenReturn(updatedAdmin);
        Mockito.when(modelMapper.map(updatedAdmin, AdminUpdateDto.class)).thenReturn(updatedAdminDto);

        //when
        AdminUpdateDto resultUpdatedAdminDto = adminService.update(adminId, updatedAdminDto);

        //then
        Assertions.assertEquals(updatedEmail, resultUpdatedAdminDto.getEmail());
        Mockito.verify(adminRepository).findById(adminId);
        Mockito.verify(adminRepository).save(existingAdmin);
        Mockito.verify(modelMapper).map(updatedAdmin, AdminUpdateDto.class);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentAdmin() {
        //given
        Long nonExistentAdminId = 10L;
        Mockito.when(adminRepository.findById(nonExistentAdminId)).thenReturn(Optional.empty());

        AdminUpdateDto updatedAdminDto = new AdminUpdateDto();
        updatedAdminDto.setEmail("updated_email@example.com");

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> adminService.update(nonExistentAdminId, updatedAdminDto));

        //then
        Assertions.assertEquals("There is no admin with id = "
                + nonExistentAdminId, exception.getMessage());
    }


    // Test for the method addCourseToAdmin(Long admin_id, Long course_id)
    @Test
    public void shouldAddCourseToAdmin() {
        //given
        Long adminId = 1L;
        Long courseId = 1L;

        Administrator admin = new Administrator(adminId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Course course = new Course(courseId, "Course1", null);

        admin.getCourses().add(course);

        Mockito.when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(adminRepository.save(admin)).thenReturn(admin);

        AdminResponseDto adminDto = new AdminResponseDto(adminId, "Anna", "Smirnova");

        CourseResponseDto expectedCourseDto = new CourseResponseDto(courseId, "Course1", adminDto);
        Mockito.when(modelMapper.map(course, CourseResponseDto.class))
                .thenReturn(expectedCourseDto);

        //when
        CourseResponseDto resultCourseDto = adminService.addCourseToAdmin(adminId, courseId);

        //then
        Assertions.assertEquals(expectedCourseDto, resultCourseDto);
        Assertions.assertEquals(expectedCourseDto.getId(), resultCourseDto.getId());
        Assertions.assertEquals(expectedCourseDto.getName(), resultCourseDto.getName());
        Mockito.verify(adminRepository).findById(adminId);
        Mockito.verify(courseRepository).findById(courseId);
        Mockito.verify(courseRepository).save(course);
        Mockito.verify(adminRepository).save(admin);
    }
}
