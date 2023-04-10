package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException;
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
    IExp deepCopy();
}
