package com.postify.main.dto.validationException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String  field;
    private  String defaultMessage;
}
