package infrastructure;

import models.User;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.currentUser = user;
        System.out.println("Usuario logueado: " + user.getName());
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Usuario salió: " + currentUser.getName());
            this.currentUser = null;
        } else {
            System.out.println("No hay sesión activa para cerrar.");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
