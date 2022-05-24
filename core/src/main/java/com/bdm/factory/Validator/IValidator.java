package com.bdm.factory.Validator;

public interface IValidator<T> {
    public Boolean Validate(String[] args);
    public String Message();
    public int Code();
    public T ReturnValues();
}