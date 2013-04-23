package cslt.subtopic.eva;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class ResultCheckerChinese {
private Map<String, String> subtopicintent;
	
	public ResultCheckerChinese(String answer) throws IOException {
		subtopicintent = new HashMap<String, String>();
//		Set<String> intents = GoldGenerator.getIprob();
		ReadFile rf = new ReadFile(answer, "gbk");
		
		String line = "";
		while ((line = rf.readLine()) != null) { 
			String[] element = line.split(";");
			String subtopic = element[2];
//			String topic = element[0];
			String intent = element[1];
			subtopicintent.put(subtopic, intent);
		}
		
		rf.close();
	}
	
	public void checkResult(String filename) throws IOException {
		ReadFile rf = new ReadFile(filename, "gbk");
		WriteFile wf = new WriteFile(filename + ".check", false, "gbk");
		
		String line = "";
		while((line = rf.readLine()) != null) {
			String[] element = line.split(";");
			String newline = line;
			if (subtopicintent.containsKey(element[2]))
				newline += "\t" + subtopicintent.get(element[2]);
			else
				newline += "\tNull";
			wf.writeLine(newline, "\n");
			System.out.println(newline);
		}
		
		wf.close();
		rf.close();
	}
	
	public static void toLowerCase(String filename) throws IOException {
		ReadFile rf = new ReadFile(filename);
		WriteFile wf = new WriteFile(filename + ".low", false);
		
		String line;
		while((line = rf.readLine()) != null) {
			line = line.toLowerCase();
			wf.writeLine(line, "\n");
		}
		wf.close();
		rf.close();
	}
	
	public static void main(String[] args) throws IOException {
		ResultCheckerChinese test = new ResultCheckerChinese("./data/NTCIR9/ntcir9.Dqrel");
		test.checkResult("data/NTCIR9/baseline1");
		test.checkResult("data/NTCIR9/baseline2");
		test.checkResult("data/NTCIR9/baseline3");
		test.checkResult("data/NTCIR9/baseline4");
//		toLowerCase("baseline1");
//		toLowerCase("baseline2");
//		toLowerCase("baseline3");
//		toLowerCase("baseline4");
	}
}
