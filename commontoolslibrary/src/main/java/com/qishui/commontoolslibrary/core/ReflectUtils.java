package com.qishui.commontoolslibrary.core;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhou on 2018/12/22.
 * https://github.com/jOOQ/jOOR
 */

public class ReflectUtils {

    /**
     * Wrap a class name.
     */
    public static ReflectUtils on(String name) throws ReflectException {
        return on(forName(name));
    }

    /**
     * Wrap a class name, loading it via a given class loader.
     */
    public static ReflectUtils on(String name, ClassLoader classLoader) throws ReflectException {
        return on(forName(name, classLoader));
    }

    /**
     * Wrap a class.
     */
    public static ReflectUtils on(Class<?> clazz) {
        return new ReflectUtils(clazz);
    }


    /**
     * Wrap an object.
     */
    public static ReflectUtils on(Object object) {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    /**
     * Wrap an object.
     */
    private static ReflectUtils on(Class<?> type, Object object) {
        return new ReflectUtils(type, object);
    }

    /**
     * Get the initialisation or default value for any given type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T initValue(Class<T> type) {
        return type == boolean.class
                ? (T) Boolean.FALSE
                : type == byte.class
                ? (T) Byte.valueOf((byte) 0)
                : type == short.class
                ? (T) Short.valueOf((short) 0)
                : type == int.class
                ? (T) Integer.valueOf(0)
                : type == long.class
                ? (T) Long.valueOf(0L)
                : type == double.class
                ? (T) Double.valueOf(0.0)
                : type == float.class
                ? (T) Float.valueOf(0.0f)
                : type == char.class
                ? (T) Character.valueOf((char) 0)
                : (T) null;
    }


    /**
     * 可以使用
     */
    public static <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) {
            return null;
        }

