package com.example.dMaker.dto;

import com.example.dMaker.entity.Developer;
import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;    //-경력
    private String memberId;            //-회사의 특정한 멤버아이디
    private String name;                //-이름
    private Integer age;                //-나이

    public static DeveloperDetailDto fromEntity(Developer developer) {
        return DeveloperDetailDto
                .builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYears())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .build();
    }

}