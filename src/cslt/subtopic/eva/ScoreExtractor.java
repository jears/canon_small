package cslt.subtopic.eva;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import cslt.util.fileOperation.ReadFile;
import cslt.util.fileOperation.WriteFile;

/**
 * 从评测结果文档中抽取得分
 * @author jears
 *
 */
public class ScoreExtractor {
	ArrayList<Score> scores;
	ArrayList<Score> scores0;
	final int numOfMetric;
	final int numOfBaseline;
	final int numOfTopic;

	public ScoreExtractor(int numOfBaseline, int numOfTopic) throws IOException {
		this.numOfBaseline = numOfBaseline;
		numOfMetric = 3*numOfBaseline;
		this.numOfTopic = numOfTopic;
		scores = new ArrayList<ScoreExtractor.Score>();
		for (int i = 0; i < numOfTopic; ++i) {
			Score s = new Score();
			scores.add(s);
		}

		scores0 = new ArrayList<ScoreExtractor.Score>();
		for (int i = 0; i < numOfTopic; ++i) {
			Score s = new Score();
			scores0.add(s);
		}
	}

	public void setCompare(ArrayList<Score> s) {
		scores0 = s;
	}

	public ArrayList<Score> getCompare() {
		return scores;
	}

	public void extractorScore(String run, int runno) throws IOException {
		String[] filename = { run + ".l10.Dnev", run + ".l20.Dnev",
				run + ".l30.Dnev" };
		for (int i = 0; i < 3; i++) {
			extractorScoreOfCutOff(filename[i], runno, (i + 1));
		}
	}

