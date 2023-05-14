package com.alex.courses.service.curator;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;
import com.alex.courses.entity.Curator;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CuratorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuratorServiceImpl implements CuratorService {

    @Autowired
    private final CuratorRepository curatorRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public CuratorServiceImpl(CuratorRepository curatorRepository, ModelMapper modelMapper) {
        this.curatorRepository = curatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CuratorResponseDto> getAllCurators() {
        List<Curator> curators = curatorRepository.findAll();
        return curators.stream().map((curator) -> modelMapper.map(curator, CuratorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CuratorRequestDto saveCurator(CuratorRequestDto curatorDto) {
        Curator curator = modelMapper.map(curatorDto, Curator.class);
        Curator savedCurator = curatorRepository.save(curator);
        CuratorRequestDto savedCuratorDto = modelMapper.map(savedCurator, CuratorRequestDto.class);
        return savedCuratorDto;
    }

    @Override
    public CuratorResponseDto getCurator(Long id) {
        Curator curator = curatorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no curator with id = " + id));
        return modelMapper.map(curator, CuratorResponseDto.class);
    }

    @Override
    public void deleteCurator(Long id) {
        curatorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no curator with id = " + id));
        curatorRepository.deleteById(id);
    }

    @Override
    public CuratorUpdateDto updateCurator(Long id, CuratorUpdateDto updatedCuratorDto) {
        Curator existingCurator = curatorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no curator with id = " + id));
        existingCurator.setEmail(updatedCuratorDto.getEmail());
        Curator savedCurator = curatorRepository.save(existingCurator);
        return modelMapper.map(savedCurator, CuratorUpdateDto.class);
    }
}
