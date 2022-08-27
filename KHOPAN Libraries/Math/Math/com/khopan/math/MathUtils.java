package com.khopan.math;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MathUtils {
	public static String toHex(String Text) throws Throwable {
		return String.format("%040x", new BigInteger(1, Text.getBytes("UTF-8"))).toUpperCase();
	}

	public static String fromHex(String Hexadecimal) throws Throwable {
		Hexadecimal = Hexadecimal.toLowerCase().replaceAll("^(00)+", "");
		byte[] Bytes = new byte[Hexadecimal.length() / 2];

		for (int i = 0; i < Hexadecimal.length(); i += 2) {
			Bytes[i / 2] = (byte) ((Character.digit(Hexadecimal.charAt(i), 16) << 4) + Character.digit(Hexadecimal.charAt(i + 1), 16));
		}

		return new String(Bytes);
	}

	public static double map(double Number, double InputMin, double InputMax, double OutputMin, double OutputMax) {
		return (Number - InputMin) * (OutputMax - OutputMin) / (InputMax - InputMin) + OutputMin;
	}

	public static double protectedMap(double Number, double InputMin, double InputMax, double OutputMin, double OutputMax) {
		return Number < InputMin ? MathUtils.map(InputMin, InputMin, InputMax, OutputMin, OutputMax) : Number > InputMax ? MathUtils.map(InputMax, InputMin, InputMax, OutputMin, OutputMax) : MathUtils.map(Number, InputMin, InputMax, OutputMin, OutputMax);
	}

	public static int length(double Number1, double Number2) {
		return (int) (Number1 >= Number2 ? Number1 - Number2 : Number2 - Number1);
	}

	public static String getHexadeciamal(Color Color) {
		return "0x" + String.format("%02x%02x%02x", Color.getRed(), Color.getGreen(), Color.getBlue()).toUpperCase();
	}

	@SafeVarargs
	public static <T> T random(T... Inputs) {
		if(Inputs == null) {
			return null;
		}

		Random Random = ThreadLocalRandom.current();

		return Inputs[Random.nextInt(Inputs.length)];
	}

	@SuppressWarnings("unchecked")
	public static <T extends Enum<?>> T randomEnum(Class<? extends T> Enum) {
		Field Field = MathUtils.random(Enum.getFields());
		T Value = null;

		try {
			Value = (T) Field.get(null);
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}

		return Value;
	}

	public static double multiply(double... Numbers) {
		if(Numbers == null) {
			return Double.MIN_VALUE;
		}

		if(Numbers.length == 1) {
			return Numbers[0];
		}

		double Temp = Numbers[0];

		for(int i = 1; i < Numbers.length; i++) {
			Temp *= Numbers[i];
		}

		return Temp;
	}

	@SafeVarargs
	public static <T> void runCase(T Value, Case<T>... Cases) {
		if(Cases == null) {
			return;
		}

		for(Case<T> Case : Cases) {
			InnerLoop:
				for(T Checking : Case.Cases) {
					if(Checking.equals(Value)) {
						Case.Executor.execute(Value);
						break InnerLoop;
					}
				}
		}
	}

	public static BigInteger intToBigInt(int Number) {
		return new BigInteger(Integer.toString(Number));
	}

	public static int bigIntToInt(BigInteger Number) {
		return Number.intValue();
	}

	public static String convertNumberToBaseN(int Number, char[] n) {
		return MathUtils.convertNumberToBaseN(MathUtils.intToBigInt(Number), n);
	}

	public static String convertNumberToBaseN(int Number, char[] n, int DigitCount) {
		return MathUtils.convertNumberToBaseN(MathUtils.intToBigInt(Number), n, DigitCount);
	}

	public static String convertNumberToBaseN(BigInteger Number, char[] n) {
		Objects.requireNonNull(Number);

		if(n == null || n.length <= 1) {
			return null;
		}

		int DigitCount = 0;
		int i = 0;

		while(true) {
			if(Number.compareTo(MathUtils.intToBigInt(n.length).pow(i)) == -1) {
				DigitCount = i;
				break;
			} else {
				i++;
			}
		}

		return MathUtils.convertNumberToBaseN(Number, n, DigitCount);
	}

	public static String convertNumberToBaseN(BigInteger Number, char[] n, int DigitCount) {
		Objects.requireNonNull(Number);

		if(n == null || n.length <= 1) {
			return null;
		}

		String Text = "";

		for(int i = 0; i < DigitCount; i++) {
			char Character = n[Number.divide(MathUtils.intToBigInt(n.length).pow(i)).mod(MathUtils.intToBigInt(n.length)).intValue()];
			Text = Character + Text;
		}

		return Text;
	}

	public static byte[] convertNumberToByteArray(int Number, byte[] ByteValues) {
		return MathUtils.convertNumberToByteArray(MathUtils.intToBigInt(Number), ByteValues);
	}

	public static byte[] convertNumberToByteArray(int Number, byte[] ByteValues, int DigitCount) {
		return MathUtils.convertNumberToByteArray(MathUtils.intToBigInt(Number), ByteValues, DigitCount);
	}

	public static byte[] convertNumberToByteArray(BigInteger Number, byte[] ByteValues) {
		Objects.requireNonNull(Number);

		if(ByteValues == null || ByteValues.length <= 1) {
			return null;
		}

		int DigitCount = 0;
		int i = 0;

		while(true) {
			if(Number.compareTo(MathUtils.intToBigInt(ByteValues.length).pow(i)) == -1) {
				DigitCount = i;
				break;
			} else {
				i++;
			}
		}

		return MathUtils.convertNumberToByteArray(Number, ByteValues, DigitCount);
	}

	public static byte[] convertNumberToByteArray(BigInteger Number, byte[] ByteValues, int DigitCount) {
		Objects.requireNonNull(Number);

		if(ByteValues == null || ByteValues.length <= 1) {
			return null;
		}

		Byte[] ByteArray = new Byte[DigitCount];

		for(int i = 0; i < DigitCount; i++) {
			Byte Byte = ByteValues[Number.divide(MathUtils.intToBigInt(ByteValues.length).pow(i)).mod(MathUtils.intToBigInt(ByteValues.length)).intValue()];
			ByteArray[(int) MathUtils.map(i, 0, DigitCount - 1, DigitCount - 1, 0)] = Byte;
		}

		byte[] PrimitiveByteArray = new byte[ByteArray.length];

		for(int i = 0; i < PrimitiveByteArray.length; i++) {
			PrimitiveByteArray[i] = ByteArray[i];
		}

		return PrimitiveByteArray;
	}

	public static BigInteger convertBaseNToNumber(String Number, char[] n) {
		BigInteger[] DigitValues = new BigInteger[Number.length()];
		BigInteger nLength = MathUtils.intToBigInt(n.length);
		BigInteger Output = MathUtils.intToBigInt(0);
		String nString = new String(n);

		for(int i = Number.length() - 1; i >= 0; i--) {
			if(i == Number.length() - 1) {
				DigitValues[i] = MathUtils.intToBigInt(1);
				continue;
			}

			DigitValues[i] = DigitValues[i + 1].multiply(nLength);
		}

		for(int i = Number.length() - 1; i >= 0; i--) {
			char Character = Number.charAt(i);
			int Index = nString.indexOf(Character);

			if(Index == -1) {
				throw new IllegalArgumentException("Invalid character '" + Character + "'.");
			}

			Output = Output.add(DigitValues[i].multiply(MathUtils.intToBigInt(Index)));
		}

		return Output;
	}

	public static BigInteger convertByteArrayToNumber(byte[] ByteArray, byte[] ByteValues) {
		BigInteger[] DigitValues = new BigInteger[ByteArray.length];
		BigInteger nLength = MathUtils.intToBigInt(ByteValues.length);
		BigInteger Output = MathUtils.intToBigInt(0);

		for(int i = ByteArray.length - 1; i >= 0; i--) {
			if(i == ByteArray.length - 1) {
				DigitValues[i] = MathUtils.intToBigInt(1);
				continue;
			}

			DigitValues[i] = DigitValues[i + 1].multiply(nLength);
		}

		for(int i = ByteArray.length - 1; i >= 0; i--) {
			byte Byte = ByteArray[i];
			int Index = IntStream.range(0, ByteValues.length).filter(ti -> ByteValues[ti] == Byte).findFirst().orElse(-1);

			if(Index == -1) {
				throw new IllegalArgumentException("Invalid byte value '" + Byte + "'.");
			}

			Output = Output.add(DigitValues[i].multiply(MathUtils.intToBigInt(Index)));
		}

		return Output;
	}

	public static Container getParent(Component Component) {
		Container BeforeParent = null;
		Container Parent = null;

		BeforeParent = Parent;
		Parent = Component.getParent();

		while(Parent != null) {
			BeforeParent = Parent;
			Parent = Parent.getParent();
		}

		return BeforeParent;
	}
}
