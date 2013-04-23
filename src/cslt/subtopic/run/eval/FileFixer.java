package cslt.subtopic.run.eval;

import java.io.IOException;

import cslt.util.fileOperation.Format;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class FileFixer {
	public FileFixer(String oldfile, String newfile, String des, String dir) throws IOException {
		String runname = newfile.substring(0, newfile.indexOf("."));
		WriteFile wf = new WriteFile(dir + newfile, false);
		wf.write("<SYSDESC>" + des + "</SYSDESC>\n");
		ReadFile rf = new ReadFile(oldfile);
		
		String line = "";
		while ((line = rf.readLine()) != null) {
			if (line.charAt(0) > '9' || line.charAt(0) < '0')
				line = line.substring(1);
			String[] element = line.split(";");
			String topicNo = element[0];
			int no = Integer.parseInt(topicNo);
			element[0] = Format.get000X(no);
			element[element.length-1] = runname;
			String newline = "";
			for (int i = 0; i < element.length-1; i++)
				newline += element[i] + ";";
			newline += element[element.length -1] + "\n";
			wf.write(newline);
		}
		
		rf.close();
		wf.close();
	}
	
	public static void main(String[] args) throws IOException {
		new FileFixer("./run/test.txt", "run4.txt", "This is a test system.", "THUCIB-INTENT2/");
	}
}
