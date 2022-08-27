package com.khopan.file.byteFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import com.khopan.file.ExceptionHandler;

public class ByteFileReader {
	private final File File;
	private final ExceptionHandler Handler;
	private final byte[] Bytes;

	private int Pointer = -1;

	public ByteFileReader(File File, ExceptionHandler Handler) {
		this.File = Objects.requireNonNull(File);
		this.Handler = Objects.requireNonNull(Handler);
		byte[] Bytes;

		try {
			FileInputStream Stream = new FileInputStream(this.File);
			Bytes = Stream.readAllBytes();
			Stream.close();
		} catch(IOException Exception) {
			Bytes = new byte[] {};
			Handler.exception(Exception);
		}

		this.Bytes = Bytes;
	}

	public void resetPointerLocation() {
		this.Pointer = -1;
	}

	public void setPointerLocation(int PointerLocation) {
		if(PointerLocation >= -1 && PointerLocation < Bytes.length - 1) {
			this.Pointer = PointerLocation;
		}

		this.Handler.exception(new IndexOutOfBoundsException("Index " + PointerLocation + " out of bounds for length " + Bytes.length + "."));
	}

	public byte readNextByte() {
		if(this.hasNext()) {
			this.Pointer++;
			return this.Bytes[this.Pointer];
		} else {
			return Byte.MIN_VALUE;
		}
	}

	public boolean hasNext() {
		return this.Pointer <= this.Bytes.length - 1;
	}

	public byte[] readNextNBytes(int n) {
		if(this.Pointer + n >= this.Bytes.length) {
			this.Handler.exception(new ArrayIndexOutOfBoundsException("Index out of bounds for length " + (this.Bytes.length - 1) + "."));
		}

		byte[] Bytes = new byte[n];

		for(int i = 0; i < n; i++) {
			Bytes[i] = this.readNextByte();
		}

		return Bytes;
	}

	public byte[] readAllLeftByte() {
		return this.readNextNBytes((this.Bytes.length - 1) - this.Pointer);
	}

	public byte[] getAllByte() {
		return this.Bytes;
	}
}
