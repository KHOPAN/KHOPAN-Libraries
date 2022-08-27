package javax.microedition.shell;

import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import filelog.Log;

public class MyClassLoader extends DexClassLoader {
	
	private static final String tag = MyClassLoader.class.getName();
	private File resFolder;
	
	public MyClassLoader(String paths, String tmpDir, String libs, ClassLoader parent, String resDir) {
		super(paths, tmpDir, libs, parent);
		this.resFolder = new File(resDir);
	}

	@Override
	public InputStream getResourceAsStream(String resName) {
		System.err.println("CUSTOM GET RES CALLED WITH PATH: " + resName);
		Log.d(tag, "CUSTOM GET RES CALLED WITH PATH: " + resName);
		try {
			return new FileInputStream(new File(resFolder, resName));
		} catch (FileNotFoundException e) {
			System.err.println("Can't load res " + resName + " on path: " + resFolder.getPath() + resName);
			Log.d(tag, "Can't load res " + resName + " on path: " + resFolder.getPath() + resName);
			return null;
		}
	}
}
