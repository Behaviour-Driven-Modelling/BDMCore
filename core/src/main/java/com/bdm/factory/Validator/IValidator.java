package com.bdm.factory.validator;

public interface IValidator<T> {
    public Boolean Validate(T args);
    public String Message();
    public int Code();
    public T ReturnValues();
}
