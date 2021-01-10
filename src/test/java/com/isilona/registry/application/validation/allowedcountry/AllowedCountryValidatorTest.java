package com.isilona.registry.application.validation.allowedcountry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllowedCountryValidatorTest {

    private static final List<String> ALLOWED_COUNTRIES = List.of("ES");
    private final AllowedCountryValidator validator = new AllowedCountryValidator(ALLOWED_COUNTRIES);

    @Mock
    ConstraintValidatorContextImpl cxt;

    @Test
    void testNullIsValid() {
        assertTrue(validator.isValid(null, cxt));
    }

    @Test
    void testEmptyIsValid() {
        assertTrue(validator.isValid("", cxt));
        assertTrue(validator.isValid(" ", cxt));
        assertTrue(validator.isValid("       ", cxt));
    }

    @Test
    void testAnyFromListIsValid() throws NoSuchAlgorithmException {
        String randomAllowedCountry = ALLOWED_COUNTRIES.get(SecureRandom.getInstanceStrong().nextInt(ALLOWED_COUNTRIES.size()));
        assertTrue(validator.isValid(randomAllowedCountry, cxt));
    }

    @Test
    void testAnyNotInListIsInvalid() {
        assertFalse(validator.isValid("XX", cxt));
    }


}