package com.bdm.factory;

public interface IAbstractFactory<T,Type> {
    T create(Type choice) ;
}
