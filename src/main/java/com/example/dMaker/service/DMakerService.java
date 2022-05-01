package com.example.dMaker.service;

import com.example.dMaker.dto.CreateDeveloper;
import com.example.dMaker.entity.Developer;
import com.example.dMaker.repository.DeveloperRepository;
import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

//-비지니스 로직 담당
@Service
@RequiredArgsConstructor    //-손쉽게 injection을 시킬 수 있게 만들어줌
public class DMakerService {

    //    @Autowired  //-구방식 서비스를 단독으로 테스트 하고싶어도 어려워지는 어려움이 있었다.
    // 그이후로 Service의 생성자에 Repository를 매개변수로 받아 주입하는 방식이 나왔다
    // 이경우 repository가 여러개일경우 생성자를 고쳐야하는 상황이 있을때 매우 번거럽다고 함
    private final DeveloperRepository developerRepository;  //현 inject 방식

    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("Olaf")
                .age(5)
                .build();   //-Entity 생성

        developerRepository.save(developer);
    }

}
