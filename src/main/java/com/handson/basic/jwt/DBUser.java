package com.handson.basic.jwt;


import com.google.common.base.MoreObjects;
import org.springframework.data.domain.Persistable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class DBUser implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = -5554304839188669754L;

    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    public Long getId() {
        return id;
    }
    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    @Column(nullable = false, length = 33)
    private String fullName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 33)
    private String permission;

    @Column(nullable = false, length = 33)
    private String name;

    private String role;

    @Column(nullable = false, length = 33)
    private String password;

    protected DBUser() {
    }

    @Transient
    public static String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
//        return Hashing.sha256().hashString(password, Charset.defaultCharset()).toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("fullName", fullName)
                .add("name", name)
                .add("role", role)
                .toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password; //DBUser.hashPassword(unencryptedPassword);
    }
    public String getPassword() {
        return password;
    }


    public static final class UserBuilder {
        protected Long id;
        private String fullName;
        private String name;
        private String role;

        private String email;

        private String permission;

        private String password;//https://bcrypt-generator.com/ generate password user+email:javainuse,password:$2y$12$JfmXLQVmTZGpeYVgr6AVhejDGynQ739F4pJE1ZjyCPTvKIHTYb2fi

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public UserBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public DBUser build() {
            DBUser user = new DBUser();
            user.setId(id);
            user.setFullName(fullName);
            user.setName(name);
            user.setRole(role);
            user.setPassword(password);
            user.setPermission(permission);
            user.setEmail(email);
            return user;
        }
    }
}
