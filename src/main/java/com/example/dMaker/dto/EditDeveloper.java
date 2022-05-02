package com.example.dMaker.dto;

import com.example.dMaker.type.DeveloperLevel;
import com.example.dMaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EditDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0) //-최소 0이상
        @Max(20)    //-최대 20
        private Integer experienceYears;

    }

}