        //成员内部类
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }

        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }

        return accessible;
    }


    private final Class<?> type;
    private final Object object;


    private ReflectUtils(Class<?> type) {
        this(type, type);
    }

    private ReflectUtils(Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }


    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }


    /**
     * 给属性赋值
     *
     * @param name
     * @param value
     * @return
     * @throws ReflectException
     */
    public ReflectUtils set(String name, Object value) {
        try {
            Field field = field0(name);
            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                try {
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                } catch (NoSuchFieldException ignore) {
                }
            }
            field.set(object, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }


    /**
     * 获取一个字段值
     *
     * @param name
     * @param <T>
     * @return
     * @throws ReflectException
     */
    public <T> T get(String name) {
        return field(name).<T>get();
    }

    /**
     * Get a wrapped field.
     */
    public ReflectUtils field(String name) {
        try {
            Field field = field0(name);
            return on(field.getType(), field.get(object));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field field0(String name) {
        Class<?> t = type();

        // Try getting a public field
        try {
            return accessible(t.getField(name));
        }

        // Try again, getting a non-public field
        catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(t.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }

                t = t.getSuperclass();
            }
            while (t != null);

            throw new ReflectException(e);
        }
    }

    /**
     * 获取属性值
     */
    public Map<String, ReflectUtils> fields() {
        Map<String, ReflectUtils> result = new LinkedHashMap<String, ReflectUtils>();
        Class<?> t = type();

        do {
            for (Field field : t.getDeclaredFields()) {
                if (type != object ^ Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();

                    if (!result.containsKey(name))
                        result.put(name, field(name));
                }
            }
            t = t.getSuperclass();
        } while (t != null);

        return result;
    }

    /**
     * Call a method by its name.
     */
    public ReflectUtils call(String name) throws ReflectException {
        return call(name, new Object[0]);
    }

    /**
     * Call a method by its name.
     */
    public ReflectUtils call(String name, Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        // Try invoking the "canonical" method, i.e. the one with exact
        // matching argument types
        try {
            Method method = exactMethod(name, types);
            return on(method, object, args);
        }

        // If there is no exact match, try to find a method that has a "similar"
        // signature if primitive argument types are converted to their wrappers
        catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return on(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    /**
     * Searches a method with the exact same signature as desired.
     */
    private Method exactMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        try {
            return t.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            do {
                try {
                    return t.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {

                }
                t = t.getSuperclass();
            } while (t != null);

            throw new NoSuchMethodException();
        }
    }

    /**
     * Searches a method with a similar signature as desired using
     */
    private Method similarMethod(String name, Class<?>[] types) throws NoSuchMethodException {
        Class<?> t = type();

        for (Method method : t.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                return method;
            }
        }

        do {
            for (Method method : t.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    return method;
                }
            }

            t = t.getSuperclass();
        }
        while (t != null);

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + type() + ".");
    }

    /**
     * Determines if a method has a "similar" signature, especially if wrapping
     * primitive argument types would result in an exactly matching signature.
     */
    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    /**
     * Call a constructor.
     */
    public ReflectUtils create() throws ReflectException {
        return create(new Object[0]);
    }

    /**
     * Call a constructor.
     */
    public ReflectUtils create(Object... args) throws ReflectException {
        Class<?>[] types = types(args);

        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return on(constructor, args);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    return on(constructor, args);
                }
            }

            throw new ReflectException(e);
        }
    }

    /**
     * Create a proxy for the wrapped object allowing to typesafely invoke
     */
    @SuppressWarnings("unchecked")
    public <P> P as(final Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            @SuppressWarnings("null")
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();

                // Actual method name matches always come first
                try {
                    return on(type, object).call(name, args).get();
                }

                // [#14] Emulate POJO behaviour on wrapped map objects
                catch (ReflectException e) {
                    if (isMap) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        int length = (args == null ? 0 : args.length);

                        if (length == 0 && name.startsWith("get")) {
                            return map.get(property(name.substring(3)));
                        } else if (length == 0 && name.startsWith("is")) {
                            return map.get(property(name.substring(2)));
                        } else if (length == 1 && name.startsWith("set")) {
                            map.put(property(name.substring(3)), args[0]);
                            return null;
                        }
                    }


                    throw e;
                }
            }
        };

        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(), new Class[]{proxyType}, handler);
    }

    /**
     * Get the POJO property name of an getter/setter
     */
    private static String property(String string) {
        int length = string.length();
        if (length == 0) {
            return "";
        } else if (length == 1) {
            return string.toLowerCase();
        } else {
            return string.substring(0, 1).toLowerCase() + string.substring(1);
        }
    }


    /**
     * Check whether two arrays of types match, converting primitive types to
     * their corresponding wrappers.
     */
    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class)
                    continue;

                if (wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i])))
                    continue;

                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReflectUtils) {
            return object.equals(((ReflectUtils) obj).get());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(object);
    }

    // ---------------------------------------------------------------------
    // Utility methods
    // ---------------------------------------------------------------------

    /**
     * Wrap an object created from a constructor
     */
    private static ReflectUtils on(Constructor<?> constructor, Object... args) throws ReflectException {
        try {
            return on(constructor.getDeclaringClass(), accessible(constructor).newInstance(args));
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Wrap an object returned from a method
     */
    private static ReflectUtils on(Method method, Object object, Object... args) throws ReflectException {
        try {
            accessible(method);

            if (method.getReturnType() == void.class) {
                method.invoke(object, args);
                return on(object);
            } else {
                return on(method.invoke(object, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Unwrap an object
     */
    private static Object unwrap(Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }
        return object;
    }

    /**
     * Get an array of types for an array of objects
     *
     * @see Object#getClass()
     */
    private static Class<?>[] types(Object... values) {
        if (values == null) {
            return new Class[0];
        }

        Class<?>[] result = new Class[values.length];

        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }

        return result;
    }


    /**
     * 加载一个class
     */
    private static Class<?> forName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * 加载一个class
     */
    private static Class<?> forName(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Get the type of the wrapped object.
     *
     * @see Object#getClass()
     */
    public Class<?> type() {
        return type;
    }


    /**
     * 获取值类型的基本类型
     *
     * @param type
     * @return
     */
    public static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }

    private static class NULL {
    }

    public static class ReflectException extends RuntimeException {
        public ReflectException() {
        }

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}
