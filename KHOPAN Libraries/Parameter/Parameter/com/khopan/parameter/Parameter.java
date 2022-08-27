package com.khopan.parameter;

import com.khopan.list.ItemMap;

public class Parameter {
	private ItemMap<String, Object> List;
	private boolean IsChild;

	public Parameter(String Parameter) {
		this.List = new ItemMap<>();

		Parameter = this.clean(Parameter);
		String NoBackslash = Parameter.replaceAll("\\\\.", "\u0000\u0000");
		int BracketDepth = 0;
		int ParenthesesDepth = 0;
		boolean InsideString = false;
		int BeforeIndex = 0;

		for(int i = 0; i < Parameter.length(); i++) {
			char Char = NoBackslash.charAt(i);

			if(Char == '[') {
				BracketDepth += 1;
			} else if(Char == ']') {
				BracketDepth -= 1;
			} else if(Char == '(') {
				ParenthesesDepth += 1;
			} else if(Char == ')') {
				ParenthesesDepth -= 1;
			} else if(Char == '\'') {
				InsideString = !InsideString;
			}

			if(BracketDepth == 0 && ParenthesesDepth == 0 && !InsideString) {
				if(Char == ',') {
					this.addToList(Parameter.substring(BeforeIndex, i));
					BeforeIndex = i + 1;
				}

				if(i == Parameter.length() - 1) {
					this.addToList(Parameter.substring(BeforeIndex));
				}
			}
		}
	}

	private String clean(String Input) {
		String Text = "";
		boolean InsideString = false;

		for(int i = 0; i < Input.length(); i++) {
			char Char = Input.charAt(i);

			if(Char == '\'') {
				InsideString = !InsideString;
			}

			if(InsideString) {
				Text += Character.toString(Char);
			} else {
				if(!Character.toString(Char).matches("\s")) {
					Text += Character.toString(Char);
				}
			}
		}

		return Text;
	}

	protected Parameter child() {
		this.IsChild = true;
		return this;
	}

	private void addToList(String Element) {
		String NoBackslash = Element.replaceAll("\\\\.", "\u0000\u0000");

		String Key = Element.substring(0, NoBackslash.indexOf('='));
		String Value = Element.substring(NoBackslash.indexOf('=') + 1);

		List.put(Key, this.toType(Value));
	}

	private Object toType(String Object) {
		if(Object == null || "null".equals(Object == null ? null : Object.toLowerCase())) {
			return null;
		}

		if("true".equals(Object.toLowerCase())) {
			return true;
		} else if("false".equals(Object.toLowerCase())) {
			return false;
		}

		if(Object.matches("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?")) {
			return Double.valueOf(Object);
		}

		if(Object.startsWith("(") && Object.endsWith(")")) {
			return new Parameter(Object.substring(1, Object.length() - 1)).child();
		}

		if(Object.startsWith("[") && Object.endsWith("]")) {
			return new ParameterArray(Object.substring(1, Object.length() - 1));
		}

		if(Object.startsWith("'") && Object.endsWith("'")) {
			return (String) Object.substring(1, Object.length() - 1);
		}

		return null;
	}

	public Object get(String Key) {
		return List.get(Key);
	}

	public boolean getBoolean(String Key) {
		Object Value = List.get(Key);

		if(!(Value instanceof Boolean)) {
			throw Parameter.typeNotMatch(Key, "Boolean");
		}

		return (boolean) Value;
	}

	public long getLong(String Key) {
		return (long) this.getDouble(Key);
	}

	public double getDouble(String Key) {
		Object Value = List.get(Key);

		if(!(Value instanceof Double)) {
			throw Parameter.typeNotMatch(Key, "Double");
		}

		return (double) Value;
	}

	public String getString(String Key) {
		Object Value = List.get(Key);

		if(!(Value instanceof String)) {
			throw Parameter.typeNotMatch(Key, "String");
		}

		return (String) Value;
	}

	public Parameter getParameter(String Key) {
		Object Value = List.get(Key);

		if(!(Value instanceof Parameter)) {
			throw Parameter.typeNotMatch(Key, "Parameter");
		}

		return (Parameter) Value;
	}

	public ParameterArray getParameterArray(String Key) {
		Object Value = List.get(Key);

		if(!(Value instanceof ParameterArray)) {
			throw Parameter.typeNotMatch(Key, "Parameter Array");
		}

		return (ParameterArray) Value;
	}

	public void put(String Key, Object Value) {
		List.put(Key, Value);
	}

	public void remove(String Key) {
		List.remove(Key);
	}

	public boolean has(String Key) {
		return List.has(Key);
	}

	public boolean isBoolean(String Key) {
		return List.get(Key) instanceof Boolean;
	}

	public boolean isLong(String Key) {
		return List.get(Key) instanceof Long;
	}
	public boolean isDouble(String Key) {
		return List.get(Key) instanceof Double;
	}

	public boolean isString(String Key) {
		return List.get(Key) instanceof String;
	}

	public boolean isParameter(String Key) {
		return List.get(Key) instanceof Parameter;
	}

	public boolean isParameterArray(String Key) {
		return List.get(Key) instanceof ParameterArray;
	}

	@Override
	public String toString() {
		return (IsChild ? "(" : "") + List.toString() + (IsChild ? ")" : "");
	}

	public static Parameter parse(String Parameter) {
		return new Parameter(Parameter);
	}

	protected static IllegalArgumentException typeNotMatch(Object Key, String Type) {
		if(Key instanceof String) {
			return new IllegalArgumentException("Key '" + Key + "' is not a " + Type.toLowerCase() + ".");
		} else if(Key instanceof Integer) {
			return new IllegalArgumentException("Key at index " + Key + " is not a " + Type.toLowerCase() + ".");
		} else {
			return null;
		}
	}
}
