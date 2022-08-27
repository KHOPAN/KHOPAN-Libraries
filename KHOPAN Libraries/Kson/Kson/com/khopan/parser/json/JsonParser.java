package com.khopan.parser.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;
import java.util.regex.Pattern;

public class JsonParser {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String NEW_LINE = "\n";
	private static final String TAB = "\t";
	private static final String SPACE = " ";
	private static final String DOUBLE_QUOTE = "\"";
	private static final String OPEN_CURLY_BRACE = "{";
	private static final String CLOSE_CURLY_BRACE = "}";
	private static final String OPEN_SQUARE_BRACKET = "[";
	private static final String CLOSE_SQUARE_BRACKET = "]";
	private static final String BACKSLASH = "\\";
	private static final String COMMA = ",";
	private static final String COLON = ":";
	private static final String EMPTY = "";
	private static final String NULL = "null";

	private static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

	protected static final JsonException BEGIN_CURLY_BRACE_EXCEPTION = new JsonException("Json Object must begin with '{'.");
	protected static final JsonException END_CURLY_BRACE_EXCEPTION = new JsonException("Json Object must end with '}'.");

	protected static final JsonException KEY_DOES_NOT_EXIST = new JsonException("Key does not exist.");

	protected static final String BOOLEAN_TRUE = "true";
	protected static final String BOOLEAN_FALSE = "false";

	protected static final String JSON_NULL = "null";

	private JsonObject Object;

	private JsonParser(String Json) {
		String NoWhitespaceJson = this.clean(Json);

		if(!NoWhitespaceJson.startsWith(OPEN_CURLY_BRACE)) {
			throw BEGIN_CURLY_BRACE_EXCEPTION;
		}

		if(!NoWhitespaceJson.endsWith(CLOSE_CURLY_BRACE)) {
			throw BEGIN_CURLY_BRACE_EXCEPTION;
		}

		this.Object = new JsonObject(NoWhitespaceJson);
	}

	private String clean(String Json) {
		String Temp = "";
		boolean InsideString = false;

		for(int i = 0; i < Json.length(); i++) {
			String Char = Character.toString(Json.charAt(i));

			if(JsonParser.isEquals(Char, DOUBLE_QUOTE)) {
				if(i >= 1) {
					if(!Character.toString(Json.charAt(i - 1)).equals(BACKSLASH)) {
						InsideString = !InsideString;
					}
				}
			}

			if(JsonParser.isEquals(Char, LINE_SEPARATOR, NEW_LINE, TAB, SPACE) && !InsideString) {
				continue;
			}

			Temp += Char;
		}

		return Temp;
	}

	protected static boolean isEquals(String Text, String... CheckingTexts) {
		for(String CheckingText : CheckingTexts) {
			if(CheckingText.equals(Text)) {
				return true;
			}
		}

		return false;
	}

	private JsonObject get() {
		return this.Object;
	}

	protected static boolean checkType(Object Type) {
		return (
				Type instanceof Boolean ||
				Type instanceof Integer ||
				Type instanceof Long ||
				Type instanceof Double ||
				Type instanceof String ||
				Type instanceof JsonObject ||
				Type instanceof JsonArray
				);
	}

	protected static boolean isNull(Object Object) {
		return Object == null || Object instanceof JsonNull;
	}

	protected static String safeToString(Object Object) {
		return Object == null ? NULL : Object.toString();
	}

	protected static Object convertValue(String Value) {
		if(JsonParser.isNull(Value)) {
			return new JsonNull();
		}

		if(JsonParser.isEquals(Value, BOOLEAN_TRUE, BOOLEAN_FALSE)) {
			return Value.equals(BOOLEAN_TRUE) ? true : false;
		}

		if(Value.startsWith(OPEN_CURLY_BRACE) && Value.endsWith(CLOSE_CURLY_BRACE)) {
			return new JsonObject(Value);
		}

		if(Value.startsWith(OPEN_SQUARE_BRACKET) && Value.endsWith(CLOSE_SQUARE_BRACKET)) {
			return new JsonArray(Value);
		}

		if(Value.startsWith(DOUBLE_QUOTE) && Value.endsWith(DOUBLE_QUOTE)) {
			return Value.substring(1, Value.length() - 1);
		}

		if(Value.matches(NUMBER_PATTERN.pattern())) {
			double Number = Double.parseDouble(Value);

			if(Number == (long) Number) {
				return (long) Number;
			} else {
				return Number;
			}
		}

		return null;
	}

	public static JsonObject parseJson(String Json) {
		return new JsonParser(Objects.requireNonNull(Json)).get();
	}

	public static JsonObject parseJson(File File) {
		String Text = EMPTY;

		try {
			BufferedReader Reader = new BufferedReader(new FileReader(File));

			try {
				String Line = EMPTY;

				while((Line = Reader.readLine()) != null) {
					Text += Line + NEW_LINE;
				}
			} finally {
				Reader.close();
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}

		return JsonParser.parseJson(Text);
	}

	public static String formatJson(String Json) {
		return new JsonParser(Objects.requireNonNull(Json)).get().toString();
	}

	public static void formatJson(File Json) {
		BufferedReader Reader = null;
		BufferedWriter Writer = null;

		try {
			Reader = new BufferedReader(new FileReader(Json));
			String Line = "";
			String Input = "";

			while((Line = Reader.readLine()) != null) {
				Input += Line + "\n";
			}

			Writer = new BufferedWriter(new FileWriter(Json));
			Writer.write(JsonParser.formatJson(Input));
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		} finally {
			try {
				Reader.close();
				Writer.close();
			} catch(Throwable Errors) {
				Errors.printStackTrace();
			}
		}
	}

	public static JsonObject emptyJsonObject() {
		return new JsonObject("{}");
	}

	public static JsonArray emptyJsonArray() {
		return new JsonArray("[]");
	}
}
