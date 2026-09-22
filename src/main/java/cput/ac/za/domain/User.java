package cput.ac.za.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String phone;

    public User() {
    }

    public User(Builder builder) {
        this.userID = builder.userID;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.passwordHash;
        this.password = builder.password;
        this.role = builder.role;
        this.phone = builder.phone;
    }

    public Long getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", phone='" + phone +
                '}';
    }

    public static class Builder {
        private Long userID;
        private String name;
        private String email;
        private String password;
        private Role role;
        private String phone;

        public Builder setUserID(Long userID) {
            this.userID = userID;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPasswordHash(String passwordHash) {
            this.password = passwordHash;
            return this;
        }
        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder copy(User user) {
            this.userID = user.userID;
            this.name = user.name;
            this.email = user.email;
            this.passwordHash = user.password;
            this.password = user.password;
            this.role = user.role;
            this.phone = user.phone;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}