package no.hvl.dat250.gruppe9.feedapp.restapi.services;
import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.stereotype.Service;

public class HashingService {
/*
 *  SCrypt using:
 *   N = 16384, r = 8 and p = 1;
 */

    private static final int scN = 16384;
    private static final int scR = 8;
    private static final int scP = 1;

    private HashingService() {
        throw new IllegalStateException("Crypto Utility class");
    }

    public static String createPasswordHash(String password) {
        return SCryptUtil.scrypt(password, scN, scR, scP);
    }

    public static boolean checkPassword(String password, String hash) {
        return SCryptUtil.check(password, hash);
    }
}
