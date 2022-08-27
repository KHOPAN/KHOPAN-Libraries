package com.khopan.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.khopan.file.byteFile.ByteFileReader;
import com.khopan.file.byteFile.ByteFileWriter;

public class FileUtils {
	public static String readFile(File File, ExceptionHandler Handler) {
		Objects.requireNonNull(Handler);

		if(File == null) {
			return null;
		}

		String Line = "";
		String LineSeparator = System.getProperty("line.separator");
		String Text = "";

		try {
			BufferedReader Reader = null;

			try {
				Reader = new BufferedReader(new FileReader(File));

				while((Line = Reader.readLine()) != null) {
					Text += Line + LineSeparator;
				}
			} finally {
				Reader.close();
			}
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}

		return Text;
	}

	public static String readFile(String Filepath, ExceptionHandler Handler) {
		return FileUtils.readFile(new File(Filepath), Handler);
	}

	public static void writeFile(File File, String Text, ExceptionHandler Handler) {
		Objects.requireNonNull(Handler);

		try {
			BufferedWriter Writer = null;

			try {
				Writer = new BufferedWriter(new FileWriter(File));
				Writer.write(Text);
			} finally {
				Writer.close();
			}
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}
	}

	public static void writeFile(String Text, File File, ExceptionHandler Handler) {
		FileUtils.writeFile(File, Text, Handler);
	}

	public static void writeFile(String Filepath, String Text, ExceptionHandler Handler) {
		FileUtils.writeFile(new File(Filepath), Text, Handler);
	}

	public static File changeExtension(File File, String Extension) {
		Extension = Extension.toLowerCase();

		if(FileUtils.getExtension(File).equals(Extension)) {
			return File;
		}

		String Path = File.getAbsolutePath();

		if(
				Path == null ||
				Path.equals("")
				) {
			return File;
		}

		String FileSeperator = System.getProperty("file.separator");
		String Dot = ".";
		String FileExtension = Extension == null || Extension.equals("") ? "" : Dot + Extension;
		String FileName = null;
		String NewFileName = null;

		if(Path.contains(FileSeperator)) {
			FileName = Path.substring(Path.lastIndexOf(FileSeperator) + 1);
		} else {
			FileName = Path;
		}

		if(FileName.contains(Dot)) {
			NewFileName = FileName.substring(0, FileName.lastIndexOf(Dot));
		} else {
			NewFileName = FileName;
		}

		return new File(Path.substring(0, Path.lastIndexOf(FileSeperator) + 1) + NewFileName + FileExtension);
	}

	public static String getExtension(File File) {
		String Path = File.getAbsolutePath();

		if(
				Path == null ||
				Path.equals("")
				) {
			return null;
		}

		String FileSeperator = System.getProperty("file.separator");
		String Dot = ".";
		String FileName = null;

		if(Path.contains(FileSeperator)) {
			FileName = Path.substring(Path.lastIndexOf(FileSeperator) + 1);
		} else {
			FileName = Path;
		}

		if(FileName.contains(Dot)) {
			return FileName.substring(FileName.lastIndexOf(Dot) + 1).toLowerCase();
		} else {
			return null;
		}
	}

