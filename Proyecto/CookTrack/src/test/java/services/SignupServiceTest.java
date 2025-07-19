package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;

public class SignupServiceTest {

    private SignupService service;

    @BeforeEach
    public void setup(){
        service = new SignupService();
    }

    @Test
    public void testValidName(){
        assertThat(service.validUsername("admin123")).isTrue();
        assertThat(service.validUsername("")).isFalse();
        assertThat(service.validUsername(null)).isFalse();
        assertThat(service.validUsername("@")).isFalse();
    }

    @Test
    public void testValidPassword(){
        assertThat(service.validPassword("Password1")).isTrue();
        assertThat(service.validPassword("LEGALlegal123456789@")).isFalse();
        assertThat(service.validPassword("JUSTUPPERCASE")).isFalse();
        assertThat(service.validPassword("JustLetters")).isFalse();
        assertThat(service.validPassword(null)).isFalse();
        assertThat(service.validPassword("1234567890110")).isFalse();
        assertThat(service.validPassword("NotEn8")).isFalse();
    }

}