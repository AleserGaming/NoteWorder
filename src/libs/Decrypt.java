package libs;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Decrypt {

    public static String decrypt(String str, Cipher dcipher) throws Exception {

        byte[] dec = BASE64DecoderStream.decode(str.getBytes());
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, StandardCharsets.UTF_8);
    }
}