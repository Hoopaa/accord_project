import utilities.ReadFiles;

import java.util.ArrayList;

public class Indexing {

    public static void main(String[] args) {
        ReadFiles tFiles = new ReadFiles("../parsing/Lettre parsee", "readThread");
        tFiles.start();
    }
}
