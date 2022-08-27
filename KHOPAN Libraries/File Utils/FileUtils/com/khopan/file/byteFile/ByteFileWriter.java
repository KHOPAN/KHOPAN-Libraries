package com.khopan.file.byteFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import com.khopan.file.ExceptionHandler;
import com.khopan.list.ItemList;

public class ByteFileWriter {
	private final File File;
	private final ExceptionHandler Handler;
	private final ItemList<Byte> ByteList;

	public ByteFileWriter(File File, ExceptionHandler Handler) {
		this.File = Objects.requireNonNull(File);	
		this.Handler = Objects.requireNonNull(Handler);
		this.ByteList = new ItemList<>();
	}

	public void writeByte(int Byte) {
		this.ByteList.add((byte) Byte);
	}

	public void writeBytes(int... Bytes) {
		Objects.requireNonNull(Bytes);

		for(int Byte : Bytes) {
			this.ByteList.add((byte) Byte);
		}
	}

	public void build() {
		try {
			FileOutputStream Stream = new FileOutputStream(this.File);

			for(byte Byte : this.ByteList.list()) {
				Stream.write(Byte);
			}

			Stream.close();
		} catch(IOException Exception) {
			this.Handler.exception(Exception);
		}
	}
}
