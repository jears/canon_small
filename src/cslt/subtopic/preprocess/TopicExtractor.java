package cslt.subtopic.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cslt.subtopic.model.Topic;
import cslt.util.fileOperation.ReadFile;

//TODO:预处理程序，从文件中读取话题，返回List<Topic>
public class TopicExtractor {
	public static List<Topic> extractTopicFromFile(String source, String filename) throws IOException {
		List<Topic> topics = new ArrayList<Topic>();

		ReadFile rf = new ReadFile(filename);

		String line = null;
		int i = 0;
		while ((line = rf.readLine()) != null) {
			String query = line.substring(line.indexOf(":") + 1);
			i++;
			topics.add(new Topic(i, query, source));
		}

		rf.close();

		return topics;
	}
}
