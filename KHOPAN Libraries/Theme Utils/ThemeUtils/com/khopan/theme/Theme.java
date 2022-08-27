package com.khopan.theme;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public final class Theme {
	private Themes Type;
	private ArrayList<ThemePair<?>> CustomThemeArguments;

	public Theme(Themes Type, ThemePair<?>... CustomThemeArguments) {
		this.Type = Utilities.requireNonNull(Type, ThemeLocal.THEME_TYPE_NULL_EXCEPTION);
		this.updateTheme();

		if(Themes.CUSTOM_THEME.equals(this.Type)) {
			this.list(this.CustomThemeArguments, CustomThemeArguments);
		}
	}

	public Themes getType() {
		return this.Type;
	}

	public void setType(Themes Type) {
		this.Type = Type;
		this.updateTheme();
	}

	private void updateTheme() {
		if(this.CustomThemeArguments == null) {
			this.CustomThemeArguments = new ArrayList<>();
		}

		if(!Themes.CUSTOM_THEME.equals(this.Type)) {
			this.CustomThemeArguments = new ArrayList<>();

			for(ThemePair<?> Pair : Theme.getTheme(ThemeFactory.class, this.Type).CustomThemeArguments) {
				this.CustomThemeArguments.add(Pair);
			}
		}
	}

	public ThemePair<?>[] getArguments() {
		return this.CustomThemeArguments.toArray(new ThemePair<?>[this.CustomThemeArguments.size()]);
	}

	public void setArguments(ThemePair<?>... CustomThemeArguments) {
		this.list(this.CustomThemeArguments, CustomThemeArguments);
	}

	public void addArguments(ThemePair<?>... CustomThemeArguments) {
		for(ThemePair<?> Argument : CustomThemeArguments) {
			this.CustomThemeArguments.add(Argument);
		}
	}

	public void removeArguments(ThemePair<?>... CustomThemeArguments) {
		for(ThemePair<?> Argument : CustomThemeArguments) {
			this.CustomThemeArguments.remove(Argument);
		}
	}

	public Object get(String Key) {
		for(ThemePair<?> Argument : this.CustomThemeArguments) {
			if(Argument.getKey().equals(Key)) {
				return Argument.getValue();
			}
		}

		throw new IllegalArgumentException("Theme.CustomThemeArguments does not have '" + Key + "' key.");
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String Key, Class<T> Type) {
		Object Value = this.get(Key);

		if(Value.getClass().getName().equals(Type.getName())) {
			return (T) Value;
		}

		return null;
	}

	public <T> T get(String Key, T Default, Class<T> Type) {
		try {
			T Object = this.get(Key, Type);
			return Object;
		} catch(IllegalArgumentException Exception) {
			return Default;
		}
	}

	public boolean has(String Key) {
		try {
			this.get(Key);
			return true;
		} catch(IllegalArgumentException Exception) {
			return false;
		}
	}

	public <T> boolean has(String Key, Class<T> Type) {
		try {
			this.get(Key, Type);
			return true;
		} catch(IllegalArgumentException Exception) {
			return false;
		}
	}

	public boolean have(String... Keys) {
		for(String Key : Keys) {
			if(!this.has(Key)) {
				return false;
			}
		}

		return true;
	}

	public <T> boolean have(Class<T> Type, String... Keys) {
		for(String Key : Keys) {
			if(!this.has(Key, Type)) {
				return false;
			}
		}

		return true;
	}

	@SafeVarargs
	private <T> void list(ArrayList<T> Array, T... Objects) {
		for(T Object : Objects) {
			Array.add(Object);
		}
	}

	@SuppressWarnings("deprecation")
	public static Theme getTheme(Class<?> ThemeFactory, Themes Type) {
		if(Themes.CUSTOM_THEME.equals(Type)) {
			throw ThemeLocal.CUSTOM_THEME_NOT_AVAILABLE_EXCEPTION;
		}

		try {
			if(ThemeFactory.getAnnotation(FactoryTheme.class) != null) {
				for(Field Field : ThemeFactory.getDeclaredFields()) {
					if(!Field.isAccessible()) {
						Field.setAccessible(true);
					}

					if(Theme.isStaticFinal(Field.getModifiers())) {
						RegisterTheme Register = Field.getAnnotation(RegisterTheme.class);

						if(Register != null && Register.value().equals(Type)) {
							Object Value = Field.get(null);

							if(Value instanceof Theme Theme) {
								return Theme;
							}
						}
					}
				}
			}
		} catch(Throwable Errors) {
			Errors.printStackTrace();
		}

		return null;
	}

	public static boolean matchType(Theme Theme, Themes Type) {
		return Theme.Type.equals(Type);
	}

	private static boolean isStaticFinal(int Modifiers) {
		return Modifier.isStatic(Modifiers) && Modifier.isFinal(Modifiers);
	}
}
