package com.khopan.parser.json;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.khopan.parser.json.interpreter.JsonInterpreter;

public class JsonObject {
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

	protected static final String BOOLEAN_TYPE = "Boolean";
	protected static final String INTEGER_TYPE = "Integer";
	protected static final String LONG_TYPE = "Long";
	protected static final String DOUBLE_TYPE = "Double";
	protected static final String STRING_TYPE = "String";
	protected static final String JSON_OBJECT_TYPE = "Json Object";
	protected static final String JSON_ARRAY_TYPE = "Json Array";

	protected JsonMap Map;

	protected JsonObject(String Json) {
		Map = new JsonMap();

		String JsonText = Json.substring(1, Json.length() - 1);
		ArrayList<String> Pairs = new ArrayList<>();

		int TempIndex = 0;
		int BraceDepth = 0;
		int BracketDepth = 0;
		boolean InsideString = false;

		for(int i = 1; i < JsonText.length(); i++) {
			String Char = Character.toString(JsonText.charAt(i));

			if(Char.equals(OPEN_CURLY_BRACE)) {
				BraceDepth++;
			} else if(Char.equals(CLOSE_CURLY_BRACE)) {
				BraceDepth--;
			} else if(Char.equals(OPEN_SQUARE_BRACKET)) {
				BracketDepth++;
			} else if(Char.equals(CLOSE_SQUARE_BRACKET)) {
				BracketDepth--;
			}

			if(JsonParser.isEquals(Char, DOUBLE_QUOTE)) {
				if(!Character.toString(JsonText.charAt(i - 1)).equals(BACKSLASH)) {
					InsideString = !InsideString;
				}
			}

			if(Char.equals(COMMA) && BraceDepth == 0 && BracketDepth == 0 && InsideString) {
				Pairs.add(JsonText.substring(TempIndex, i));

				TempIndex = i + 1;
			}

			if(i == JsonText.length() - 1) {
				Pairs.add(JsonText.substring(TempIndex));
			}
		}

		for(String Pair : Pairs) {
			int i = this.indexOfColon(Pair);
			String Key = Pair.substring(1, i - 1);
			Object Value = JsonParser.convertValue(Pair.substring(i + 1));

			if(Map.has(Key)) {
				Map.set(Key, Value);
			} else {
				Map.put(Key, Value);
			}
		}
	}

	private int indexOfColon(String Json) {
		if(!Json.contains(COLON)) {
			throw new JsonException("No colon was found.");
		}

		boolean InsideString = false;

		for(int i = 0; i < Json.length(); i++) {
			String Char = Character.toString(Json.charAt(i));

			if(Char.equals(DOUBLE_QUOTE)) {
				if(i != 0) {
					if(!Character.toString(Json.charAt(i - 1)).equals(BACKSLASH)) {
						InsideString = !InsideString;
					}
				} else {
					InsideString = !InsideString;
				}
			}

			if(!InsideString) {
				if(Char.equals(COLON)) {
					return i;
				}
			}
		}

		return 0;
	}

	private String remove(String Text, int BeginIndex, int EndIndex) {
		return Text.substring(0, BeginIndex) + Text.substring(EndIndex, Text.length());
	}

	public Object get(String Key) {
		return Map.get(Key);
	}

