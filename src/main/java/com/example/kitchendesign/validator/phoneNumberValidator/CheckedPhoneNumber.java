package com.example.kitchendesign.validator.phoneNumberValidator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.Phonenumber;

public class UserPhoneNumber {

    PhoneNumber phoneNumber = PHONE_UTIL.


    private static void validatePhoneNumber(String value){

        try {
            if (Long.parseLong(value) <= 0){
                throw new PhoneNumberParsingException()
            }
        }
    }

}
