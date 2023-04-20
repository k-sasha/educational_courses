package com.alex.courses.service;

import com.alex.courses.dto.AdminRequestDto;
import com.alex.courses.dto.AdminResponseDto;
import com.alex.courses.dto.AdminUpdateDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.exseption_handling.NoSuchHumanException;
import com.alex.courses.repository.AdminRepository;
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

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks //i.e. upload this mocks (adminRepository and modelMapper)
    // to the service (adminService)
    private AdminServiceImpl adminService;

    // Test for the methods getAllAdmins() and getAdmin(Long id)
    @Test
    public void shouldReturnAllAdminsOrAdminById() {
        //given
        List<Administrator> admins = createAdmins();
        Mockito.when(adminRepository.findAll()).thenReturn(admins);

        List<AdminResponseDto> expectedAdminResponseDtos = createAdminDtos();

        Mockito.when(modelMapper.map(admins.get(0), AdminResponseDto.class)).thenReturn(expectedAdminResponseDtos.get(0));
        Mockito.when(modelMapper.map(admins.get(1), AdminResponseDto.class)).thenReturn(expectedAdminResponseDtos.get(1));

        //when
        List<AdminResponseDto> resultAdminResponseDtos = adminService.getAllAdmins();

        //then
        Assertions.assertEquals(2, resultAdminResponseDtos.size());
        Assertions.assertEquals(expectedAdminResponseDtos, resultAdminResponseDtos);
        Assertions.assertEquals(expectedAdminResponseDtos.get(0), (resultAdminResponseDtos.get(0)));
        Assertions.assertEquals(expectedAdminResponseDtos.get(1), (resultAdminResponseDtos.get(1)));
    }

    private List<Administrator> createAdmins() {
        Administrator admin1 = new Administrator(1L, "Anna"
                , "Smirnova", "anna@ya.ru");
        Administrator admin2 = new Administrator(2L, "Ivan"
                , "Ivanov", "ivan@gmail.com");
        return List.of(admin1, admin2);
    }

    private List<AdminResponseDto> createAdminDtos() {
        AdminResponseDto adminResponseDto1 = new AdminResponseDto(1L, "Anna"
                , "Smirnova");
        AdminResponseDto adminResponseDto2 = new AdminResponseDto(2L, "Ivan"
                , "Ivanov");
        return List.of(adminResponseDto1, adminResponseDto2);
    }


    // Test for the method saveAdmin(AdminRequestDto adminDto)
    @Test
    public void shouldSavedAdmin() {
        //given
        Administrator admin = new Administrator(1L, "Petr"
                , "Petrov", "petr@ya.ru");
        AdminRequestDto expectedAdminDto = new AdminRequestDto(admin.getName()
                , admin.getSurname(), admin.getEmail());

        Mockito.when(modelMapper.map(expectedAdminDto, Administrator.class)).thenReturn(admin);
        Mockito.when(adminRepository.save(admin)).thenReturn(admin);
        Mockito.when(modelMapper.map(admin, AdminRequestDto.class)).thenReturn(expectedAdminDto);

        //when
        AdminRequestDto resultSavedAdminDto = adminService.saveAdmin(expectedAdminDto);

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
        adminService.deleteAdmin(adminId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(adminRepository).findById(adminId);
        Mockito.verify(adminRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(adminId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowNoSuchHumanExceptionWhenAdminNotFound() {
        //given
        Long nonExistentAdminId = 3L;
        Mockito.when(adminRepository.findById(nonExistentAdminId)).thenReturn(Optional.empty());

        //when
        NoSuchHumanException exception = Assertions.assertThrows(NoSuchHumanException.class
                , () -> adminService.deleteAdmin(nonExistentAdminId));

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
        AdminUpdateDto resultUpdatedAdminDto = adminService.updateAdmin(adminId, updatedAdminDto);

        //then
        Assertions.assertEquals(updatedEmail, resultUpdatedAdminDto.getEmail());
        Mockito.verify(adminRepository).findById(adminId);
        Mockito.verify(adminRepository).save(existingAdmin);
        Mockito.verify(modelMapper).map(updatedAdmin, AdminUpdateDto.class);
    }

    @Test
    public void shouldThrowNoSuchHumanExceptionWhenUpdatingNonExistentAdmin() {
        //given
        Long nonExistentAdminId = 3L;
        Mockito.when(adminRepository.findById(nonExistentAdminId)).thenReturn(Optional.empty());

        AdminUpdateDto updatedAdminDto = new AdminUpdateDto();
        updatedAdminDto.setEmail("updated_email@example.com");

        //when
        NoSuchHumanException exception = Assertions.assertThrows(NoSuchHumanException.class
                , () -> adminService.updateAdmin(nonExistentAdminId, updatedAdminDto));

        //then
        Assertions.assertEquals("There is no admin with id = "
                + nonExistentAdminId, exception.getMessage());
    }


}
