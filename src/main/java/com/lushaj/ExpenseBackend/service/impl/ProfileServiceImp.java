package com.lushaj.ExpenseBackend.service.impl;

import com.lushaj.ExpenseBackend.dto.ProfileDTO;
import com.lushaj.ExpenseBackend.entity.ProfileEntity;
import com.lushaj.ExpenseBackend.repository.ProfileRepository;
import com.lushaj.ExpenseBackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = mapToProfileEntity(profileDTO);
        profileEntity.setProfileId(UUID.randomUUID().toString());

        profileEntity = profileRepository.save(profileEntity);
        return mapToProfileDTO(profileEntity);
    }

    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setProfileId(profileEntity.getProfileId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setCreatedAt(profileEntity.getCreatedAt());
        profileDTO.setUpdatedAt(profileEntity.getUpdatedAt());
        return profileDTO;
    }

    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileEntity.class);
    }
}
