package com.example.form_service.service;

import org.springframework.stereotype.Service;

import com.example.form_service.model.Form;
import com.example.form_service.repository.FormRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;

    public Boolean saveForm(Form form) {
        if(form != null) {
            formRepository.save(form);
            return true;
        }
        return false;
    }
}
