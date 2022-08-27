package com.khopan.project.textEditor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipHandler {
	public static void compressZipDirectory(String DirectoryPath, String ZipFile) {
		try {
			Path Path = Files.createFile(Paths.get(ZipFile));

			try(ZipOutputStream ZipOutputStream = new ZipOutputStream(Files.newOutputStream(Path))) {
				Path PathTemp = Paths.get(DirectoryPath);

				Files.walk(PathTemp).filter(PathItem -> !Files.isDirectory(PathItem)).forEach(PathItem -> {
					ZipEntry Entry = new ZipEntry(PathTemp.relativize(PathItem).toString());

					try {
						ZipOutputStream.putNextEntry(Entry);
						Files.copy(PathItem, ZipOutputStream);
						ZipOutputStream.closeEntry();
					} catch(Throwable Errors) {
						Errors.printStackTrace();
					}
				});
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	public static void decompressZipDirectory(String OutputPath, String ZipFile) {
		try {
			int Buffer = 2048;
			File File = new File(ZipFile);

			ZipFile Zip = new ZipFile(File);
			String NewPath = OutputPath;

			new File(NewPath).mkdir();
			Enumeration<? extends ZipEntry> ZipFileEntries = Zip.entries();

			// Process each entry
			while(ZipFileEntries.hasMoreElements()) {
				// grab a zip file entry
				ZipEntry Entry = (ZipEntry) ZipFileEntries.nextElement();
				String CurrentEntry = Entry.getName();

				File DestinationFile = new File(NewPath, CurrentEntry);
				//destFile = new File(newPath, destFile.getName());
				File DestinationParent = DestinationFile.getParentFile();

				// create the parent directory structure if needed
				DestinationParent.mkdirs();

				if(!Entry.isDirectory()) {
					BufferedInputStream BufferedInputStream = new BufferedInputStream(Zip.getInputStream(Entry));
					int CurrentByte;
					// establish buffer for writing file
					byte Data[] = new byte[Buffer];

					// write the current file to disk
					FileOutputStream FileOutputStream = new FileOutputStream(DestinationFile);
					BufferedOutputStream BufferedOutputStream = new BufferedOutputStream(FileOutputStream, Buffer);

					// read and write until last byte is encountered
					while((CurrentByte = BufferedInputStream.read(Data, 0, Buffer)) != -1) {
						BufferedOutputStream.write(Data, 0, CurrentByte);
					}

					BufferedOutputStream.flush();
					BufferedOutputStream.close();
					BufferedInputStream.close();
				}
			}

			Zip.close();
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	/*public static void decompressZipDirectory(String OutputPath, String ZipFile) {
		Path DirectoryPath = new File(OutputPath).toPath();

		try(ZipInputStream ZipInputStream = new ZipInputStream(Files.newInputStream(new File(ZipFile).toPath()))) {
			ZipEntry Entry = ZipInputStream.getNextEntry();

			while(Entry != null) {
				Path Path = DirectoryPath.resolve(Entry.getName());

				if(Entry.isDirectory()) {
					Files.createDirectories(Path);
				} else {
					if(!Files.exists(Path.getParent())) {
						Files.createDirectories(Path.getParent());
					}

					try(OutputStream OutputStream = Files.newOutputStream(DirectoryPath.resolve(Path))) {
						byte[] Buffer = new byte[Math.toIntExact(Entry.getSize())];

						int Location;

						while((Location = ZipInputStream.read(Buffer)) != -1) {
							OutputStream.write(Buffer, 0, Location);
						}
					}
				}

				Entry = ZipInputStream.getNextEntry();
			}
		} catch (Throwable Errors) {
			Errors.printStackTrace();
		}
	}*/
}
