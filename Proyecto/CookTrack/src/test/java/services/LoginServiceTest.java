package services;

import dao.interfaces.UserDao;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import infrastructure.SessionManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    private UserDao userDaoMock;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        userDaoMock = mock(UserDao.class);
        LoginService.userDao = userDaoMock; // inyectamos el mock en el static field
        loginService = new LoginService();
    }

    @Test
    void testSuccessfulLogin() {
        User user = new User();
        user.setName("Cristiano Ronaldo");
        user.setPassword("$TryAgain");

        // mock findByName para que retorne un usuario
        when(userDaoMock.findByName("Cristiano Ronaldo")).thenReturn(user);

        // mock BCrypt.checkpw static method
        try (MockedStatic<org.mindrot.jbcrypt.BCrypt> bcryptMock = Mockito.mockStatic(org.mindrot.jbcrypt.BCrypt.class)) {
            bcryptMock.when(() -> org.mindrot.jbcrypt.BCrypt.checkpw("1234", user.getPassword())).thenReturn(true);

            // mock SessionManager static getInstance
            try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
                SessionManager sessionManagerMock = mock(SessionManager.class);
                sm.when(SessionManager::getInstance).thenReturn(sessionManagerMock);

                // call method
                boolean result = loginService.logIn("Cristiano Ronaldo", "1234");

                // assertions
                assertTrue(result);
                verify(sessionManagerMock).login(user); // verifica que se haya llamado login con el user
            }
        }
    }

    @Test
    void testWrongPassword() {
        User user = new User();
        user.setName("juan");
        user.setPassword("$2a$10$examplehash");

        when(userDaoMock.findByName("juan")).thenReturn(user);

        try (MockedStatic<org.mindrot.jbcrypt.BCrypt> bcryptMock = Mockito.mockStatic(org.mindrot.jbcrypt.BCrypt.class)) {
            bcryptMock.when(() -> org.mindrot.jbcrypt.BCrypt.checkpw("wrong", user.getPassword())).thenReturn(false);

            boolean result = loginService.logIn("juan", "wrong");

            assertFalse(result);
        }
    }

    @Test
    void testUserNotFound() {
        when(userDaoMock.findByName("noexiste")).thenReturn(null);

        boolean result = loginService.logIn("noexiste", "any");

        assertFalse(result);
    }
}
