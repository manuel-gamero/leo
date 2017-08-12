package com.mg.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( { METHOD } )
@Retention( RUNTIME )
public @interface Action {

	/**
	 * The method's description as an el-language string. Should include enough
	 * information about its parameters to be useful in the audit log.
	 */
	String value() default "";

	/**
	 * By default, an action method should declare a restriction (
	 * &#064;Restrict, &#064;PermissionCheck, &#064;RoleCheck, ... ) . When this
	 * flag is set to false, the developer acknowledges that the action does not
	 * require any special privilege.
	 */
	boolean restricted() default true;

	/**
	 * By default, an action method call will generate a detailed audit log
	 * entry. Should be set to <code>false</code> when the action does not
	 * require auditing.
	 */
	boolean audited() default true;
}
