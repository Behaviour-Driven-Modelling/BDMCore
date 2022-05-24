package com.bdm.factory;

public interface IAbstractFactory<T> {
    T create(String choice) ;
}
