package com.ssafy.realestate.user.encoder;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder {
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean isMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}