package com.example.restapitokensecurity.api.exceptions;

import org.springframework.validation.BindingResult;

import java.util.List;

public class ValidationFailureException extends  RuntimeException{
    private static final long serialVersionUID= 1L;

    private List<String> messages;

//    public ValidationFailureException(BindingResult result){
//        messages = result.getFieldErrors().stream()
//                .map(a->a.getDefaultMessage()).toList();
//    }



    public List<String> getMessages(){
        return messages;
    }
}
