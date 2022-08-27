package com.khopan.file.byteFile;

import java.io.File;

import com.khopan.file.ExceptionHandler;

public class ByteFileEditor {
	private final ByteFileReader Reader;
	private final ByteFileWriter Writer;

	public ByteFileEditor(File File, ExceptionHandler Handler) {
		this.Reader = new ByteFileReader(File, Handler);
		this.Writer = new ByteFileWriter(File, Handler);
	}

	public ByteFileReader getReader() {
		return this.Reader;
	}

	public ByteFileWriter getWriter() {
		return this.Writer;
	}
}
