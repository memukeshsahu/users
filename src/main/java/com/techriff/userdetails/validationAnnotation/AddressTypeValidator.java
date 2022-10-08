package com.techriff.userdetails.validationAnnotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.techriff.userdetails.entity.AddressType;
import com.techriff.userdetails.repository.AddressTypeRepository;

public class AddressTypeValidator implements ConstraintValidator<ValidateAdressType,Integer>{

    @Autowired
    private AddressTypeRepository addressTypeRepository;

    @Override
    public boolean isValid(Integer addressTypeId, ConstraintValidatorContext context) {
        Optional<AddressType> addressTypes=addressTypeRepository.findById(addressTypeId);
        
            
        return (addressTypeId!=0 && addressTypes.isPresent());
    }


}
