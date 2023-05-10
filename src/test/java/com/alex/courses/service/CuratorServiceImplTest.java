package com.alex.courses.service;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;
import com.alex.courses.entity.Curator;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CuratorRepository;
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
public class CuratorServiceImplTest {

    @Mock
    private CuratorRepository curatorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks //i.e. upload this mocks (curatorRepository and modelMapper)
    // to the service (curatorService)
    private CuratorServiceImpl curatorService;

    // Test for the methods getAllCurators() and getCurator(Long id)
    @Test
    public void shouldReturnAllCuratorsOrCuratorById() {
        //given
        Curator curator1 = new Curator(1L, "Anna"
                , "Smirnova", "anna@ya.ru");
        Curator curator2 = new Curator(2L, "Ivan"
                , "Ivanov", "ivan@gmail.com");
        List<Curator> curators = List.of(curator1, curator2);
        Mockito.when(curatorRepository.findAll()).thenReturn(curators);

        Mockito.when(modelMapper.map(curators.get(0), CuratorResponseDto.class))
                .thenReturn(new CuratorResponseDto(1L, "Anna", "Smirnova"));
        Mockito.when(modelMapper.map(curators.get(1), CuratorResponseDto.class))
                .thenReturn(new CuratorResponseDto(2L, "Ivan", "Ivanov"));

        CuratorResponseDto curatorResponseDto1 = modelMapper.map(curators.get(0), CuratorResponseDto.class);
        CuratorResponseDto curatorResponseDto2 = modelMapper.map(curators.get(1), CuratorResponseDto.class);
        List<CuratorResponseDto> expectedCuratorResponseDtos = List.of(curatorResponseDto1, curatorResponseDto2);

        //when
        List<CuratorResponseDto> resultCuratorResponseDtos = curatorService.getAllCurators();

        //then
        Assertions.assertEquals(2, resultCuratorResponseDtos.size());
        Assertions.assertEquals(expectedCuratorResponseDtos, resultCuratorResponseDtos);
        Assertions.assertEquals(expectedCuratorResponseDtos.get(0), (resultCuratorResponseDtos.get(0)));
        Assertions.assertEquals(expectedCuratorResponseDtos.get(1), (resultCuratorResponseDtos.get(1)));
    }


    // Test for the method saveCurator(CuratorRequestDto curatorDto)
    @Test
    public void shouldSavedCurator() {
        //given
        Curator curator = new Curator(1L, "Petr"
                , "Petrov", "petr@ya.ru");

        Mockito.when(modelMapper.map(curator, CuratorRequestDto.class))
                .thenReturn(new CuratorRequestDto("Petr", "Petrov", "petr@ya.ru"));

        CuratorRequestDto expectedCuratorDto = modelMapper.map(curator, CuratorRequestDto.class);

        Mockito.when(modelMapper.map(expectedCuratorDto, Curator.class)).thenReturn(curator);
        Mockito.when(curatorRepository.save(curator)).thenReturn(curator);

        //when
        CuratorRequestDto resultSavedCuratorDto = curatorService.saveCurator(expectedCuratorDto);

        //then
        Assertions.assertEquals(expectedCuratorDto.getName(), resultSavedCuratorDto.getName());
        Assertions.assertEquals(expectedCuratorDto.getSurname(), resultSavedCuratorDto.getSurname());
        Assertions.assertEquals(expectedCuratorDto.getEmail(), resultSavedCuratorDto.getEmail());
    }

    // Test for the method deleteCurator(Long id)
    @Test
    public void shouldDeleteCuratorById() {
        //given
        Long curatorId = 1L;
        Curator curator = new Curator(curatorId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Curator> optionalCurator = Optional.of(curator);

        Mockito.when(curatorRepository.findById(curatorId)).thenReturn(optionalCurator);

        //when
        curatorService.deleteCurator(curatorId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(curatorRepository).findById(curatorId);
        Mockito.verify(curatorRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(curatorId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCuratorNotFound() {
        //given
        Long nonExistentCuratorId = 10L;
        Mockito.when(curatorRepository.findById(nonExistentCuratorId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> curatorService.deleteCurator(nonExistentCuratorId));

        //then
        Assertions.assertEquals("There is no curator with id = "
                + nonExistentCuratorId, exception.getMessage());
    }

    // Test for the method updateCurator(Long id, CuratorUpdateDto updatedCuratorDto)
    @Test
    public void shouldUpdateCurator() {
        //given
        Long curatorId = 1L;
        Curator existingCurator = new Curator(curatorId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Curator> optionalCurator = Optional.of(existingCurator);

        Mockito.when(curatorRepository.findById(curatorId)).thenReturn(optionalCurator);

        String updatedEmail = "updated_anna@ya.ru";
        CuratorUpdateDto updatedCuratorDto = new CuratorUpdateDto();
        updatedCuratorDto.setEmail(updatedEmail);

        Curator updatedCurator = new Curator(curatorId, "Anna"
                , "Smirnova", updatedEmail);
        Mockito.when(curatorRepository.save(existingCurator)).thenReturn(updatedCurator);
        Mockito.when(modelMapper.map(updatedCurator, CuratorUpdateDto.class)).thenReturn(updatedCuratorDto);

        //when
        CuratorUpdateDto resultUpdatedCuratorDto = curatorService.updateCurator(curatorId, updatedCuratorDto);

        //then
        Assertions.assertEquals(updatedEmail, resultUpdatedCuratorDto.getEmail());
        Mockito.verify(curatorRepository).findById(curatorId);
        Mockito.verify(curatorRepository).save(existingCurator);
        Mockito.verify(modelMapper).map(updatedCurator, CuratorUpdateDto.class);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentCurator() {
        //given
        Long nonExistentCuratorId = 10L;
        Mockito.when(curatorRepository.findById(nonExistentCuratorId)).thenReturn(Optional.empty());

        CuratorUpdateDto updatedCuratorDto = new CuratorUpdateDto();
        updatedCuratorDto.setEmail("updated_email@example.com");

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> curatorService.updateCurator(nonExistentCuratorId, updatedCuratorDto));

        //then
        Assertions.assertEquals("There is no curator with id = "
                + nonExistentCuratorId, exception.getMessage());
    }

}
