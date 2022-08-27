package com.khopan.parser.json;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class JsonArray {
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

	protected JsonList List;

	protected JsonArray(String Json) {
		List = new JsonList();

		String JsonText = Json.substring(1, Json.length() - 1);
		ArrayList<String> Values = new ArrayList<>();

		int BeforeCommaIndex = 0;
		int CurlyBraceDepth = 0;
		int SquareBracketDepth = 0;
		boolean String = false;

		for(int i = 0; i < JsonText.length(); i++) {
			String Char = Character.toString(JsonText.charAt(i));

			if(Char.equals(OPEN_CURLY_BRACE)) {
				CurlyBraceDepth++;
			} else if(Char.equals(CLOSE_CURLY_BRACE)) {
				CurlyBraceDepth--;
			} else if(Char.equals(OPEN_SQUARE_BRACKET)) {
				SquareBracketDepth++;
			} else if(Char.equals(OPEN_SQUARE_BRACKET)) {
				SquareBracketDepth--;
			}

			if(JsonParser.isEquals(Char, DOUBLE_QUOTE)) {
				if(i > 0) {
					if(!Character.toString(JsonText.charAt(i - 1)).equals(BACKSLASH)) {
						String = !String;
					}
				} else {
					String = !String;
				}
			}

			if(Char.equals(COMMA) && CurlyBraceDepth == 0 && SquareBracketDepth == 0 && !String) {
				Values.add(JsonText.substring(BeforeCommaIndex, i));

				BeforeCommaIndex = i + 1;
			}

			if(i == JsonText.length() - 1) {
				Values.add(JsonText.substring(BeforeCommaIndex));
			}
		}

		for(String Value : Values) {
			Object Converted = JsonParser.convertValue(Value);

			List.add(Converted);
		}
	}

	public Object get(int Index) {
		return this.List.get(Index);
	}

	public boolean getBoolean(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return false;
		} else {
			if(Object instanceof Boolean Boolean) {
				return Boolean;
			} else if(Object instanceof String Boolean) {
				if(Boolean.equals(JsonParser.BOOLEAN_TRUE)) {
					return true;
				} else if(Boolean.equals(JsonParser.BOOLEAN_FALSE)) {
					return false;
				} else {
					throw JsonObject.objectIsNot(JsonObject.BOOLEAN_TYPE);
				}
			} else if(Object instanceof Integer Boolean) {
				if(Boolean == 1) {
					return true;
				} else if(Boolean == 0) {
					return false;
				} else {
					throw JsonObject.objectIsNot(JsonObject.BOOLEAN_TYPE);
				}
			} else {
				throw JsonObject.objectIsNot(JsonObject.BOOLEAN_TYPE);
			}
		}
	}

	public int getInteger(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return 0;
		} else {
			if(Object instanceof Long Number) {
				return (int) (long) Number;
			} else if(Object instanceof String Number) {
				return Integer.parseInt(Number);
			} else {
				throw JsonObject.objectIsNot(JsonObject.INTEGER_TYPE);
			}
		}
	}

	public long getLong(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return 0l;
		} else {
			if(Object instanceof Long Number) {
				return Number;
			} else if(Object instanceof String Number) {
				return Long.parseLong(Number);
			} else {
				throw JsonObject.objectIsNot(JsonObject.LONG_TYPE);
			}
		}
	}

	public double getDouble(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return 0.0d;
		} else {
			if(Object instanceof Double Number) {
				return Number;
			} else if(Object instanceof String Number) {
				return Double.parseDouble(Number);
			} else {
				throw JsonObject.objectIsNot(JsonObject.DOUBLE_TYPE);
			}
		}
	}

	public String getString(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return null;
		} else {
			if(Object instanceof String String) {
				return String;
			}

			return null;
		}
	}

	public JsonObject getJsonObject(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return null;
		} else {
			if(Object instanceof JsonObject JsonObject) {
				return JsonObject;
			}

			return null;
		}
	}

	public JsonArray getJsonArray(int Index) {
		Object Object = this.get(Index);

		if(this.isNull(Object)) {
			return null;
		} else {
			if(Object instanceof JsonArray JsonArray) {
				return JsonArray;
			}

			return null;
		}
	}

	public JsonArray add(Object... Values) {
		for(int i = 0; i < Values.length; i++) {
			if(JsonParser.checkType(Values[i])) {
				List.add(Values[i]);
			} else {
				throw new JsonException("Unsupported object type '" + Values[i].getClass().getName() + "' at index " + i + ".");
			}
		}

		return this;
	}

	public JsonArray addBoolean(boolean Value) {
		return this.add(Value);
	}

	public JsonArray addInteger(int Value) {
		return this.add(Value);
	}

	public JsonArray addLong(long Value) {
		return this.add(Value);
	}

	public JsonArray addDouble(double Value) {
		return this.add(Value);
	}

	public JsonArray addString(String Value) {
		return this.add(Value);
	}

	public JsonArray addJsonObject(JsonObject Value) {
		return this.add(Value);
	}

	public JsonArray addJsonArray(JsonArray Value) {
		return this.add(Value);
	}

	@Override
	public String toString() {
		return this.toString(1);
	}

	public int size() {
		return List.size();
	}

	protected String toString(int TabCount) {
		if(List.size() == 1) {
			Object Object = List.get(0);

			if(
					Object instanceof JsonObject ||
					Object instanceof JsonArray
					) {
				return OPEN_SQUARE_BRACKET + JsonParser.safeToString(Object) + CLOSE_SQUARE_BRACKET;
			}

			if(Object instanceof JsonObject JsonObject) {
				if(JsonObject.size() == 1) {
					return OPEN_SQUARE_BRACKET + JsonObject.toString() + CLOSE_SQUARE_BRACKET;
				}
			} else if(Object instanceof JsonArray JsonArray) {
				if(JsonArray.size() == 1) {
					return OPEN_SQUARE_BRACKET + JsonArray.toString() + CLOSE_SQUARE_BRACKET;
				}
			} else if(Object instanceof String) {
				if(this.size() == 1) {
					return OPEN_SQUARE_BRACKET + DOUBLE_QUOTE + JsonParser.safeToString(Object) + DOUBLE_QUOTE + CLOSE_SQUARE_BRACKET;
				}
			} else {
				if(this.size() == 1) {
					return OPEN_SQUARE_BRACKET + JsonParser.safeToString(Object) + CLOSE_SQUARE_BRACKET;
				}
			}
		}

		String Content = EMPTY;
		String Tab = EMPTY;
		String MinTab = EMPTY;

		for(int i = 0; i < TabCount; i++) {
			Tab += TAB;

			if(i > 0) {
				MinTab += TAB;
			}
		}

		if(this.allSame()) {
			String DoubleQuote = EMPTY;

			if(this.first() instanceof String) {
				DoubleQuote = DOUBLE_QUOTE;
			}

			if(List.size() <= 10) {
				for(int i = 0; i < List.size(); i++) {
					Object Object = List.get(i);
					Content += DoubleQuote + JsonParser.safeToString(Object) + DoubleQuote + (i >= List.size() - 1 ? EMPTY : COMMA + SPACE);
				}

				return OPEN_SQUARE_BRACKET + Content + CLOSE_SQUARE_BRACKET;
			} else {
				Content += NEW_LINE + Tab;

				for(int i = 0; i < List.size(); i++) {
					Object Object = List.get(i);
					String CommaSpace = i >= List.size() - 1 ? EMPTY : COMMA + SPACE;
					String CommaNewline = i >= List.size() - 1 ? EMPTY : COMMA + NEW_LINE;
					String Newline = i % 10 == 9 ? CommaNewline + Tab : CommaSpace;

					Content += DoubleQuote + JsonParser.safeToString(Object) + DoubleQuote + Newline;
				}

				return OPEN_SQUARE_BRACKET + Content + NEW_LINE + MinTab + CLOSE_SQUARE_BRACKET;
			}
		} else {
			Content += NEW_LINE + Tab;

			for(int i = 0; i < List.size(); i++) {
				Object Object = List.get(i);
				String CommaNewline = List.hasNext(i) ? COMMA + NEW_LINE : EMPTY;

				Content += JsonParser.safeToString(Object) + CommaNewline + (List.hasNext(i) ? Tab : EMPTY);
			}

			return OPEN_SQUARE_BRACKET + Content + NEW_LINE + MinTab + CLOSE_SQUARE_BRACKET;
		}

		/*for(int i = 0; i < List.size(); i++) {
			Object Object = List.get(i);
			String Comma = List.hasNext(i) ? COMMA : EMPTY;

			if(Object instanceof String String) {
				Content += Tab + DOUBLE_QUOTE + String + DOUBLE_QUOTE + Comma;
			} else if(Object instanceof JsonObject JsonObject) {
				Content += Tab + JsonObject.toString(TabCount + 1);
			} else if(Object instanceof JsonArray JsonArray) {
				Content += Tab + JsonArray.toString(TabCount + 1);
			} else {
				Content += Tab + JsonParser.safeToString(Object) + Comma;
			}

			Printer.out.println("List", i, ":", Object);
		}

		Printer.out.println("Returning of 'JsonArray.toString()' function.");
		return "[\n" + Content + MinTab + "]";*/
	}

	private boolean allSame() {
		if(List.size() == 0) {
			return false;
		} else if(List.size() == 1) {
			return true;
		}

		Object Element = List.get(0);
		Class<?> First = Element == null ? null : Element.getClass();

		for(int i = 1; i < List.size(); i++) {
			Element = List.get(i);

			if(First == null) {
				if(Element != null) {
					return false;
				}
			} else {
				if(Element == null) {
					return false;
				}

				if(!First.equals(Element.getClass())) {
					return false;
				}
			}
		}

		return true;
	}

	private Object first() {
		return List.size() > 1 ? List.get(0) : null;
	}

	private boolean areAll(Class<?> Class) {
		for(int i = 0; i < List.size(); i++) {
			if(!List.get(i).getClass().equals(Class)) {
				return false;
			}
		}

		return true;
	}

	private boolean isNull(Object Object) {
		return Object == null || Object instanceof JsonNull;
	}
}
