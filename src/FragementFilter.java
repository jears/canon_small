import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cslt.subtopic.model.Topic;
import cslt.subtopic.preprocess.TopicExtractor;
import cslt.util.fileOperation.Constants;
import cslt.util.fileOperation.CreateDir;
import cslt.util.fileOperation.Format;
import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

/**
 * 过滤掉fragement中不包含query words以外其他信息的句子
 * @author jears
 *
 */

public class FragementFilter {
	public static void fileterBold(String source, String type) throws IOException {
		String dirname = "gbk/fragement/" + type + "/" + source;
		File dir = new File(dirname);
		String newdirname = "gbk/fragement/" + type + "Filter/" + source;
		File newdir = new File(newdirname);
		CreateDir.createDir(newdirname);
		
		List<Topic> topics = TopicExtractor.extractTopicFromFile(source, source);
		Map<String, String> noTopic = new TreeMap<String, String>();
		for (int i = 0; i < topics.size(); i++) {
			noTopic.put(Format.get0X(i + 1), topics.get(i).getQuery());
		}
		
		String[] filenames = dir.list();
		for (int i = 0; i < filenames.length; i++) {
			System.out.println(filenames[i] + " " + noTopic.get(filenames[i]));
			filterLength(filenames[i], source, type, noTopic.get(filenames[i]));
		}
	}
	
	private static void filterLength(String filename, String source, String type, String query) {
		try {
			ReadFile rf = new ReadFile( "gbk/fragement/" + type + "/" + source + "/" + filename, Constants.GBK);
			WriteFile wf = new WriteFile("gbk/fragement/" + type + "Filter/" + source + "/" + filename, false, Constants.GBK);
			String line = "";
			while((line = rf.readLine()) != null) {
				if (line.length() < 5)
					continue;
				String[] lineS = line.split(" ");
				String[] querys = query.split(" ");
				if (lineS.length <= querys.length)
					continue;
				
				wf.writeLine(line, "\n");
			}
			
			wf.close();
			rf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		fileterBold("TREC09", "plain");
		fileterBold("TREC10", "plain");
		fileterBold("TREC11", "plain");
		fileterBold("NTCIR-9", "plain");
		fileterBold("TREC09", "bold");
		fileterBold("TREC10", "bold");
		fileterBold("TREC11", "bold");
		fileterBold("NTCIR-9", "bold");
	}
}
