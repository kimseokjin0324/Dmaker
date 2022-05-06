package com.example.dMaker.dto;

import com.example.dMaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {

    private DMakerErrorCode errorCode;
    private String errorMessage;
}
