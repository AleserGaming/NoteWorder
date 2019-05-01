package libs;

import lang.Dialogs;

import java.io.File;

public class CheckingSystemDir {

    public static void check() {

        File file = new File(Dialogs.SYSTEM_PATH);
        if (!file.isDirectory()) {
            if (!file.mkdirs()) {
                System.out.println("AN ERROR DURING CREATING SYSTEM DIR");
                System.exit(1);
            }
        }
    }
}
