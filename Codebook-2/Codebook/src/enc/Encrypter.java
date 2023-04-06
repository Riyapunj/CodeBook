package enc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

import codex.CodexNode;
import codex.FileUtilities;
public class Encrypter { 
    static int maxPatterns = 256;
    static String[] codebook = new String[maxPatterns];
	public static void populateCodebook(CodexNode root, String pattern) 
	{  
		if (root.left == null && root.right == null && root.label.length() == 1) { 
            
            codebook[(int)root.label.toCharArray()[0]] = pattern;
			System.out.println(root.label + ":" + pattern); 

			return; 
		} 

		// if we go to left then add "0" to the code. 
		// if we go to the right add"1" to the code. 

		// recursive calls for left and 
		// right sub-tree of the generated tree. 
		populateCodebook(root.left, pattern + "0"); 
		populateCodebook(root.right, pattern + "1"); 
	} 

    private static int[] generateFreqTable(String inputText) {
        int[] charfreq = new int[maxPatterns]; 
        
        char[] charArray = inputText.toCharArray();
        for(int j = 0 ; j < charArray.length; j++) {
             ++charfreq[charArray[j]];
        }
    //     for(int j = 0 ; j < charfreq.length; j++) {
    //         if(charfreq[j] != 0) {
    //             System.out.print((char)(j)+" : " + charfreq[j] + "");
    //             System.out.print(" || ");
    //         }
    //    }
    //     System.out.println();
        return charfreq;
    }

    private static PriorityQueue<CodexNode> generateSingleNodesAndAddToQueue(int[] charFreq) {
        // creating a priority queue q.  
		PriorityQueue<CodexNode> priorityQueue = new PriorityQueue<CodexNode>(maxPatterns); 

        for (int i = 0; i < maxPatterns; i++) { 
           // no charater for char (i)
           if(charFreq[i] == 0) {
              continue;
           }
           // creating a codex node object 
           // and add it to the priority queue. 
           CodexNode hn = new CodexNode(); 

           hn.label = (char)i + ""; 
           hn.freq = charFreq[i]; 

           hn.left = null; 
           hn.right = null; 

           // add functions adds 
           // the codex node to the queue. 
           priorityQueue.add(hn); 
        }

        return priorityQueue;
    }

	// main function 
	public static void main(String[] args) throws IOException 
	{ 
        String inputFile = "src//files//testing.txt";
        String outputFile = "src//files//encrypted.txt";
        if(args != null && args.length == 2) {
            inputFile = "src//files//" + args[0];
            outputFile = "src//files//" + args[1];
        } else if (args != null && args.length == 1) {
            inputFile = "src//files//" + args[0];
        }
        
		Scanner input= FileUtilities.getScannerForFile(inputFile);

        String inputText = input.nextLine();

        int[] charFreq = generateFreqTable(inputText);

		PriorityQueue<CodexNode> priorityQueue= generateSingleNodesAndAddToQueue(charFreq);

		CodexNode root = createCodexTree(priorityQueue);
 
        populateCodebook(root, ""); 

        PrintWriter pw = FileUtilities.openFileForSave(outputFile);
        
        for( int k =0 ; k < charFreq.length ; k++ ) {
            if(charFreq[k] != 0 && codebook[k] != null) {
				if( (char)k == ' ') {
					pw.print('_');
				} else {
					pw.print((char)(k));
				}
                pw.print("\t");
                pw.print(codebook[k]);
                pw.println();

            }
		}
		
		pw.println("---");

		for( int a = 0 ; a < inputText.length(); a++) {
			String pattern = codebook[inputText.charAt(a)];
			pw.print(pattern);
			pw.print("\t");
		}

        pw.flush();
    } 

    private static CodexNode createCodexTree(PriorityQueue<CodexNode> q) {
        // create a root node 
		CodexNode root = null; 

		while (q.size() > 1) { 
 
            //extracts two min elements from p queue
			CodexNode x = q.peek(); 
			q.poll(); 
			CodexNode y = q.peek(); 
			q.poll(); 
 
			CodexNode f = new CodexNode(); 
			f.freq = x.freq + y.freq; 
			f.label = x.label + y.label; 
 
			f.left = x; 
 
			f.right = y; 
 
			root = f; 

			// add this node to the priority-queue. 
			q.add(f); 
        }
        
        return root;
    }
} 
