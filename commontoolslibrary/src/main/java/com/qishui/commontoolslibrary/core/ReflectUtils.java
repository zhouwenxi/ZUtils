package com.qishui.commontoolslibrary.core;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2018/12/22.
 * https://github.com/jOOQ/jOOR
 * http://blankj.com
 */

//实例化反射对象
//String str1 = ReflectUtils.with(String.class).newInstance().get();
//String str1 = new String();
//String str2 = ReflectUtils.with("java.lang.String").newInstance("abc").get();
//String str2 = new String("abc");
//String str3 = ReflectUtils.with(String.class).newInstance("abc".getBytes()).get();
//String str3 = new String("abc".getBytes());

//设置反射的方法
//String str1 = ReflectUtils.with((Object) "1234").method("substring", 2).get();
// equals: String str1 = "1234".substring(2);
//String str2 = ReflectUtils.with((Object) "1234").method("substring", 0, 2).get();
// equals: String str1 = "1234".substring(0, 2);

//    设置反射的字段
//public class TestPrivateStaticFinal {
//    private static final int     I1 = new Integer(1);
//    private static final Integer I2 = new Integer(1);
//}
//我们要设置其 I1、I2 值为 2，可以如下操作：
// ReflectUtils.with(TestPrivateStaticFinal.class).field("I1", 2);
// ReflectUtils.with(TestPrivateStaticFinal.class).field("I2", 2);

//要获取其 I1、I2 值的话，可以如下操作：
//ReflectUtils.with(TestPrivateStaticFinal.class).field("I1").get()
//ReflectUtils.with(TestPrivateStaticFinal.class).field("I2").get()


public class ReflectUtils {

    private final Class<?> type;

    private final Object object;

    private ReflectUtils(final Class<?> type) {
        this(type, type);
    }

    private ReflectUtils(final Class<?> type, Object object) {
        this.type = type;
        this.object = object;
    }

    ///////////////////////////////////////////////////////////////////////////
    // with
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Reflect the class.
     *
     * @param className The name of class.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public static ReflectUtils with(final String className) throws ReflectException {
        return with(forName(className));
    }

    /**
     * Reflect the class.
     *
     * @param className   The name of class.
     * @param classLoader The loader of class.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public static ReflectUtils with(final String className, final ClassLoader classLoader) throws ReflectException {
        return with(forName(className, classLoader));
    }

    /**
     * Reflect the class.
     *
     * @param clazz The class.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public static ReflectUtils with(final Class<?> clazz) throws ReflectException {
        return new ReflectUtils(clazz);
    }

    /**
     * Reflect the class.
     *
     * @param object The object.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public static ReflectUtils with(final Object object) throws ReflectException {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    private static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    private static Class<?> forName(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // newInstance
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create and initialize a new instance.
     *
     * @return the single {@link ReflectUtils} instance
     */
    public ReflectUtils newInstance() {
        return newInstance(new Object[0]);
    }

    /**
     * Create and initialize a new instance.
     *
     * @param args The args.
     * @return the single {@link ReflectUtils} instance
     */
    public ReflectUtils newInstance(Object... args) {
        Class<?>[] types = getArgsType(args);
        try {
            Constructor<?> constructor = type().getDeclaredConstructor(types);
            return newInstance(constructor, args);
        } catch (NoSuchMethodException e) {
            List<Constructor<?>> list = new ArrayList<>();
            for (Constructor<?> constructor : type().getDeclaredConstructors()) {
                if (match(constructor.getParameterTypes(), types)) {
                    list.add(constructor);
                }
            }
            if (list.isEmpty()) {
                throw new ReflectException(e);
            } else {
                sortConstructors(list);
                return newInstance(list.get(0), args);
            }
        }
    }

