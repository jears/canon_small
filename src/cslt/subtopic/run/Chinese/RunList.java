package cslt.subtopic.run.Chinese;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cslt.util.fileOperation.CreateDir;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class RunList {
	private int runnum = 4;
	private List<String> runnames;
	private static String teamID = "THUIS";
	private static String dir = "./chinese/" + teamID + "-INTENT2/";
	/**
	 * the description of each run
	 */
	private List<String> des;
	
	public RunList() throws IOException {
		runnames = new ArrayList<String>();
		des = new ArrayList<String>();
		
		for (int i = 1; i <= runnum; i++)
			runnames.add(teamID + "-S-C-" + i + "A.txt");
		
		CreateDir.createDir(dir);
		WriteFile wf = new WriteFile(dir + teamID + "-INTENT2.txt", false);
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
		test.getDes("./chinese/run/SYSDESC-list-cn.txt");
		List<String> oldfiles = new ArrayList<String>();
		oldfiles.add("./chinese/run/NTCIR9ChRun1_0731_2.txt");
		oldfiles.add("./chinese/run/thu-meanp-NTCIR9-0730.txt");
		oldfiles.add("./chinese/run/thu-normp-NTCIR9-0730.txt");
		oldfiles.add("./chinese/run/NTCIR9ChRun4_0731_2.txt");
		
		List<String> oldfiles2 = new ArrayList<String>();
		oldfiles2.add("./chinese/run/NTCIR10ChRun1_0731_2.txt");
		oldfiles2.add("./chinese/run/thu-meanp-NTCIR10-0730.txt");
		oldfiles2.add("./chinese/run/thu-normp-NTCIR10-0730.txt");
		oldfiles2.add("./chinese/run/NTCIR10ChRun4_0731_2.txt");
		
		for (int i = 0; i < test.runnames.size(); i++) {
			new FileFixer(oldfiles.get(i), oldfiles2.get(i), test.runnames.get(i), test.des.get(i), dir);
		}
	}
}
