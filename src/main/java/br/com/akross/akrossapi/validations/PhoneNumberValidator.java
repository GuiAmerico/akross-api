package br.com.akross.akrossapi.validations;

import br.com.akross.akrossapi.exceptions.ResourceNotValidException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {


  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    Phonenumber.PhoneNumber phoneNumber = null;

    try {
      phoneNumber = phoneNumberUtil.parse(value,
        CountryCodeSource.UNSPECIFIED.name());

    } catch (NumberParseException e) {
      if (Objects.isNull(value) || value.isBlank()) {
        throw new ResourceNotValidException("Phone number is required");
      }
      throw new ResourceNotValidException(e.getMessage());
    }

    return phoneNumberUtil.isValidNumber(phoneNumber);
  }
}
