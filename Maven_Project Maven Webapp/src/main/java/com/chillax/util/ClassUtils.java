package com.chillax.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassUtils {
	private static final String CLASS_EXT = ".class";
	private static final String JAR_FILE_EXT = ".jar";
	private static final String ERROR_MESSAGE = "packageName can't be null";
	private static final String DOT = ".";
	private static final String ZIP_SLASH = "/";
	private static final String BLACK = "";
	private static final ClassFilter NULL_CLASS_FILTER = null;
	private static ClassLoader loader = Thread.currentThread()
			.getContextClassLoader();
	/** (1) 文件过滤器，过滤掉不�?��的文�?**/
	private static FileFilter fileFilter = new FileFilter() {
		public boolean accept(File pathname) {
			return isClass(pathname.getName()) || isDirectory(pathname)
					|| isJarFile(pathname.getName());
		}
	};

	/**
	 * 如果packageName为空，就抛出空指针异常�?</br>
	 * (本工具类有一个bug，如果扫描文件时�?���?��包路径为截取字符串的条件，现在还没有修复,�?��加上该条�?
	 * 
	 * @param packageName
	 */
	private static void ckeckNullPackageName(String packageName) {
		if (packageName == null || packageName.trim().length() == 0)
			throw new NullPointerException(ERROR_MESSAGE);
	}

	/**
	 * 改变 com -> com. 避免在比较的时�?把比�?completeTestSuite.class类扫描进去，如果没有"."
	 * </br>那class里面com�?��的class类也会被扫描进去,其实名称后面或前面需要一�?".",来添加包的特�?
	 * 
	 * @param packageName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getWellFormedPackageName(String packageName) {
		return packageName.lastIndexOf(DOT) != packageName.length() - 1 ? packageName
				+ DOT
				: packageName;
	}

	/**
	 * 扫面包路径下满足class过滤器条件的�?��class文件�?/br> 如果包路径为 com.abs + A.class 但是输入 abs
	 * 会产生classNotFoundException</br> 因为className 应该�?com.abs.A 现在却成�?
	 * abs.A,此工具类对该异常进行忽略处理,有可能是�?��不完善的地方，以后需要进行修�?/br>
	 * 
	 * @param packageName
	 *            包路�?com | com. | com.abs | com.abs.
	 * @param classFilter
	 *            class过滤器，过滤掉不�?��的class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> scanPackage(String packageName,
			ClassFilter classFilter) {
		// �?��packageName 是否为空，如果为空就抛出NullPointException
		ckeckNullPackageName(packageName);
		// 实例化一个篮�?P: 放置class
		@SuppressWarnings("unused")
		final List<Class> classes = new ArrayList<Class>();
		URL url = loader.getResource(packageName.replaceAll("\\.", "/"));
		String filePath = url.getPath();

		if (url != null && "file".equals(url.getProtocol())) {
			return getClassesByFile(url.getPath(), true,classFilter);
		} else {
			System.out.println("没有找到路径 " + filePath);
			return new ArrayList<Class>();
		}

		/*// 遍历在classpath 下面的jar包，class文件�?现在没有包括 java jre)
		for (String classPath : getClassPathArray()) {
			// 填充 classes
			System.out.println("classpath: " + classPath);
			fillClasses(new File(classPath),
					getWellFormedPackageName(packageName), classFilter, classes);
		}
		return classes;*/
	}

	/**
	 * 扫面改包路径下所有class文件
	 * 
	 * @param packageName
	 *            包路�?com | com. | com.abs | com.abs.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> scanPackageByFile(String packageName) {
		return scanPackage(packageName, NULL_CLASS_FILTER);
	}

	/**
	 * 填充满足条件的class 填充�?classes
	 * 
	 * @param file
	 *            类路径下的文�?
	 * @param packageName
	 *            �?��扫面的包�?
	 * @param classFilter
	 *            class过滤�?
	 * @param classes
	 *            List 集合
	 */
	@SuppressWarnings("rawtypes")
	private static void fillClasses(File file, String packageName,
			ClassFilter classFilter, List<Class> classes) {
		if (isDirectory(file)) {
			processDirectory(file, packageName, classFilter, classes);
		} else if (isClass(file.getName())) {
			processClassFile(file, packageName, classFilter, classes);
		} else if (isJarFile(file.getName())) {
			processJarFile(file, packageName, classFilter, classes);
		}
	}

	/**
	 * 处理如果为目录的情况,�?��递归调用 fillClasses方法
	 * 
	 * @param directory
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 */
	private static void processDirectory(File directory, String packageName,
			ClassFilter classFilter, @SuppressWarnings("rawtypes") List<Class> classes) {
		for (File file : directory.listFiles(fileFilter)) {
			fillClasses(file, packageName, classFilter, classes);
		}
	}

	/**
	 * 处理为class文件的情�?填充满足条件的class �?classes
	 * 
	 * @param file
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 */
	@SuppressWarnings("rawtypes")
	private static void processClassFile(File file, String packageName,
			ClassFilter classFilter, List<Class> classes) {
		final String filePathWithDot = file.getAbsolutePath().replace(
				File.separator, DOT);
		int subIndex = -1;
		if ((subIndex = filePathWithDot.indexOf(packageName)) != -1) {
			final String className = filePathWithDot.substring(subIndex)
					.replace(CLASS_EXT, BLACK);
			fillClass(className, packageName, classes, classFilter);
		}
	}

	/**
	 * 处理为jar文件的情况，填充满足条件的class �?classes
	 * 
	 * @param file
	 * @param packageName
	 * @param classFilter
	 * @param classes
	 */
	@SuppressWarnings("rawtypes")
	private static void processJarFile(File file, String packageName,
			ClassFilter classFilter, List<Class> classes) {
		try {
			for (ZipEntry entry : Collections.list(new ZipFile(file).entries())) {
				if (isClass(entry.getName())) {
					final String className = entry.getName().replace(ZIP_SLASH,
							DOT).replace(CLASS_EXT, BLACK);
					fillClass(className, packageName, classes, classFilter);
				}
			}
		} catch (Throwable ex) {
			// ignore this ex
		}
	}

	/**
	 * 填充class �?classes
	 * 
	 * @param className
	 * @param packageName
	 * @param classes
	 * @param classFilter
	 */
	@SuppressWarnings("rawtypes")
	private static void fillClass(String className, String packageName,
			List<Class> classes, ClassFilter classFilter) {
		if (checkClassName(className, packageName)) {
			try {
				final Class clazz = Class.forName(className, Boolean.FALSE,
						ClassUtils.class.getClassLoader());
				if (checkClassFilter(classFilter, clazz)) {
					classes.add(clazz);
				}
			} catch (Throwable ex) {
				// ignore this ex
			}
		}
	}

	@SuppressWarnings("unused")
	private static String[] getClassPathArray() {
		// 不包�?jre
		return System.getProperty("java.class.path").split(
				System.getProperty("path.separator"));
		/*
		 * 包括 jre return System.getProperty("java.class.path").
		 * concat(System.getProperty("path.separator")).
		 * concat(System.getProperty("java.home")).
		 * split(System.getProperty("path.separator"));
		 */
	}

	private static boolean checkClassName(String className, String packageName) {
		return className.indexOf(packageName) == 0;
	}

	@SuppressWarnings("rawtypes")
	private static boolean checkClassFilter(ClassFilter classFilter, Class clazz) {
		return classFilter == NULL_CLASS_FILTER || classFilter.accept(clazz);
	}

	private static boolean isClass(String fileName) {
		return fileName.endsWith(CLASS_EXT);
	}

	private static boolean isDirectory(File file) {
		return file.isDirectory();
	}

	private static boolean isJarFile(String fileName) {
		return fileName.endsWith(JAR_FILE_EXT);
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> getClassesByFile(String filePath,
			boolean childPackage,ClassFilter filter) {

		List<Class> myClasses = new ArrayList<Class>();

		// System.out.println(filePath + " ++++�??");
		filePath = filePath.replace("%20", " ");

		File file = new File(filePath);

		for (File childFile : file.listFiles()) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClasses.addAll(getClassesByFile(childFile.getPath(),
							childPackage,filter));
				}
			} else {
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					childFilePath = childFilePath.substring(childFilePath
							.indexOf("classes") + 8, childFilePath
							.lastIndexOf("."));
					childFilePath = childFilePath.replace("\\", ".");
					childFilePath = childFilePath.replace("/", ".");
					try{
						Class<?> clazz = loader.loadClass(childFilePath);
						if(filter.accept(clazz)) {
							myClasses.add(clazz);
						}
					} catch(ClassNotFoundException e){
						System.out.println("当前线程没有加载此类");
					}
					
				}
			}
		}
		return myClasses;
	}
}