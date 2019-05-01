import libs.CheckingSystemDir;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main extends MainFX {

    static Cipher ecipher;
    static Cipher dcipher;
    private static SecretKeySpec key;

    private static final String DEFAULT_KEY = "STANDART";

    public static void main(String[] args) {

        CheckingSystemDir.check();

        try {
            setKey();
            initCipher();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }

    private static void setKey() {
        byte[] byteKey = Main.DEFAULT_KEY.getBytes();
        key = new SecretKeySpec(byteKey,"DES");
    }

    private static void initCipher() throws Exception{
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }
}