package com.example.dMaker.service;

import com.example.dMaker.code.StatusCode;
import com.example.dMaker.dto.CreateDeveloper;
import com.example.dMaker.dto.DeveloperDetailDto;
import com.example.dMaker.dto.DeveloperDto;
import com.example.dMaker.dto.EditDeveloper;
import com.example.dMaker.entity.Developer;
import com.example.dMaker.entity.RetiredDeveloper;
import com.example.dMaker.exception.DMakerException;
import com.example.dMaker.repository.DeveloperRepository;
import com.example.dMaker.repository.RetiredDeveloperRepository;
import com.example.dMaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dMaker.exception.DMakerErrorCode.*;

//-비지니스 로직 담당
@Service
@RequiredArgsConstructor    //-손쉽게 injection을 시킬 수 있게 만들어줌
public class DMakerService {

    //    @Autowired  //-구방식 서비스를 단독으로 테스트 하고싶어도 어려워지는 어려움이 있었다.
    // 그이후로 Service의 생성자에 Repository를 매개변수로 받아 주입하는 방식이 나왔다
    // 이경우 repository가 여러개일경우 생성자를 고쳐야하는 상황이 있을때 매우 번거럽다고 함
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;  //현 inject 방식

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getAge())
                .memberId(request.getMemberId())
                .name(request.getName())
                .statusCode(StatusCode.EMPLOYED)
                .age(request.getAge())
                .build();   //-Entity 생성

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    //- business validation을 진행할 메소드
    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

//        Optional<Developer> developer =
//                developerRepository.findByMemberId(request.getMemberId());
//        if (developer.isPresent()) throw new DMakerException(DUPLICATED_MEMBER_ID);
        //-자바 8부터는 이렇게 한문장으로 위의 코드와 같은 기능을 하는 코드를 작성할 수 있다.
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId) //- Optional 타입임
                .map(DeveloperDetailDto::fromEntity)        //- 아직도 타입은 Optional임
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));  //-값이 없을때는 괄호안의 특정 동작을 수행

    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DMakerException(NO_DEVELOPER)
        );

        //-entity에 값수정하기 -> 이러면 Entity에만 수정되고 실제로는 수정이 안됨
        //-> @Transactional 키워드를 통해 editDeveloper메소드에 들어가기전 transaction을 시작했다가
        //-entity에 값을 바꿔준다음 변경 사항들을 commit이 되기로 한다.
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);

    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);  //-CustomException 적용하기
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);  //-CustomException 적용하기

        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);  //-CustomException 적용하기
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DMakerException(NO_DEVELOPER));
        // 1. EMPLOYED -> RETIRED
        developer.setStatusCode(StatusCode.RETIRED);    //-상태변화가 일어났기때문에 하나의 작업을 transaction에 예약을 해둔상태이다


        if (developer != null) throw new DMakerException(NO_DEVELOPER);

        //make RetiredDeveloper Entity
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .build();
        // 2. save into RetiredDeveloper
        retiredDeveloperRepository.save(retiredDeveloper);

        return DeveloperDetailDto.fromEntity(developer);
    }
}
