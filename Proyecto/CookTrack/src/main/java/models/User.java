package models;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short user_id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "password")
    private String password;


    public User() {}

    public  User(String username, String password){
        this.name = username;
        this.password = password;
    }

    public short getId() {
        return user_id;
    }

    public void setId(short id) {
        this.user_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
