package com.khopan.parameter;

import com.khopan.list.ItemList;

public class ParameterArray {
	private ItemList<Object> List;

	public ParameterArray(String Array) {
		this.List = new ItemList<>();

		String NoBackslash = Array.replaceAll("\\\\.", "\u0000\u0000");
		int BracketDepth = 0;
		int ParenthesesDepth = 0;
		int BeforeIndex = 0;

		for(int i = 0; i < Array.length(); i++) {
			char Char = NoBackslash.charAt(i);

			if(Char == '[') {
				BracketDepth += 1;
			} else if(Char == ']') {
				BracketDepth -= 1;
			} else if(Char == '(') {
				ParenthesesDepth += 1;
			} else if(Char == ')') {
				ParenthesesDepth -= 1;
			}

			if(BracketDepth == 0 && ParenthesesDepth == 0) {
				if(Char == ',') {
					this.List.add(this.toType(Array.substring(BeforeIndex, i)));
					BeforeIndex = i + 1;
				}

				if(i == Array.length() - 1) {
					this.List.add(this.toType(Array.substring(BeforeIndex)));
				}
			}
		}
	}

	public Object get(int Index) {
		return List.get(Index);
	}

	public boolean getBoolean(int Index) {
		Object Value = List.get(Index);

		if(!(Value instanceof Boolean)) {
			throw Parameter.typeNotMatch(Index, "Boolean");
		}

		return (boolean) Value;
	}

	public long getLong(int Index) {
		return (long) this.getDouble(Index);
	}

	public double getDouble(int Index) {
		Object Value = List.get(Index);

		if(!(Value instanceof Double)) {
			throw Parameter.typeNotMatch(Index, "Double");
		}

		return (double) Value;
	}

	public String getString(int Index) {
		Object Value = List.get(Index);

		if(!(Value instanceof String)) {
			throw Parameter.typeNotMatch(Index, "String");
		}

		return (String) Value;
	}

	public Parameter getParameter(int Index) {
		Object Value = List.get(Index);

		if(!(Value instanceof Parameter)) {
			throw Parameter.typeNotMatch(Index, "Parameter");
		}

		return (Parameter) Value;
	}

	public ParameterArray getParameterArray(int Index) {
		Object Value = List.get(Index);

		if(!(Value instanceof ParameterArray)) {
			throw Parameter.typeNotMatch(Index, "Parameter Array");
		}

		return (ParameterArray) Value;
	}

	public void add(Object Object) {
		List.add(Object);
	}

	public void remove(int Index) {
		List.remove(Index);
	}

	public boolean has(Object Object) {
		return List.has(Object);
	}

	public boolean isBoolean(int Index) {
		return List.get(Index) instanceof Boolean;
	}

	public boolean isLong(int Index) {
		return List.get(Index) instanceof Long;
	}

	public boolean isDouble(int Index) {
		return List.get(Index) instanceof Double;
	}

	public boolean isString(int Index) {
		return List.get(Index) instanceof String;
	}

	public boolean isParameter(int Index) {
		return List.get(Index) instanceof Parameter;
	}

	public boolean isParameterArray(int Index) {
		return List.get(Index) instanceof ParameterArray;
	}

	@Override
	public String toString() {
		return "[" + List.toString() + "]";
	}

	public int size() {
		return this.List.size();
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
}
