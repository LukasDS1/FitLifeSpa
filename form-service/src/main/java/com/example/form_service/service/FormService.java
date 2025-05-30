package com.example.form_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.form_service.model.Form;
import com.example.form_service.repository.FormRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FormService {

    private final FormRepository formRepository;

    /**
     * 
     * @param form objecto de formulario
     * @return si guarda el formulario {@code true}, de otra forma {@code false}.
     */
    public Boolean saveForm(Form form) {
        if(form != null) {
            formRepository.save(form);
            return true;
        }
        return false;
    }

    public List<Form> getForm() {
        try {
            return formRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al conseguir los formularios.");
        }
    }

    /**
     * 
     * @param idForm como id del formulario
     * @return si encuentra un formulario devuelve un objeto {@code Form}
     * @throws RuntimeException si no existe el formulario.
     */
    public Form getFormId(Long idForm) {
        if (formRepository.existsById(idForm)) {
            return formRepository.findById(idForm).get();
        }
        throw new RuntimeException("No existe el formulario de id: " + idForm);
    }
}
