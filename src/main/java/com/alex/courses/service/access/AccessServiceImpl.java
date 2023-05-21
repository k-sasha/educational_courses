package com.alex.courses.service.access;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    private final AccessRepository accessRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public AccessServiceImpl(AccessRepository accessRepository, ModelMapper modelMapper) {
        this.accessRepository = accessRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AccessResponseDto> getAllAccesses() {
        List<Access> accesses = accessRepository.findAll();
        return accesses.stream().map((access) -> modelMapper.map(access, AccessResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccessResponseDto saveAccess(AccessRequestDto accessDto) {
        Access access = modelMapper.map(accessDto, Access.class);
        Access savedAccess = accessRepository.save(access);
        return modelMapper.map(savedAccess, AccessResponseDto.class);
    }

    @Override
    public AccessResponseDto getAccess(Long id) {
        Access access = accessRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no access with id = " + id));
        return modelMapper.map(access, AccessResponseDto.class);
    }

    @Override
    public void deleteAccess(Long id) {
        accessRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no access with id = " + id));
        accessRepository.deleteById(id);
    }
}