    private Class<?>[] getArgsType(final Object... args) {
        if (args == null) return new Class[0];
        Class<?>[] result = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object value = args[i];
            result[i] = value == null ? NULL.class : value.getClass();
        }
        return result;
    }

    private void sortConstructors(List<Constructor<?>> list) {
        Collections.sort(list, new Comparator<Constructor<?>>() {
            @Override
            public int compare(Constructor<?> o1, Constructor<?> o2) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int len = types1.length;
                for (int i = 0; i < len; i++) {
                    if (!types1[i].equals(types2[i])) {
                        if (wrapper(types1[i]).isAssignableFrom(wrapper(types2[i]))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    private ReflectUtils newInstance(final Constructor<?> constructor, final Object... args) {
        try {
            return new ReflectUtils(
                    constructor.getDeclaringClass(),
                    accessible(constructor).newInstance(args)
            );
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // field
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Get the field.
     *
     * @param name The name of field.
     * @return the single {@link ReflectUtils} instance
     */
    public ReflectUtils field(final String name) {
        try {
            Field field = getField(name);
            return new ReflectUtils(field.getType(), field.get(object));
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Set the field.
     *
     * @param name  The name of field.
     * @param value The value.
     * @return the single {@link ReflectUtils} instance
     */
    public ReflectUtils field(String name, Object value) {
        try {
            Field field = getField(name);
            field.set(object, unwrap(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Field getField(String name) throws IllegalAccessException {
        Field field = getAccessibleField(name);
        if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException ignore) {
                // runs in android will happen
            }
        }
        return field;
    }

    private Field getAccessibleField(String name) {
        Class<?> type = type();
        try {
            return accessible(type.getField(name));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return accessible(type.getDeclaredField(name));
                } catch (NoSuchFieldException ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            throw new ReflectException(e);
        }
    }

    private Object unwrap(Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).get();
        }
        return object;
    }

    ///////////////////////////////////////////////////////////////////////////
    // method
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Invoke the method.
     *
     * @param name The name of method.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public ReflectUtils method(final String name) throws ReflectException {
        return method(name, new Object[0]);
    }

    /**
     * Invoke the method.
     *
     * @param name The name of method.
     * @param args The args.
     * @return the single {@link ReflectUtils} instance
     * @throws ReflectException if with unsuccessfully
     */
    public ReflectUtils method(final String name, final Object... args) throws ReflectException {
        Class<?>[] types = getArgsType(args);
        try {
            Method method = exactMethod(name, types);
            return method(method, object, args);
        } catch (NoSuchMethodException e) {
            try {
                Method method = similarMethod(name, types);
                return method(method, object, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    private ReflectUtils method(final Method method, final Object obj, final Object... args) {
        try {
            accessible(method);
            if (method.getReturnType() == void.class) {
                method.invoke(obj, args);
                return with(obj);
            } else {
                return with(method.invoke(obj, args));
            }
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    private Method exactMethod(final String name, final Class<?>[] types)
            throws NoSuchMethodException {
        Class<?> type = type();
        try {
            return type.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            do {
                try {
                    return type.getDeclaredMethod(name, types);
                } catch (NoSuchMethodException ignore) {
                }
                type = type.getSuperclass();
            } while (type != null);
            throw new NoSuchMethodException();
        }
    }

    private Method similarMethod(final String name, final Class<?>[] types)
            throws NoSuchMethodException {
        Class<?> type = type();
        List<Method> methods = new ArrayList<>();
        for (Method method : type.getMethods()) {
            if (isSimilarSignature(method, name, types)) {
                methods.add(method);
            }
        }
        if (!methods.isEmpty()) {
            sortMethods(methods);
            return methods.get(0);
        }
        do {
            for (Method method : type.getDeclaredMethods()) {
                if (isSimilarSignature(method, name, types)) {
                    methods.add(method);
                }
            }
            if (!methods.isEmpty()) {
                sortMethods(methods);
                return methods.get(0);
            }
            type = type.getSuperclass();
        } while (type != null);

        throw new NoSuchMethodException("No similar method " + name + " with params " + Arrays.toString(types) + " could be found on type " + type() + ".");
    }

    private void sortMethods(final List<Method> methods) {
        Collections.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                Class<?>[] types1 = o1.getParameterTypes();
                Class<?>[] types2 = o2.getParameterTypes();
                int len = types1.length;
                for (int i = 0; i < len; i++) {
                    if (!types1[i].equals(types2[i])) {
                        if (wrapper(types1[i]).isAssignableFrom(wrapper(types2[i]))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    private boolean isSimilarSignature(final Method possiblyMatchingMethod,
                                       final String desiredMethodName,
                                       final Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName)
                && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    private boolean match(final Class<?>[] declaredTypes, final Class<?>[] actualTypes) {
        if (declaredTypes.length == actualTypes.length) {
            for (int i = 0; i < actualTypes.length; i++) {
                if (actualTypes[i] == NULL.class
                        || wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
                    continue;
                }
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private <T extends AccessibleObject> T accessible(T accessible) {
        if (accessible == null) return null;
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers())
                    && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) accessible.setAccessible(true);
        return accessible;
    }

    ///////////////////////////////////////////////////////////////////////////
    // proxy
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Create a proxy for the wrapped object allowing to typesafely invoke
     * methods on it using a custom interface.
     *
     * @param proxyType The interface type that is implemented by the proxy.
     * @return a proxy for the wrapped object
     */
    @SuppressWarnings("unchecked")
    public <P> P proxy(final Class<P> proxyType) {
        final boolean isMap = (object instanceof Map);
        final InvocationHandler handler = new InvocationHandler() {
            @Override
            @SuppressWarnings("null")
            public Object invoke(Object proxy, Method method, Object[] args) {
                String name = method.getName();
                try {
                    return with(object).method(name, args).get();
                } catch (ReflectException e) {
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
        return (P) Proxy.newProxyInstance(proxyType.getClassLoader(),
                new Class[]{proxyType},
                handler);
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

    private Class<?> type() {
        return type;
    }

    private Class<?> wrapper(final Class<?> type) {
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

    /**
     * Get the result.
     *
     * @param <T> The value type.
     * @return the result
     */
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) object;
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReflectUtils && object.equals(((ReflectUtils) obj).get());
    }

    @Override
    public String toString() {
        return object.toString();
    }

    private static class NULL {
    }

    public static class ReflectException extends RuntimeException {

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