	public static boolean isExtensionPresent(File File, String Extension) {
		if(FileUtils.getExtension(File).equals(Extension.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static void serialize(File File, Serializable Object, ExceptionHandler Handler) {
		Objects.requireNonNull(Handler);

		try {
			FileOutputStream Stream = new FileOutputStream(File);
			ObjectOutputStream ObjectStream = new ObjectOutputStream(Stream);
			ObjectStream.writeObject(Object);
			ObjectStream.close();
			Stream.close();
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}
	}

	public static void serialize(File File, Serializable Object, String Extension, boolean RequiredExtension, ExceptionHandler Handler) {
		if(RequiredExtension) {
			if(!FileUtils.isExtensionPresent(File, Extension)) {
				throw new IllegalArgumentException("File '" + File.getAbsolutePath() + "' must have an extension of '" + Extension.toLowerCase() + "'");
			}
		} else {
			File = FileUtils.changeExtension(File, Extension);
		}

		FileUtils.serialize(File, Object, Handler);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(File File, Class<T> Type, ExceptionHandler Handler) {
		Objects.requireNonNull(Handler);
		T Return = null;

		try {
			FileInputStream Stream = new FileInputStream(File);
			ObjectInputStream ObjectStream = new ObjectInputStream(Stream);
			Return = (T) ObjectStream.readObject();
			ObjectStream.close();
			Stream.close();
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}

		return Return;
	}

	public static <T extends Serializable> T deserialize(File File, Class<T> Type, String Extension, ExceptionHandler Handler) {
		if(!FileUtils.isExtensionPresent(File, Extension)) {
			throw new IllegalArgumentException("File '" + File.getAbsolutePath() + "' must have an extension of '" + Extension.toLowerCase() + "'");
		}

		return FileUtils.deserialize(File, Type, Handler);
	}

	public static byte[] compress(byte[] Input, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);

		try {
			ByteArrayOutputStream Stream = new ByteArrayOutputStream();
			GZIPOutputStream Zip = new GZIPOutputStream(Stream);
			Zip.write(Input);
			Zip.close();
			byte[] ByteArray = Stream.toByteArray();
			Stream.close();

			return ByteArray;
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}

		return null;
	}

	public static void compress(byte[] Input, File Output, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);
		Objects.requireNonNull(Output);

		ByteFileWriter Writer = new ByteFileWriter(Output, Handler);

		for(byte Byte : FileUtils.compress(Input, Handler)) {
			Writer.writeByte(Byte);
		}

		Writer.build();
	}

	public static byte[] compress(File Input, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);

		return FileUtils.compress(new ByteFileReader(Input, Handler).getAllByte(), Handler);
	}

	public static void compress(File Input, File Output, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);
		Objects.requireNonNull(Output);

		FileUtils.compress(new ByteFileReader(Input, Handler).getAllByte(), Output, Handler);
	}

	public static byte[] decompress(byte[] Input, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);

		try {
			ByteArrayInputStream Stream = new ByteArrayInputStream(Input);
			GZIPInputStream Zip = new GZIPInputStream(Stream);
			byte[] ByteArray = Zip.readAllBytes();
			Zip.close();
			Stream.close();

			return ByteArray;
		} catch(Exception Exception) {
			Handler.exception(Exception);
		}

		return null;
	}

	public static void decompress(byte[] Input, File Output, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);
		Objects.requireNonNull(Output);

		ByteFileWriter Writer = new ByteFileWriter(Output, Handler);

		for(byte Byte : FileUtils.decompress(Input, Handler)) {
			Writer.writeByte(Byte);
		}

		Writer.build();
	}

	public static byte[] decompress(File Input, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);

		return FileUtils.decompress(new ByteFileReader(Input, Handler).getAllByte(), Handler);
	}

	public static void decompress(File Input, File Output, ExceptionHandler Handler) {
		Objects.requireNonNull(Input);
		Objects.requireNonNull(Output);

		FileUtils.decompress(new ByteFileReader(Input, Handler).getAllByte(), Output, Handler);
	}

	public static synchronized Cancellable addChangeListener(File File, FileChangeListener Listener) {
		return new Cancellable() {
			public ScheduledFuture<?> Future;

			{
				Future = FileUtils.getWatcherTask(File, Listener);
			}

			@Override
			public void cancel() {
				Future.cancel(false);
			}

			@Override
			public void enableFileWatcher() {
				Future = FileUtils.getWatcherTask(File, Listener);
			}

			@Override
			public void disableFileWatcher() {
				this.cancel();
			}
		};
	}

	private static ScheduledFuture<?> getWatcherTask(File File, FileChangeListener Listener) {
		return Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
			public long Time;
			public long RunningTime;
			public File UpdatedFile;

			{
				this.Time = File.lastModified();
			}

			@Override
			public void run() {
				this.RunningTime = File.lastModified();

				if(this.Time != this.RunningTime) {
					this.Time = this.RunningTime;
					Listener.fileChanged(File);
				}
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
	}
}
