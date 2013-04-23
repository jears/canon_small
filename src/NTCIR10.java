import java.io.IOException;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;


public class NTCIR10 {
	
	public static String utf82Ascii(String in) {
//		System.out.println(in.length());
		String out = "";
		
		char[] c = in.toCharArray();
//		System.out.println(c.length);
		
		for (int i = 0; i < c.length; ++i) {
//			System.out.print(i +c[i]);
//			System.out.println((int)c[i] + " " + c[i]);
			if (c[i] > 127 || c[i] <0) {
				System.out.println(c[i]);
				c[i] = ' ';
				if (i > 0 && c[i-1] == ' ') {
					
				} else {
					out += ' ';
				}
			} else
			{
				out += c[i];
			}
		}
		
		return out.trim();
	}
		
	
	
	public static void main(String[] args) throws IOException {
		ReadFile rf = new ReadFile("NTCIR10.Dqrel");
		WriteFile wf = new WriteFile("NTCIR10", false);
		
		String line = "";
		
		while((line = rf.readLine()) != null) {
			wf.writeLine(utf82Ascii(line), "\n");
		}
			
				wf.close();
		rf.close();
	}
}
