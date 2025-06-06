package orgx.uti.orm;

/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Objects;

import static orgx.uti.Uti.getDeclaredMethod;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 *
 * @since 1.8
 */
@FunctionalInterface
public interface FunctionX<T, R> extends Serializable {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Throwable;


    /**
     * 实现获取  某个静态lambda方法 method
     * @return
     */
    default Method getLambdaMethod() {
        try {
            // 通过反射获取 `writeReplace` 方法..
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(this);

            // 获取 Lambda 所在的类名，并加载 Class
            String implClass1 = lambda.getImplClass();
            implClass1=implClass1.replace('/', '.');
            Class<?> implClass = Class.forName(implClass1);
            Method implMethod = getDeclaredMethod(implClass,lambda.getImplMethodName());
            return implMethod;

        } catch (Exception e) {
            throw new RuntimeException("无法获取 Lambda 方法名称", e);
        }
    }

    default String getLambdaMethodName() {
        try {
            // 通过反射获取 `writeReplace` 方法..
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(this);

            return lambda.getImplClass()+"."+lambda.getImplMethodName();
            //+ lambda.getImplMethodSignature();
            //lambda.getImplMethodName(); // 获取方法名
        } catch (Exception e) {
            throw new RuntimeException("无法获取 Lambda 方法名称", e);
        }
    }


    /**
     * 获取方法的第一个参数
     * @return
     */
    default Object getLambdaMethodParamFirst() {
        try {
            // 通过反射获取 `writeReplace` 方法
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(this);

            return lambda.getCapturedArg(0);
        } catch (Exception e) {
            throw new RuntimeException("无法获取 Lambda 方法名称", e);
        }
    }

    /**
     * 获取方法的第一个参数类型
     * @return
     */
    default Class getLambdaMethodParamFirstType() {
        try {
            // 通过反射获取 `writeReplace` 方法
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(this);

            // 获取实现类的 Class
            Class<?> implClass = Class.forName(lambda.getImplClass().replace("/", "."));
        //    lambda.getImplMethodSignature()
            String implMethodName = lambda.getImplMethodName();
            Method implMethod = getDeclaredMethod(implClass,implMethodName);

            // 返回第一个参数的类型
            return implMethod.getParameterTypes()[0];
         //   return null;
        } catch (Exception e) {
            throw new RuntimeException("无法获取 Lambda 方法名称", e);
        }
    }



    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(java.util.function.Function)
     */
    default <V> FunctionX<V, R> compose(FunctionX<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     *
     * @see #compose(java.util.function.Function)
     */
    default <V> FunctionX<T, V> andThen(FunctionX<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T>  FunctionX<T, T> identity() {
        return t -> t;
    }


}
