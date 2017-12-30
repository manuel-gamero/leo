package com.mg.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface FieldView {

	/**
	 * This method indicates the order for the field in the view list
	 */
	int order() default 0;

	/**
	 * This method indicates if the the user can change the field value
	 */
	boolean modifiable() default false;
}
