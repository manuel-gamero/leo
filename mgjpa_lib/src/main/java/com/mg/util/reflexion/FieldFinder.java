package com.mg.util.reflexion;

import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldFinder {

	public static List<Field> getInheritedPrivateFields( Class<?> type ) {
		List<Field> result = new ArrayList<Field>();

		Class<?> i = type;
		while( i != null && i != Object.class ) {
			result.addAll( Arrays.asList( i.getDeclaredFields() ) );
			i = i.getSuperclass();
		}

		return result;
	}

	public static List<Field> getInheritedPrivateFieldsAnnotated( Class<?> type, Class<? extends Annotation> annotationClass ) {
		List<Field> fields = getInheritedPrivateFields( type );

		List<Field> annotatedFields = new ArrayList<Field>();
		for( Field field : fields ) {
			if( field.isAnnotationPresent( annotationClass ) ) {
				annotatedFields.add( field );
			}
		}

		return annotatedFields;
	}
}
