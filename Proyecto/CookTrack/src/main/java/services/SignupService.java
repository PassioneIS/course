package services;

import dao.interfaces.UserDao;
import dao.impl.UserDaoImpl;
import infrastructure.SessionManager;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

public class SignupService {
    static UserDao userDao = new UserDaoImpl();

    public boolean signUp(String username, String password, String confirmPassword){
        if(userExists(username) || !validPassword(password) || !passwordMatch(password, confirmPassword)){
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(username, hashedPassword);
        userDao.createUser(newUser);
        return true;
    }

    public boolean userExists(String username){
        User user = userDao.findByName(username);
        return user != null;
    }

    public boolean validPassword(String password){
        //La contraseña debe tener mínimo 8 carácteres, 1 mayúscula, 1 minúscula y 1 número.
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$");
    }

    public boolean passwordMatch(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    public void ensureAdminUser() {
        if (!userExists("admin")) {
            String hash = BCrypt.hashpw("admin", BCrypt.gensalt());
            User admin = new User("admin", hash);
            userDao.createUser(admin);
        }
    }

    public boolean validUsername(String username){
        return username.matches("^[a-zA-Z0-9]{1,}$");
    }

}