package com.khopan.theme;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Theme {
	@FactoryField
	public static final Class<ThemeFactory> FACTORY = ThemeFactory.class;

	protected Color Foreground;
	protected Color Background;
	protected Color Border;

	protected Color EnteredForeground;
	protected Color EnteredBackground;
	protected Color EnteredBorder;

	protected Color PressedForeground;
	protected Color PressedBackground;
	protected Color PressedBorder;

	protected Color DisabledForeground;
	protected Color DisabledBackground;
	protected Color DisabledBorder;

	public Theme() {
		this(
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000),
				new Color(0x000000)
				);
	}

	public Theme(
			Color Foreground,
			Color Background,
			Color Border,
			Color EnteredForeground,
			Color EnteredBackground,
			Color EnteredBorder,
			Color PressedForeground,
			Color PressedBackground,
			Color PressedBorder,
			Color DisabledForeground,
			Color DisabledBackground,
			Color DisabledBorder
			) {
		this.Foreground = Foreground;
		this.Background = Background;
		this.Border = Border;
		this.EnteredForeground = EnteredForeground;
		this.EnteredBackground = EnteredBackground;
		this.EnteredBorder = EnteredBorder;
		this.PressedForeground = PressedForeground;
		this.PressedBackground = PressedBackground;
		this.PressedBorder = PressedBorder;
		this.DisabledForeground = DisabledForeground;
		this.DisabledBackground = DisabledBackground;
		this.DisabledBorder = DisabledBorder;
	}

	public Theme(Theme Theme) {
		this.Foreground = Theme.Foreground;
		this.Background = Theme.Background;
		this.Border = Theme.Border;
		this.EnteredForeground = Theme.EnteredForeground;
		this.EnteredBackground = Theme.EnteredBackground;
		this.EnteredBorder = Theme.EnteredBorder;
		this.PressedForeground = Theme.PressedForeground;
		this.PressedBackground = Theme.PressedBackground;
		this.PressedBorder = Theme.PressedBorder;
		this.DisabledForeground = Theme.DisabledForeground;
		this.DisabledBackground = Theme.DisabledBackground;
		this.DisabledBorder = Theme.DisabledBorder;
	}

	public Color getForeground() {
		return this.Foreground;
	}

	public Color getBackground() {
		return this.Background;
	}

	public Color getBorder() {
		return this.Border;
	}

	public ColorState getColor() {
		return new ColorState(this.Foreground, this.Background, this.Border);
	}

	public Color getEnteredForeground() {
		return this.EnteredForeground;
	}

	public Color getEnteredBackground() {
		return this.EnteredBackground;
	}

	public Color getEnteredBorder() {
		return this.EnteredBorder;
	}

	public ColorState getEnteredColor() {
		return new ColorState(this.EnteredForeground, this.EnteredBackground, this.EnteredBorder);
	}

	public Color getPressedForeground() {
		return this.PressedForeground;
	}

	public Color getPressedBackground() {
		return this.PressedBackground;
	}

	public Color getPressedBorder() {
		return this.PressedBorder;
	}

	public ColorState getPressedColor() {
		return new ColorState(this.PressedForeground, this.PressedBackground, this.PressedBorder);
	}

	public Color getDisabledForeground() {
		return this.DisabledForeground;
	}

	public Color getDisabledBackground() {
		return this.DisabledBackground;
	}

	public Color getDisabledBorder() {
		return this.DisabledBorder;
	}

	public ColorState getDisabledColor() {
		return new ColorState(this.DisabledForeground, this.DisabledBackground, this.DisabledBorder);
	}

	public void setForeground(Color Foreground) {
		this.Foreground = Foreground;
	}

	public void setBackground(Color Background) {
		this.Background = Background;
	}

	public void setBorder(Color Border) {
		this.Border = Border;
	}

	public void setColor(ColorState Color) {
		this.Foreground = Color.getForeground();
		this.Background = Color.getBackground();
		this.Border = Color.getBorder();
	}

	public void setEnteredForeground(Color EnteredForeground) {
		this.EnteredForeground = EnteredForeground;
	}

	public void setEnteredBackground(Color EnteredBackground) {
		this.EnteredBackground = EnteredBackground;
	}

	public void setEnteredBorder(Color EnteredBorder) {
		this.EnteredBorder = EnteredBorder;
	}

	public void setEnteredColor(ColorState Color) {
		this.EnteredForeground = Color.getForeground();
		this.EnteredBackground = Color.getBackground();
		this.EnteredBorder = Color.getBorder();
	}

	public void setPressedForeground(Color PressedForeground) {
		this.PressedForeground = PressedForeground;
	}

	public void setPressedBackground(Color PressedBackground) {
		this.PressedBackground = PressedBackground;
	}

	public void setPressedBorder(Color PressedBorder) {
		this.PressedBorder = PressedBorder;
	}

	public void setPressedColor(ColorState Color) {
		this.PressedForeground = Color.getForeground();
		this.PressedBackground = Color.getBackground();
		this.PressedBorder = Color.getBorder();
	}

	public void setDisabledForeground(Color DisabledForeground) {
		this.DisabledForeground = DisabledForeground;
	}

	public void setDisabledBackground(Color DisabledBackground) {
		this.DisabledBackground = DisabledBackground;
	}

	public void setDisabledBorder(Color DisabledBorder) {
		this.DisabledBorder = DisabledBorder;
	}

	public void setDisabledColor(ColorState Color) {
		this.DisabledForeground = Color.getForeground();
		this.DisabledBackground = Color.getBackground();
		this.DisabledBorder = Color.getBorder();
	}

	public FieldName[] toFieldNames() {
		return new FieldName[] {
				new FieldName("Foreground", this.Foreground),
				new FieldName("Background", this.Background),
				new FieldName("Border", this.Border),
				new FieldName("EnteredForeground", this.EnteredForeground),
				new FieldName("EnteredBackground", this.EnteredBackground),
				new FieldName("EnteredBorder", this.EnteredBorder),
				new FieldName("PressedForeground", this.PressedForeground),
				new FieldName("PressedBackground", this.PressedBackground),
				new FieldName("PressedBorder", this.PressedBorder),
				new FieldName("DisabledForeground", this.DisabledForeground),
				new FieldName("DisabledBackground", this.DisabledBackground),
				new FieldName("DisabledBorder", this.DisabledBorder)
		};
	}

	public <Type extends Theme> Type copy(Class<Type> DestinationClass) {
		return Theme.copy(DestinationClass, this);
	}

	@SuppressWarnings("unchecked")
	public static <Type extends Theme> Type getTheme(Class<Type> Class, ThemeType Type, FieldName... CustomThemeArgs) {
		if(Type.equals(ThemeType.CUSTOM_THEME)) {
			if(!Theme.hasEmptyConstructor(Class)) {
				throw new RuntimeException("The class must have an empty constructor.");
			}

			try {
				Type Instance = Class.getDeclaredConstructor().newInstance();

				for(Field Field : Class.getDeclaredFields()) {
					for(FieldName Name : CustomThemeArgs) {
						if(Field.getName().equals(Name.FieldName)) {
							Field.setAccessible(true);
							Field.set(Instance, Name.FieldValue);
							break;
						}
					}
				}

				return Instance;
			} catch(Throwable Errors) {
				Errors.printStackTrace();
			}
		} else {
			for(Field Field : Class.getDeclaredFields()) {
				if(Field.getAnnotation(FactoryField.class) != null) {
					Object Value = null;

					try {
						Value = Field.get(null);
					} catch(IllegalAccessException Exception) {
						return null;
					}

					if(Value instanceof Class<?> FactoryClass) {
						if(FactoryClass.getAnnotation(Factory.class) != null) {
							for(Field FactoryField : FactoryClass.getDeclaredFields()) {
								if(FactoryField.getName().equals(Type.name())) {
									try {
										return (Type) FactoryField.get(null);
									} catch(IllegalAccessException Exception) {
										return null;
									}
								}
							}
						}
					}
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <Type extends Theme> Type copy(Class<Type> DestinationClass, Theme SourceTheme) {
		if(!Theme.hasEmptyConstructor(DestinationClass)) {
			throw new RuntimeException("The class must have an empty constructor.");
		}

		try {
			Class<?> SourceClass = SourceTheme.getClass();
			Field[] SourceFields = Theme.getAllField(SourceClass);
			Field[] DestinationFields = Theme.getAllField(DestinationClass);
			Object Instance = DestinationClass.getDeclaredConstructor().newInstance();

			for(Field DestinationField : DestinationFields) {
				if(Theme.allow(DestinationField)) {
					DestinationField.setAccessible(true);

					for(Field SourceField : SourceFields) {
						SourceField.setAccessible(true);

						if(DestinationField.getName().equals(SourceField.getName())) {
							DestinationField.set(Instance, SourceField.get(SourceTheme));
						}
					}
				}
			}

			return (Type) Instance;
		} catch(Throwable Errors) {
			Errors.printStackTrace();
			System.exit(1);
		}

		return null;
	}

	private static boolean allow(Field Field) {
		return !Modifier.isStatic(Field.getModifiers()) && !Modifier.isFinal(Field.getModifiers());
	}

	private static Field[] getAllField(Class<?> Class) {
		if(Class == null) {
			return null;
		}

		ArrayList<Field> Fields = new ArrayList<>();

		for(Field Field : Class.getDeclaredFields()) {
			Fields.add(Field);
		}

		Field[] AllField = Theme.getAllField(Class.getSuperclass());

		if(AllField != null) {
			for(Field Field : AllField) {
				Fields.add(Field);
			}
		}

		return Fields.toArray(new Field[Fields.size()]);
	}

	private static Field[] getAllField(Class<?> Class, int Depth) {
		if(Class == null || Depth <= 0) {
			return null;
		}

		ArrayList<Field> Fields = new ArrayList<>();

		for(Field Field : Class.getDeclaredFields()) {
			Fields.add(Field);
		}

		Field[] AllField = Theme.getAllField(Class.getSuperclass(), Depth - 1);

		if(AllField != null) {
			for(Field Field : AllField) {
				Fields.add(Field);
			}
		}

		return Fields.toArray(new Field[Fields.size()]);
	}

	private static boolean hasField(String Name, Class<?> Class) {
		try {
			Class.getField(Name);
			return true;
		} catch(NoSuchFieldException NoField) {
			return false;
		}
	}

	private static boolean hasEmptyConstructor(Class<?> Class) {
		for(Constructor<?> Constructor : Class.getDeclaredConstructors()) {
			if(Constructor.getParameterCount() == 0) {
				return true;
			}
		}

		return false;
	}

	public static boolean match(Theme ThemeA, Theme ThemeB) {
		if(ThemeA == null && ThemeB == null) {
			return true;
		} else if(ThemeA == null || ThemeB == null) {
			return false;
		} else {
			return ThemeA.Foreground.equals(ThemeB.Foreground) &&
					ThemeA.Background.equals(ThemeB.Background) &&
					ThemeA.Border.equals(ThemeB.Border) &&
					ThemeA.EnteredForeground.equals(ThemeB.EnteredForeground) &&
					ThemeA.EnteredBackground.equals(ThemeB.EnteredBackground) &&
					ThemeA.EnteredBorder.equals(ThemeB.EnteredBorder) &&
					ThemeA.PressedForeground.equals(ThemeB.PressedForeground) &&
					ThemeA.PressedBackground.equals(ThemeB.PressedBackground) &&
					ThemeA.PressedBorder.equals(ThemeB.PressedBorder) &&
					ThemeA.DisabledForeground.equals(ThemeB.DisabledForeground) &&
					ThemeA.DisabledBackground.equals(ThemeB.DisabledBackground) &&
					ThemeA.DisabledBorder.equals(ThemeB.DisabledBorder);
		}
	}
}
