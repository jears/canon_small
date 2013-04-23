package cslt.subtopic.eva;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class ResultChecker {
	private Map<String, String> subtopicintent;
	
	public ResultChecker(String filename, String answer) throws IOException {
		subtopicintent = new HashMap<String, String>();
//		Set<String> intents = GoldGenerator.getIprob();
		ReadFile rf = new ReadFile(answer);
		
		String line = "";
		while ((line = rf.readLine()) != null) {
			if (line.charAt(0) > '9' || line.charAt(0) < '0')
				line = line.substring(1);
			line = line.toLowerCase();
//			System.out.println(line);
			String[] element = line.split(";");
			String subtopic = element[2];
//			String topic = element[0];
			String intent = element[1];
			subtopicintent.put(subtopic, intent);
			
//			int topicno = Integer.valueOf(element[0]);
//			int intentno = Integer.valueOf(element[2]);
//			
//			if (intents.contains(topicno + "-" + intentno)) {
//				if (topicno == 1)
//				System.out.println("***" + subtopic);
//				subtopicintent.put(subtopic, intent);
//			}
		}
		
		rf.close();
		checkResult(filename);
	}
	
	private Map<Integer, String> getTopics() {
		Map<Integer, String> ret = new HashMap<Integer, String>();
		
		try {
			ReadFile rf = new ReadFile("./data/NTCIR10/NTCIR10Topics.txt");
			String line = "";
			int i = 1;
			while((line = rf.readLine()) != null) {
				ret.put(i, line);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(ret);
		return ret;
	}
	
	public void checkResult(String filename) throws IOException {
		Map<Integer, String> topics = getTopics();
		ReadFile rf = new ReadFile(filename);
		WriteFile wf = new WriteFile(filename + ".check", false);
		WriteFile wf1 = new WriteFile(filename + ".an", false);
		
		String line = "";
		while((line = rf.readLine()) != null) {
			if (line.charAt(0) > '9' || line.charAt(0) < '0')
				line = line.substring(1);
//			System.out.println(line);
			line = line.toLowerCase();
			String[] element = line.split(";");

			String newline = line;
//			if(line.startsWith("0001"))
//			System.out.println(element[3]);
			if (element.length > 6) {
				System.out.println(filename + "\t" + line);
				continue;
			}
			
			if (subtopicintent.containsKey(element[2]))
				newline += "\t" + subtopicintent.get(element[2]);
			else {
				newline += "\tNull";
				if (Integer.parseInt(element[element.length-3]) <= 30) {
					System.out.println(line);
					wf1.writeLine(element[0] + "\t" + topics.get(Integer.parseInt(element[0])) + "\t" + element[2]);
				}
			}
			wf.writeLine(newline, "\n");
			
//			System.out.println(newline);
		}
		
		wf1.close();
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
		String path = "data/manual";
		String gold = "./data/manual/manualcannon.Dqrel";
		ArrayList<String> runs = new ArrayList<String>();
		ReadFile rf = new ReadFile(path + "/runlist");
		String line = "";
		while((line = rf.readLine()) != null) {
			runs.add(line);
		}
		rf.close();
		for (int i = 0; i < runs.size(); i++)
			new ResultChecker(path + "/" + runs.get(i),gold); 
	}
	
}
