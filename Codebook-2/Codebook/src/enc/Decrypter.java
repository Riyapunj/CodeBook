package enc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import codex.FileUtilities; 
public class Decrypter { 

    static int maxPatterns = 256;
	public static void main(String[] args) throws IOException 
	{ 
        String inputFile = "src//files//encrypted.txt";
        String outputFile = "src//files//recovered.txt";
        if(args != null && args.length == 2) {
            inputFile = "src//files//" + args[0];
            outputFile = "src//files//" + args[1];
        } else if (args != null && args.length == 1) {
            inputFile = "src//files//" + args[0];
        }
		Scanner input = FileUtilities.getScannerForFile(inputFile);
 
        String[] codebook = new String[maxPatterns];
        while( input.hasNext()) {
            String label = input.next();
            if(label.equals("---")) {
                break;
            }
            char character = label.toCharArray()[0];
            if( character == '_') {
                codebook[' '] = input.next();
            } else {
                codebook[character] = input.next();
            }
        }
        PrintWriter pw = FileUtilities.openFileForSave(outputFile);
        while( input.hasNext()) {
            String label = input.next();
            pw.print(getCharForCodebook(codebook, label));
        }
        pw.flush();
    } 

    private static char getCharForCodebook(String[] codebook, String code) {
        for(int l = 0; l < codebook.length; l++) {
            if(codebook[l] != null  && codebook[l].equals(code)) {
                return (char)l;
            }
        }
        return (char)-1;
    }
} 
