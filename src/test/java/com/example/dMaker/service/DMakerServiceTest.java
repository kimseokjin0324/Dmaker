package com.example.dMaker.service;

import com.example.dMaker.code.StatusCode;
import com.example.dMaker.dto.DeveloperDetailDto;
import com.example.dMaker.entity.Developer;
import com.example.dMaker.repository.DeveloperRepository;
import com.example.dMaker.repository.RetiredDeveloperRepository;
import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.dMaker.code.StatusCode.*;
import static com.example.dMaker.type.DeveloperLevel.*;
import static com.example.dMaker.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class DMakerServiceTest {


    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;
    @InjectMocks
    private DMakerService dMakerService;

    @Test
    public void testSomething() {
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(
                        Developer.builder()
                                .developerLevel(SENIOR)
                                .developerSkillType(FRONT_END)
                                .experienceYears(12)
                                .statusCode(EMPLOYED)
                                .name("name")
                                .age(12)
                                .build()
                ));
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR,developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END,developerDetail.getDeveloperSkillType());
        assertEquals(12,developerDetail.getExperienceYears());
    }
}