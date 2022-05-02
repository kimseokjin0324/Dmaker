package com.example.dMaker.service;

import com.example.dMaker.dto.CreateDeveloper;
import com.example.dMaker.dto.DeveloperDto;
import com.example.dMaker.entity.Developer;
import com.example.dMaker.exception.DMakerException;
import com.example.dMaker.repository.DeveloperRepository;
import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.dMaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.example.dMaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED;

//-비지니스 로직 담당
@Service
@RequiredArgsConstructor    //-손쉽게 injection을 시킬 수 있게 만들어줌
public class DMakerService {

    //    @Autowired  //-구방식 서비스를 단독으로 테스트 하고싶어도 어려워지는 어려움이 있었다.
    // 그이후로 Service의 생성자에 Repository를 매개변수로 받아 주입하는 방식이 나왔다
    // 이경우 repository가 여러개일경우 생성자를 고쳐야하는 상황이 있을때 매우 번거럽다고 함
    private final DeveloperRepository developerRepository;  //현 inject 방식

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getAge())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .build();   //-Entity 생성

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    //- business validation을 진행할 메소드
    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
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

//        Optional<Developer> developer =
//                developerRepository.findByMemberId(request.getMemberId());
//        if (developer.isPresent()) throw new DMakerException(DUPLICATED_MEMBER_ID);
        //-자바 8부터는 이렇게 한문장으로 위의 코드와 같은 기능을 하는 코드를 작성할 수 있다.
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }
}
