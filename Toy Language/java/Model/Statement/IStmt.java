package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Type.IType;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, IOException;
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException;
    IStmt deepCopy();
    //String toString();
}
