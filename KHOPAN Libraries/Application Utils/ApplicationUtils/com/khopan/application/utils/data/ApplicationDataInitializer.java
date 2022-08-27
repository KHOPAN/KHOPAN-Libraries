package com.khopan.application.utils.data;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import com.khopan.file.ExceptionHandler;
import com.khopan.file.FileUtils;
import com.khopan.list.ItemMap;

public class ApplicationDataInitializer {
	public static final int STATUS_BOTH_FILE_AND_DIRECTORY_EXIST = 0b00;
	public static final int STATUS_FILE_DOESNT_EXIST = 0b01;
	public static final int STATUS_BOTH_FILE_AND_DIRECTORY_DOESNT_EXIST = 0b11;

	private ApplicationDataInitializer() {}

	public static int createDataFile(File DataFile, ExceptionHandler Handler) {
		Objects.requireNonNull(DataFile);

		int Status = ApplicationDataInitializer.STATUS_BOTH_FILE_AND_DIRECTORY_EXIST;

		if(!DataFile.getParentFile().exists()) {
			Status = (Status | ApplicationDataInitializer.STATUS_BOTH_FILE_AND_DIRECTORY_DOESNT_EXIST);

			if(!DataFile.getParentFile().mkdirs()) {
				throw new InternalError("Cannot create file's parent directory.");
			}
		}

		if(!DataFile.exists()) {
			Status = (Status | ApplicationDataInitializer.STATUS_FILE_DOESNT_EXIST);

			FileUtils.serialize(DataFile, new ApplicationData(), Handler);
		}

		return Status;
	}

	public static boolean isFileExist(int Status) {
		return (Status & ApplicationDataInitializer.STATUS_FILE_DOESNT_EXIST) == 0;
	}

	public static boolean isDirectoryExist(int Status) {
		return Status != ApplicationDataInitializer.STATUS_BOTH_FILE_AND_DIRECTORY_DOESNT_EXIST;
	}

	public static void putData(File DataFile, String DataKey, Serializable DataValue, ExceptionHandler Handler) {
		ApplicationData Data = FileUtils.deserialize(DataFile, ApplicationData.class, Handler);
		Data.Map.put(DataKey, DataValue);
		FileUtils.serialize(DataFile, Data, Handler);
	}

	public static void setData(File DataFile, String DataKey, Serializable DataValue, ExceptionHandler Handler) {
		ApplicationData Data = FileUtils.deserialize(DataFile, ApplicationData.class, Handler);
		ItemMap<String, Serializable> Map = Data.Map;

		if(Map.has(DataKey)) {
			Map.remove(DataKey);
		}

		Map.put(DataKey, DataValue);

		FileUtils.serialize(DataFile, Data, Handler);
	}

	public static <T> T getData(File DataFile, String DataKey, Class<T> OutputType, ExceptionHandler Handler) {
		return FileUtils.deserialize(DataFile, ApplicationData.class, Handler).get(DataKey, OutputType);
	}
}
