package com.mg.util.reflexion;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;
//import org.apache.commons.collections15.EnumerationUtils;


public class ClassFinder {
	
	private final static Logger log = LogManager.getLogger( ClassFinder.class );
	
	public static List<Class<?>> getClasses( String packageName ) {
		List<Class<?>> clazzes = new ArrayList<Class<?>>();
		
		for( String className : getClassNames( packageName ) ) {
			Class<?> c = getClassSafe( className );
			if( c == null ) {
				continue;
			}
			clazzes.add( c );
		}
		
		return clazzes;
	}
	
	private static Class<?> getClassSafe( String className ) {
		try {
			return Class.forName( className );
		} catch( ClassNotFoundException e ) {
			if( log.isTraceEnabled() ) {
				log.error( "Unable to instantiate this class: " + className );
			}
			return null;
		} catch( NoClassDefFoundError e ) {
			if( log.isTraceEnabled() ) {
				log.error( "Unable to instantiate this class: " + className );
			}
			return null;
		}
	}

	// enumerate classes without loading them
	public static List<String> getClassNames( String packageName ) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			String path = packageName.replace( '.', '/' );
			List<String> classNames = new ArrayList<String>();

			Enumeration<URL> resources = classLoader.getResources( path );
			while( resources.hasMoreElements() ) {
				URL resource = resources.nextElement();
				if( resource.getProtocol().equals( "jar" ) ) {
					JarURLConnection j = (JarURLConnection)resource.openConnection();
					//findClassNames( classNames, j.getJarFile(), path );
				} else {
					findClassNames( classNames, new File( resource.getFile() ), path );
				}
			}
			return classNames;
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}
	}
	
	/*private static void findClassNames( List<String> listClassNames, JarFile jar,
			String basePackage ) {

		for( Object j : EnumerationUtils.toList( jar.entries() ) ) {

			JarEntry je = (JarEntry)j;

			if( je.getName().startsWith( basePackage )
					&& je.getName().endsWith( ".class" ) && !je.getName().contains( "$" ) ) {
				String className = je.getName();
				className = className.substring( 0, className.length() - 6 );
				className = className.replace( '/', '.' );
				if( log.isTraceEnabled() ) {
					log.trace( "JAR - " + jar.getName() + " - found class file: " + className );
				}
				listClassNames.add( className );
			}
		}
	}*/

	private static void findClassNames( List<String> listClassNames, File directory,
			String basePackage ) {

		if( !directory.exists() ) {
			throw new RuntimeException( "directory not found: " + directory.getName() );
		}

		for( File file : directory.listFiles() ) {

			String name = file.getName();

			if( file.isDirectory() ) {
				findClassNames( listClassNames, file, basePackage + "/" + name );
				continue;
			}

			if( name.endsWith( ".class" ) && !name.contains( "$" ) ) {
				String className = basePackage + "/" + name.substring( 0, name.length() - 6 );
				className = className.replace( '/', '.' );
				if( log.isTraceEnabled() ) {
					log.trace( "DIRECTORY - found class file: " + className );
				}
				listClassNames.add( className );
			}
		}
	}
}
