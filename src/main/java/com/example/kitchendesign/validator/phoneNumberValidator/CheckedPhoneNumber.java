package com.example.kitchendesign.validator.phoneNumberValidator;

import com.example.kitchendesign.exception.PhoneNumberParsingException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.Data;
import lombok.Value;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;

//@Value
@Data
public class CheckedPhoneNumber {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

    String value;
    private final static String region = "BY";

//    public CheckedPhoneNumber(String value) {
//        this.value = validateAndNormalizePhoneNumber(value);
//    }


//    public String validateAndNormalizePhoneNumber(String value) {
//
//        try {
//            if (Long.parseLong(value) <= 0) {
//                throw new PhoneNumberParsingException("The phone number cannot be negative: " + value);
//            }
//            final var phoneNumber = PHONE_NUMBER_UTIL.parse(value, region);
//            final String formattedPhoneNumber = PHONE_NUMBER_UTIL.format(phoneNumber, E164);
//            // E164 format returns phone number with + character
//
//        } catch (NumberParseException | NumberFormatException e) {
//            throw new PhoneNumberParsingException("The phone number isn't valid: " + value, e);
//        }
//    }


    public String validateAndNormalizePhoneNumber(String value) {
        final Phonenumber.PhoneNumber phoneNumber;
        final String formattedPhoneNumber;

        try {

            if (Long.parseLong(value) <= 0) {
                throw new PhoneNumberParsingException("The phone number cannot be negative: " + value);
            }
            phoneNumber = PHONE_NUMBER_UTIL.parse(value, region);

            if (!PHONE_NUMBER_UTIL.isValidNumberForRegion(phoneNumber, region)) {
                throw new PhoneNumberParsingException("Enter correct phone number: " + value);
            }
            formattedPhoneNumber = PHONE_NUMBER_UTIL.format(phoneNumber, E164);
            // E164 format returns phone number with + character

            return formattedPhoneNumber.substring(1);

        } catch (NumberParseException | NumberFormatException e) {
            throw new PhoneNumberParsingException("The phone number isn't valid: " + value, e);
        }
    }
}

