package com.alex.courses.service;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.service.access.AccessServiceImpl;
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
public class AccessServiceImplTest {

    @InjectMocks //i.e. upload this mocks (accessRepository and modelMapper)
    // to the service (accessService)
    private AccessServiceImpl accessService;

    @Mock
    private AccessRepository accessRepository;

    @Mock
    private ModelMapper modelMapper;

    // Test for the methods getAllAccesses() and getAccess(Long id)
    @Test
    public void shouldReturnAllAccessesOrAccessById() {
        //given
        Access access1 = new Access(1L, "standard", 5);
        Access access2 = new Access(2L, "business", 10);
        List<Access> accesses = List.of(access1, access2);
        Mockito.when(accessRepository.findAll()).thenReturn(accesses);

        Mockito.when(modelMapper.map(accesses.get(0), AccessResponseDto.class))
                .thenReturn(new AccessResponseDto(1L, "standard", 5));
        Mockito.when(modelMapper.map(accesses.get(1), AccessResponseDto.class))
                .thenReturn(new AccessResponseDto(2L, "business", 10));


        AccessResponseDto accessResponseDto1 = modelMapper.map(accesses.get(0), AccessResponseDto.class);
        AccessResponseDto accessResponseDto2 = modelMapper.map(accesses.get(1), AccessResponseDto.class);
        List<AccessResponseDto> expectedAccessResponseDtos = List.of(accessResponseDto1, accessResponseDto2);

        //when
        List<AccessResponseDto> resultAccessResponseDtos = accessService.getAll();

        //then
        Assertions.assertEquals(2, resultAccessResponseDtos.size());
        Assertions.assertEquals(expectedAccessResponseDtos, resultAccessResponseDtos);
        Assertions.assertEquals(expectedAccessResponseDtos.get(0), (resultAccessResponseDtos.get(0)));
        Assertions.assertEquals(expectedAccessResponseDtos.get(1), (resultAccessResponseDtos.get(1)));
    }


    // Test for the method saveAccess(AccessRequestDto accessDto)
    @Test
    public void shouldSavedAccess() {
        //given
        Access access = new Access(1L, "standard", 5);

        AccessRequestDto accessRequestDto = new AccessRequestDto("standard", 5);
        Mockito.when(modelMapper.map(accessRequestDto, Access.class)).thenReturn(access);

        Mockito.when(modelMapper.map(access, AccessResponseDto.class))
                .thenReturn(new AccessResponseDto(1L, "standard", 5));
        AccessResponseDto expectedAccessDto = modelMapper.map(access, AccessResponseDto.class);

        Mockito.when(accessRepository.save(access)).thenReturn(access);

        //when
        AccessResponseDto resultSavedAccessDto = accessService.save(accessRequestDto);

        //then
        Assertions.assertEquals(expectedAccessDto.getId(), resultSavedAccessDto.getId());
        Assertions.assertEquals(expectedAccessDto.getPlanName(), resultSavedAccessDto.getPlanName());
        Assertions.assertEquals(expectedAccessDto.getAvailableLessonsCount(), resultSavedAccessDto.getAvailableLessonsCount());
    }

    // Test for the method deleteAccess(Long id)
    @Test
    public void shouldDeleteAccessById() {
        //given
        Long accessId = 1L;
        Access access = new Access(accessId, "standard", 5);
        Optional<Access> optionalAccess = Optional.of(access);

        Mockito.when(accessRepository.findById(accessId)).thenReturn(optionalAccess);

        //when
        accessService.delete(accessId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(accessRepository).findById(accessId);
        Mockito.verify(accessRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(accessId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenAccessNotFound() {
        //given
        Long nonExistentAccessId = 10L;
        Mockito.when(accessRepository.findById(nonExistentAccessId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> accessService.delete(nonExistentAccessId));

        //then
        Assertions.assertEquals("There is no access with id = "
                + nonExistentAccessId, exception.getMessage());
    }


}
