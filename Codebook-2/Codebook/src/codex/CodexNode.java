package codex;

// node class is the basic structure 
public class CodexNode implements Comparable<CodexNode>{ 

	public int freq; 
	public String label; 

	public CodexNode left; 
    public CodexNode right; 
 
     @Override
     public int compareTo(CodexNode o) {
        return this.freq - o.freq;
     }
    }
