import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;


public class ReduplateSubtopicRemover {
	public static void removeReduplateSubtopic(String dirname) {
		File dir = new File(dirname);
		String newdirname = dirname + "Remove";
		File newdir = new File(newdirname);
		if (!newdir.exists())
			newdir.mkdir();
		String[] filenames = dir.list();
		for (int i = 0; i < filenames.length; i++) {
			removeSubtopic(filenames[i], dirname);
		}
	}
	
	private static void removeSubtopic(String filename, String dirname) {
		try {
			ReadFile rf = new ReadFile(dirname + "/" + filename);
			WriteFile wf = new WriteFile("rename/" + filename.substring(0, filename.indexOf('.')), false);
			TreeSet<String> subtopics = new TreeSet<String>();
			String subtopic = "";
			while((subtopic = rf.readLine()) != null) {
				wf.writeLine(subtopic, "\n");
			}
			
			wf.close();
			rf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		removeReduplateSubtopic("NTCIR9JoinCandidate");
	}
}
