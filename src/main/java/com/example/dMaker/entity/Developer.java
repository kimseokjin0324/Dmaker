package com.example.dMaker.entity;

import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYears;    //-경력
    private String memberId;            //-회사의 특정한 멤버아이디
    private String name;                //-이름
    private Integer age;                //-나이

    @CreatedDate    //-Spring JPA의 기능중 Auditing이라는 기능을 통해 자동으로 생성시점을 저장
    private LocalDateTime createdAt;

    @LastModifiedDate//-Spring JPA의 기능중 Auditing이라는 기능을 통해 자동으로 마지막 수정시점을 저장
    private LocalDateTime updatedAt;
}
