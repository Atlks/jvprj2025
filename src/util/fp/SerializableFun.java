package util.fp;

import util.algo.FunctionThrowing;

import java.io.Serializable;

// 泛型接口，继承 Serializable，方便获取 SerializedLambda
@FunctionalInterface
public interface SerializableFun<T, Rt> extends FunctionThrowing<T, Rt>, Serializable {}