	public boolean getBoolean(String Key) {
		Object Object = this.get(Key);

		if(Object == null) {
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
					throw JsonObject.objectIsNot(BOOLEAN_TYPE);
				}
			} else if(Object instanceof Integer Boolean) {
				if(Boolean == 1) {
					return true;
				} else if(Boolean == 0) {
					return false;
				} else {
					throw JsonObject.objectIsNot(BOOLEAN_TYPE);
				}
			} else {
				throw JsonObject.objectIsNot(BOOLEAN_TYPE);
			}
		}
	}

	public int getInteger(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof Long Number) {
			return (int) (long) Number;
		} else if(Object instanceof String Number) {
			return Integer.parseInt(Number);
		} else {
			throw JsonObject.objectIsNot(DOUBLE_TYPE);
		}
	}

	public long getLong(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof Long Number) {
			return Number;
		} else if(Object instanceof String Number) {
			return Long.parseLong(Number);
		} else {
			throw JsonObject.objectIsNot(DOUBLE_TYPE);
		}
	}

	public double getDouble(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof Double Number) {
			return Number;
		} else if(Object instanceof String Number) {
			return Double.parseDouble(Number);
		} else {
			throw JsonObject.objectIsNot(DOUBLE_TYPE);
		}
	}

	public String getString(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof String String) {
			return String;
		} else {
			throw JsonObject.objectIsNot(STRING_TYPE);
		}
	}

	public JsonObject getJsonObject(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof JsonObject JsonObject) {
			return JsonObject;
		} else {
			throw JsonObject.objectIsNot(JSON_OBJECT_TYPE);
		}
	}

	public JsonArray getJsonArray(String Key) {
		Object Object = this.get(Key);

		if(Object instanceof JsonArray JsonArray) {
			return JsonArray;
		} else {
			throw JsonObject.objectIsNot(JSON_ARRAY_TYPE);
		}
	}

	public JsonObject put(String Key, Object Value) {
		if(JsonParser.checkType(Value)) {
			if(Map.has(Key)) {
				if(Value == null) {
					Map.set(Key, new JsonNull());
				} else {
					Map.set(Key, Value);
				}
			} else {
				if(Value == null) {
					Map.put(Key, new JsonNull());
				} else {
					Map.put(Key, Value);
				}
			}
		} else {
			throw new JsonException("Unsupported object type '" + Value.getClass().getName() + "'.");
		}

		return this;
	}

	public JsonObject putBoolean(String Key, boolean Value) {
		return this.put(Key, Value);
	}

	public JsonObject putInteger(String Key, int Value) {
		return this.put(Key, Value);
	}

	public JsonObject putLong(String Key, long Value) {
		return this.put(Key, Value);
	}

	public JsonObject putDouble(String Key, double Value) {
		return this.put(Key, Value);
	}

	public JsonObject putString(String Key, String Value) {
		return this.put(Key, Value);
	}

	public JsonObject putJsonObject(String Key, JsonObject Value) {
		return this.put(Key, Value);
	}

	public JsonObject putJsonArray(String Key, JsonArray Value) {
		return this.put(Key, Value);
	}

	public JsonObject putNull(String Key) {
		return this.put(Key, null);
	}

	public boolean has(String Key) {
		return Map.has(Key);
	}

	public ArrayList<String> keyList() {
		return Map.keyList();
	}

	public ArrayList<Object> valueList() {
		return Map.valueList();
	}

	public int size() {
		return Map.size();
	}

	public JsonInterpreter asInterpreter() {
		return new JsonInterpreter(this);
	}

	@Override
	public String toString() {
		return this.toString(1);
	}

	protected String toString(int TabCount) {
		if(Map.size() == 1) {
			String Key = this.keyList().get(0);
			Object Value = this.Map.get(Key);

			if(!(Value instanceof JsonObject) && !(Value instanceof JsonArray)) {
				String ValueText = EMPTY;

				if(Value instanceof String) {
					ValueText = "\"" + Value.toString() + "\"";
				} else {
					ValueText = JsonParser.safeToString(Value);
				}

				return "{\"" + Key + "\": " + ValueText + "}";
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

		for(String Key : Map.keyList()) {
			String ValueText = EMPTY;
			Object Value = Map.get(Key);
			String Comma = Map.hasNext(Key) ? COMMA : EMPTY;

			if(Value instanceof String String) {
				ValueText = DOUBLE_QUOTE + String + DOUBLE_QUOTE + Comma;
			} else if(Value instanceof JsonObject Object) {
				String ObjectText = Object.toString(TabCount + 1);
				String LineSeparator = System.getProperty("line.separator");

				if(ObjectText.endsWith("\n") || ObjectText.endsWith(LineSeparator)) {
					ObjectText = ObjectText.substring(0, ObjectText.length() - 1) + Comma + "\n";
				} else {
					ObjectText = Object.toString(0) + Comma;
				}

				ValueText = ObjectText;
			} else if(Value instanceof JsonArray Array) {
				String ArrayString = Array.toString(TabCount + 1);

				ValueText = ArrayString + Comma + (Map.hasNext(Key) ? (this.hasNoLine(ArrayString) ? EMPTY : NEW_LINE) : "");
			} else {
				if(Value != null) {
					ValueText = Value.toString() + Comma;
				} else {
					ValueText = "null" + Comma;
				}
			}

			Content += Tab + DOUBLE_QUOTE + Key + DOUBLE_QUOTE + ": " + ValueText + "\n";
		}

		String Return = "{\n" + Content + MinTab + "}";

		if(!this.hasSingleLine(Return)) {
			Return += "\n";
		}

		return Return;
	}

	private boolean hasSingleLine(String Text) {
		return this.lineCount(Text) == 1;
	}

	private boolean hasNoLine(String Text) {
		return this.lineCount(Text) == 0;
	}

	private int lineCount(String Text) {
		String LineSeparator = System.getProperty("line.separator");
		int Count = 0;

		for(int i = 0; i < Text.length(); i++) {
			String Char = Character.toString(Text.charAt(i));

			if(Char.equals("\n") || Char.equals(LineSeparator)) {
				Count++;
			}
		}

		return Count;
	}

	protected static JsonException objectIsNot(String Type) {
		return new JsonException("Object is not a " + Type + ".");
	}
}
