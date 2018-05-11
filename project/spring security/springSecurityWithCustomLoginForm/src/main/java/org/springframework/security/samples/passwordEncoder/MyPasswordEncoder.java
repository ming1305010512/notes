package org.springframework.security.samples.passwordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author:longming
 * @Description:
 * @Date:Created in 11:07 2018/5/9
 * @Modified By:
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
