package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Type.IType;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute (PrgState state){
        return null;
    }
    @Override
    public IStmt deepCopy(){
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString(){
        return "NopStatement";
    }
}
