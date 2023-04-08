package com.pacoprojects.exception;

import lombok.Builder;

@Builder
public record ExpcetionObject(String message, Integer code) {

}
