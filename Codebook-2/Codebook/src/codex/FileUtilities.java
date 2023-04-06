package codex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtilities {
    public static Scanner getScannerForFile(String fileName) throws IOException {
        Scanner input=null;
        try {
           input = new Scanner(new File(fileName));
        }
        catch (IOException ioe) {
           System.out.println(ioe);
           throw ioe;
        }
        return input;
    }

    public static PrintWriter openFileForSave(String filename) throws IOException {
        try {
            return new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        }
        catch (IOException ioe) {
            System.out.println(ioe);
            throw ioe;
        }
    }
}