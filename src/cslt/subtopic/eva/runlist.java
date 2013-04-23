package cslt.subtopic.eva;

import java.io.File;
import java.io.IOException;

import cslt.util.fileOperation.WriteFile;

public class runlist {
	public static void main(String[] args) throws IOException {
		File f = new File(args[0]);
		String[] filenames = f.list();
		WriteFile wf = new WriteFile("runlist", false);
		for (int i = 0; i < filenames.length; i++) {
			wf.write(filenames[i] + "\n");
		}
		wf.close();
	}
}
