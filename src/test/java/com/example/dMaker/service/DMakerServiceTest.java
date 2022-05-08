package com.example.dMaker.service;

import com.example.dMaker.dto.CreateDeveloper;
import com.example.dMaker.dto.DeveloperDetailDto;
import com.example.dMaker.entity.Developer;
import com.example.dMaker.exception.DMakerErrorCode;
import com.example.dMaker.exception.DMakerException;
import com.example.dMaker.repository.DeveloperRepository;
import com.example.dMaker.repository.RetiredDeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.dMaker.code.StatusCode.EMPLOYED;
import static com.example.dMaker.type.DeveloperLevel.SENIOR;
import static com.example.dMaker.type.DeveloperSkillType.FRONT_END;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class DMakerServiceTest {


    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;
    @InjectMocks
    private DMakerService dMakerService;
    private final Developer defaultDeveloper = Developer.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .statusCode(EMPLOYED)
            .name("name")
            .age(32)
            .build();

    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONT_END)
            .experienceYears(12)
            .name("name")
            .memberId("memberId")
            .age(32)
            .build();

    @Test
    public void testSomething() {

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(
                        defaultDeveloper
                ));
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(FRONT_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }

    @Test
    void crateDeveloperTest_success() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);   //-캡터생성
        //when
        CreateDeveloper.Response developer = dMakerService.createDeveloper(defaultCreateRequest);
        //then
        verify(developerRepository, times(1))  //-검증
                .save(captor.capture());

        Developer savedDeveloper = captor.getValue();
        assertEquals(SENIOR, savedDeveloper.getDeveloperLevel());
        assertEquals(FRONT_END, savedDeveloper.getDeveloperSkillType());
        assertEquals(12, savedDeveloper.getExperienceYears());
    }

    @Test
    void crateDeveloperTest_failed_with_duplicated() {
        //given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));
        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(defaultCreateRequest));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID,dMakerException.getDMakerErrorCode());
    }
}