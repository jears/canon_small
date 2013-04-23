import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;


public class GoogleSnippetExtractor {
	public static void extractTitleDir(String dirname) {
		File dir = new File(dirname);
		String newdirname ="snippet/" + dirname;
		File newdir = new File(newdirname);
		if (!newdir.exists())
			newdir.mkdir();
		String[] filenames = dir.list();
		for (int i = 0; i < filenames.length; i++) {
			extractTitleFile(filenames[i], dirname);
		}
	}
	
	private static void extractTitleFile(String filename, String dirname) {
		try {
			ReadFile rf = new ReadFile(dirname + "/" + filename);
			WriteFile wf = new WriteFile("snippet/" + dirname + "/" + filename, false);
			ArrayList<String> titles = new ArrayList<String>();
			String title = "";
			int count = 0;
			while((title = rf.readLine()) != null) {
				title = rf.readLine();
				titles.add(title.trim());
				
				rf.readLine();
				rf.readLine();
				count++;
				if (count == 100)
					break;
			}
			
			for (int i = 0; i < titles.size(); i++) {
				wf.write(titles.get(i) + "\n");
			}
			
			wf.close();
			rf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		extractTitleDir("TREC09");
		extractTitleDir("TREC10");
		extractTitleDir("TREC11");
		extractTitleDir("NTCIR-9");
	}
}
