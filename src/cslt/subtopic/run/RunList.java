package cslt.subtopic.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cslt.util.fileOperation.CreateDir;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class RunList {
	private List<String> runnames;
	private static String teamID = "THCIB";
	private static String dir = teamID + "-INTENT2/";
	/**
	 * the description of each run
	 */
	private List<String> des;
	
	public RunList() throws IOException {
		runnames = new ArrayList<String>();
		des = new ArrayList<String>();
		
		for (int i = 1; i <= 5; i++)
			runnames.add(teamID + "-S-E-" + i + "A.txt");
		
		CreateDir.createDir(dir);
		WriteFile wf = new WriteFile(dir + teamID + "-INTENT2.txt", false, "utf-8");
		for (int i = 0; i < runnames.size()-1; i++)
			wf.write(runnames.get(i) + "\n");
		wf.write(runnames.get(runnames.size()-1));
		wf.close();
	}
	
	public void getDes(String filename) throws IOException {
		ReadFile rf = new ReadFile(filename);
		
		for (int i = 0; i < runnames.size(); i++) {
			String line = rf.readLine();
			des.add(line);
		}
		
		rf.close();
	}
	
	public static void main(String[] args) throws IOException {
		RunList test = new RunList();
		test.getDes("./run/SYSDESC-list-en.txt");
		List<String> oldfiles = new ArrayList<String>();
		oldfiles.add("./run/NTCIR10EnRun1_0729-100.txt");
		oldfiles.add("./run/NTCIR10EnRun2_0729-100.txt");
		oldfiles.add("./run/NTCIR10EnRun3_0729_freebase.txt");
		oldfiles.add("./run/thurun3-meanp_freebase.txt");
		oldfiles.add("./run/thurun3-normp_freebase.txt");
		
		for (int i = 0; i < test.runnames.size(); i++) {
			new FileFixer(oldfiles.get(i), test.runnames.get(i), test.des.get(i), dir);
		}
	}
}
