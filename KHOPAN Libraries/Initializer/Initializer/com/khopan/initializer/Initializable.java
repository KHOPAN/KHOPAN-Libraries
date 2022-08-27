package com.khopan.initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Initializable {
	public static final int NO_PARAMETER = -1;
	public static final int ALL_PARAMETER = -2;
	public static final int ARRAY_OF_ALL_PARAMETER = -3;

	public static void initialize(Object... ParameterArgs) {
		try {
			boolean Found = false;
			String Main = null;

			for(StackTraceElement Element : Thread.currentThread().getStackTrace()) {
				String Name = Element.getClassName();

				if(Found) {
					Main = Name;
					break;
				} else if(Initializable.class.getName().equals(Name) && Element.getMethodName().equals("initialize")) {
					Found = true;
				}
			}

			if(Main == null) {
				throw new RuntimeException("Error: unable to determine Initializable class.");
			} else {
				Class<?> MainClass = Class.forName(Main, false, Thread.currentThread().getContextClassLoader());
				Class<Initializer> InitializerAnnoation = Initializer.class;
				Object Instance = null;

				for(Constructor<?> Constructor : MainClass.getDeclaredConstructors()) {
					Initializer Annotation = Constructor.getAnnotation(InitializerAnnoation);

					if(Annotation != null) {
						Instance = Constructor.newInstance(Initializable.getParameterArgs(ParameterArgs, Annotation));
					}
				}

				if(Instance == null) {
					Instance = MainClass.getDeclaredConstructor().newInstance();
				}

				for(Method Method : MainClass.getDeclaredMethods()) {
					Initializer Annotation = Method.getAnnotation(InitializerAnnoation);

					if(Annotation != null) {
						Method.invoke(Instance, Initializable.getParameterArgs(ParameterArgs, Annotation));
					}
				}
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}
	}

	private static Object[] getParameterArgs(Object[] ParameterArgs, Initializer Annotation) {
		ArrayList<Object> Arguments = new ArrayList<>();
		int[] Indexes = Annotation.value();

		for(int Index : Indexes) {
			if(Index >= 0) {
				if(ParameterArgs != null) {
					int Length = ParameterArgs.length;

					if(Length == Index) {
						Arguments.add(ParameterArgs[Index - 1]);
					} else if(Length < Index) {
						Arguments.add(ParameterArgs[(Index % Length) - 1]);
					} else {
						Arguments.add(ParameterArgs[Index]);
					}
				}
			} else {
				if(Index == Initializable.NO_PARAMETER) {
					continue;
				} else if(Index == Initializable.ALL_PARAMETER) {
					for(Object Argument : ParameterArgs) {
						Arguments.add(Argument);
					}
				} else if(Index == Initializable.ARRAY_OF_ALL_PARAMETER) {
					Arguments.add(ParameterArgs);
				}
			}
		}

		return Arguments.toArray();
	}
}
