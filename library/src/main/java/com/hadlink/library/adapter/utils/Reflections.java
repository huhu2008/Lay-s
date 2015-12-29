package com.hadlink.library.adapter.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple class to help caching reflection stuff from the generic layout builders
 */
public class Reflections {

    private static Map<String, Method> methods = new HashMap<>();
    private static Map<String, Constructor> constructors = new HashMap<>();

    /**
     * Returns the {@code Method}  present on the class provided by clazz, with the name given on the name
     * parameter and the parameter types specified in params.
     * <p/>
     * The reflection calls are cached in memory so subsequent calls to this method will be fast.
     *
     * @param clazz  class that contains the wanted method
     * @param name   name of the method
     * @param params parameter types of the method
     * @return the desired method from {@code clazz} and {@code name}
     * @throws NoSuchMethodException
     */
    public static Method method(Class clazz, String name, Class<?>... params) throws NoSuchMethodException {
        List<String> paramClassNames = new ArrayList<>();
        for (Class<?> c : params) {
            paramClassNames.add(c.getCanonicalName());
        }

        final String fullName = clazz.getCanonicalName() + "." + name + "(" + join("+", paramClassNames) + ")";
        if (!methods.containsKey(fullName)) {
            Method method = clazz.getMethod(name, params);
            methods.put(fullName, method);
        }
        return methods.get(fullName);
    }

    /**
     * Returns the {@code Constructor} for the class provided, with the params given.
     * No params mean the empty constructor.
     * <p/>
     * The reflection calls are cached in memory so subsequent calls to this method will be fast.
     *
     * @param clazz  class where we want to get the constructor
     * @param params parameter types of the constructor, could be empty
     * @return the desired constructor
     * @throws NoSuchMethodException
     */
    public static Constructor constructor(Class clazz, Class<?>... params) throws NoSuchMethodException {
        List<String> paramClassNames = new ArrayList<>();
        for (Class<?> c : params) {
            paramClassNames.add(c.getCanonicalName());
        }
        final String fullName = clazz.getCanonicalName() + "(" + join("+", paramClassNames) + ")";

        if (!constructors.containsKey(fullName)) {
            Constructor constructor = clazz.getConstructor(params);
            constructors.put(fullName, constructor);
        }

        return constructors.get(fullName);
    }

    /**
     * Taken from Android's TextUtils.
     */
    private static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
}
