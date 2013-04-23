package cslt.subtopic.run.Chinese;

import java.io.IOException;

import cslt.util.fileOperation.Format;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class FileFixer {
	public FileFixer(String oldfile, String oldfile2, String newfile, String des, String dir) throws IOException {
		String runname = newfile.substring(0, newfile.indexOf("."));
		WriteFile wf = new WriteFile(dir + newfile, false);
		wf.write(des + "\n");
		ReadFile rf = new ReadFile(oldfile, "gbk");
		
		String line = "";
		while ((line = rf.readLine()) != null) {
			if (line.charAt(0) > '9' || line.charAt(0) < '0')
				line = line.substring(1);
			String[] element = line.split(";");
			String subtopicNo = element[3];
			element[3] = String.valueOf(Integer.parseInt(subtopicNo));	
			element[2] = element[2].trim();
			if (line.indexOf('') != -1)
				System.out.println(element[2]);
			element[element.length-1] = runname;
			String newline = "";
			for (int i = 0; i < element.length-1; i++)
				newline += element[i] + ";";
			newline += element[element.length -1] + "\n";
			wf.write(newline);
		}
		
		rf.close();
		
		rf = new ReadFile(oldfile2, "gbk");
		
		line = "";
		while ((line = rf.readLine()) != null) {
			if (line.charAt(0) > '9' || line.charAt(0) < '0')
				line = line.substring(1);
			String[] element = line.split(";");
			String topicNo = element[0];
			int no = Integer.parseInt(topicNo);
			element[0] = Format.get000X(200+no);
			String subtopicNo = element[3];
			element[3] = String.valueOf(Integer.parseInt(subtopicNo));
			element[2] = element[2].trim();
			if (element[2].indexOf("") != -1)
				System.out.println(element[2]);
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
		new FileFixer("./run/test.txt", "./run/test.txt", "THUCIB-S-E-4A.txt", "This is a test system.", "THUCIB-INTENT2/");
	}
}
