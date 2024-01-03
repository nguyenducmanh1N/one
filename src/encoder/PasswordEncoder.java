package encoder;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    // Hàm mã hóa mật khẩu
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plainTextPassword, salt);
        
    }
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

}
