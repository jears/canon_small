package cslt.util.fileOperation;

import java.io.File;

public class CreateDir {
	public static void createDir(String dirName) {
		File dir = new File(dirName);
		if (!dir.exists()) 
			dir.mkdirs();
	}
}