	public void extractorScoreOfCutOff(String filename, int runno, int cutoff)
			throws IOException {
		ReadFile rf = new ReadFile(filename);
		int k = (runno - 1) * 3 + cutoff - 1;

		String line = "";
		while ((line = rf.readLine()) != null) {
			if (line.indexOf("Dsharp-MSnDCG") != -1) {
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicno - 1).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);

				scores.get(topicno - 1).scores[2][k] = score;

			} else if (line.indexOf("MSnDCG@0") != -1) {
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicno - 1).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);

				scores.get(topicno - 1).scores[1][k] = score;
			} else if (line.indexOf("I-rec@0") != -1) {
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicno - 1).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);
				scores.get(topicno - 1).scores[0][k] = score;
			}
		}

		rf.close();
	}

	public void wsiToString() {
		System.out.println(scores.get(19).LDAK());
	}
	public String toString() {
		String ret = "";
		for (int i = 0; i < scores.size(); i++) {
			System.out.println(scores.get(i));
			ret += scores.get(i) + "\n";
		}

		return ret;
	}
	
	public String select() {
		Integer[] filterNo = {9, 10, 11, 12, 39, 45, 46, 47, 48, 49, 50};
		Set<Integer> filter = new HashSet<Integer>(Arrays.asList(filterNo));
		String ret = "";
		
		Score s = new Score();
		
		for (int i = 0; i < scores.size(); i++) {
			if (filter.contains(i+1)) {
				s.add(scores.get(i));
			System.out.println(scores.get(i));
			ret += scores.get(i) + "\n";
			}
		}
		
		System.out.println(s);

		return ret;
	}

	public String compare() {
		String ret = "";
		for (int i = 0; i < scores.size(); i++) {
			System.out.println(scores.get(i).compare(scores0.get(i)));
			ret += scores.get(i) + "\n";
		}

		return ret;
	}
	public String compare(boolean better) {
		String ret = "";
		for (int i = 0; i < scores.size(); i++) {
			System.out.println(scores.get(i).compare(scores0.get(i),better));
			ret += scores.get(i) + "\n";
		}

		return ret;
	}

	class Score {
		String topicno;
		double[][] scores = new double[3][numOfMetric];
		
		public void add(Score s0) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < numOfMetric; j++) {
					this.scores[i][j] += s0.scores[i][j];
				}
			}
		}

		public String LDAK() {
			String ret = "";
			for(int i = 0; i < numOfBaseline; ++i) {
				ret += scores[2][3*i] + "\t";
				ret += scores[2][3*i+1] + "\t";
				ret += scores[2][3*i+2] + "\n";
			}
			return ret;
		}
		
		public String wsi() {
			String ret = "";
			for (int i = 0; i < numOfBaseline; ++i) {
				for(int j = 0; j < 3; j++) {
					switch (j) {
					case 0:
						ret += "I-rec\t";
						break;
					case 1:
						ret += "nDCG\t";
						break;
					case 2:
						ret += "D-nDCG\t";
						break;
					}
					ret += scores[j][3*i] + "\t";
					ret += scores[j][3*i+1] + "\t";
					ret += scores[j][3*i+2] + "\n";
				}
				
				ret += "\n";
			}
			return ret;
		}
		
		public String toString() {
			String ret = "";

			for (int i = 0; i < 3; i++) {
				ret += topicno + "\t";
				switch (i) {
				case 0:
					ret += "I-rec\t";
					break;
				case 1:
					ret += "nDCG\t";
					break;
				case 2:
					ret += "D-nDCG\t";
					break;
				}
				for (int j = 0; j < numOfMetric; j++) {
					ret += scores[i][j] + "\t";
				}
				ret += "\n";
			}

			// ret+="\n";
			return ret;
		}

		public String compare(Score s0) {
			String ret = "";

			for (int i = 0; i < 3; i++) {
				ret += topicno + "\t";
				switch (i) {
				case 0:
					ret += "I-rec\t";
					break;
				case 1:
					ret += "nDCG\t";
					break;
				case 2:
					ret += "D-nDCG\t";
					break;
				}
				for (int j = 0; j < numOfMetric; j++) {
					ret += scores[i][j] + "\t";
				}

				ret += "\n\t\t";

				for (int j = 0; j < numOfMetric; j++) {
					ret += s0.scores[i][j] + "\t";
				}
				ret += "\n";
			}

			// ret+="\n";
			return ret;
		}

		public String compare(Score s0, boolean better) {
			String ret = "";

			for (int i = 0; i < 3; i++) {
				ret += topicno + "\t";
				switch (i) {
				case 0:
					ret += "I-rec\t";
					break;
				case 1:
					ret += "nDCG\t";
					break;
				case 2:
					ret += "D-nDCG\t";
					break;
				}
				for (int j = 0; j < numOfMetric; j++) {
					if ((s0.scores[i][j] < scores[i][j]) == better)
						ret += scores[i][j] + "\t";
					else
						ret += "\t";
				}

				ret += "\n\t\t";

				for (int j = 0; j < numOfMetric; j++) {
					if ((s0.scores[i][j] < scores[i][j]) == better)
						ret += s0.scores[i][j] + "\t";
					else
						ret += "\t";
				}
				ret += "\n";
			}

			ret += "\n";
			return ret;
		}
		
	}

	public void check() {
		for (int i = 0; i < numOfTopic; i++) {
			Score s = scores.get(i);
			Score s0 = scores0.get(i);
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < numOfMetric; k++) {
					if (s.scores[j][k] < s0.scores[j][k])
						System.out.println(i + "\t" + j + "\t" + k);
				}
			}
		}
	}

	public void extractMeanScore(String path, String name) throws IOException {
		Score s = new Score();
		if (path.endsWith("/") || path.endsWith("\\"))
			;
		else
			path += "/";
		String prefix = path +  "runlist." + name + ".Iprob.l";
		String[] metrics = { ".Dnev.I-rec@00", ".Dnev.MSnDCG@00",
				".Dnev.Dsharp-MSnDCG@00" };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String filename = prefix + (j + 1) * 10 + metrics[i] + (j + 1)
						* 10;
				ReadFile rf = new ReadFile(filename);

				String line = "";
				for (int k = 0; k < numOfBaseline; k++) {
					line = rf.readLine();
//					System.out.println(line);
					Scanner c = new Scanner(line);
					c.next();
					double score = c.nextDouble();
					s.scores[i][k * 3 + j] = score;
				}

				rf.close();
			}
		}
		System.out.println(s);
		System.out.println(s.LDAK());
	}

	public static void main(String[] args) throws IOException {
		String path = "data/manual/";
		
		ArrayList<String> runs = new ArrayList<String>();
		ReadFile rf = new ReadFile(path + "/runlist");
		String line = "";
		while((line = rf.readLine()) != null) {
			runs.add(line);
		}
		rf.close();
		
		ScoreExtractor test = new ScoreExtractor(runs.size(), 56);
		for (int i = 0; i < runs.size(); i++)
			test.extractorScore(path + "/" + runs.get(i), i+1);

		test.toString();
//		test.select();
		
		test.extractMeanScore(path, "manualcannon");
		
		
//		 ScoreExtractor test0 = new ScoreExtractor();
//		 test0.extractorScore("data/before/baseline1", 1);
//		 test0.extractorScore("data/before/baseline2", 2);
//		 test0.extractorScore("data/before/baseline3", 3);
//		 test0.extractorScore("data/before/baseline4", 4);
//		 test0.toString();
//		 test0.extractMeanScore("data/before/");
//
//		 test.setCompare(test0.getCompare());
//		 test.compare();
//		 test.compareBetter();
//		 test.compareWorse();
	}
}
