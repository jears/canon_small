package cslt.subtopic.eva;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cslt.util.fileOperation.Format;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

public class GoldGenerator { 
	private static Set<String> topicintent = new HashSet<String>();
	
	public static Set<String> getIprob() throws IOException {
		ReadFile rf = new ReadFile("./data/dummyintent.txt");
		WriteFile wf = new WriteFile("./data/TREC11.Iprob", false);
		
		String line;
		line = rf.readLine();
		String[] intent = line.split("\t");
		String a = intent[0];
//		System.out.println(intent[0] + " " + a.length());
		int topicno = Integer.valueOf(a.substring(1));
		int intentno = Integer.valueOf(intent[1]);
		topicintent.add(topicno + "-" + intentno);
		double intentpb = Double.valueOf(intent[12]);
		String newline = Format.get000X(topicno) + ";" + intentno + ";" + intentpb;
		System.out.println(newline);
		wf.writeLine(newline, "\n");
		
		while ((line = rf.readLine()) != null) {
//			System.out.println(line);
			intent = line.split("\t");
			a = intent[0];
//			System.out.println(intent[0] + " " + a.length());
			topicno = Integer.valueOf(a);
			intentno = Integer.valueOf(intent[1]);
			topicintent.add(topicno + "-" + intentno);
			intentpb = Double.valueOf(intent[12]);
			
			newline = Format.get000X(topicno) + ";" + intentno + ";" + intentpb;
			System.out.println(newline);
			wf.writeLine(newline, "\n");
		}
		
		wf.close();
		rf.close();
		
		return topicintent;
	}
	
	public static void getDqrel() throws IOException {
		ReadFile rf = new ReadFile("./data/dummysubtopic.txt");
		WriteFile wf = new WriteFile("./data/TREC11.Dqrel", false);
		
		String line;
		line = rf.readLine();
		line = line.toLowerCase();
		String[] intent = line.split("\t");
		String a = intent[0];
//		System.out.println(intent[0] + " " + a.length());
		int topicno = Integer.valueOf(a.substring(1));
		int intentno = Integer.valueOf(intent[2]);
		String newline = Format.get000X(topicno) + ";" + intentno + ";" + intent[1] + ";" + "L1" ;
		
		String key = topicno + "-" + intentno;
		key = topicno + "-" + intentno;
		if (topicintent.contains(key)) {
			System.out.println(newline);
			wf.writeLine(newline, "\n");
		}
		
		while ((line = rf.readLine()) != null) {
			line = line.toLowerCase();
//			System.out.println(line);
			intent = line.split("\t");
			a = intent[0];
//			System.out.println(intent[0] + " " + a.length());
			topicno = Integer.valueOf(a);
			intentno = Integer.valueOf(intent[2]);
			
			newline = Format.get000X(topicno) + ";" + intentno + ";" + intent[1] + ";" + "L1" ;
//			
			
			
			key = topicno + "-" + intentno;
			if (topicintent.contains(key)) {
				System.out.println(newline);
				wf.writeLine(newline, "\n");
			}
		}
		
		wf.close();
		rf.close();
	}
	
	public static void main(String[] args) throws IOException {
		getIprob();
		System.out.println(topicintent);
		getDqrel();
	}
}
