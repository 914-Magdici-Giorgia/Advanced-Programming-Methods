package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class VarDeclStmt implements IStmt{

    String name;
    IType type;

    public VarDeclStmt(String n, IType t){
        name=n;
        type=t;
    }

    @Override
    public PrgState execute(PrgState state)throws MyException {
        MyIDictionary<String, IValue> symTable=state.getSymTable();
        if(symTable.isVarDef(name)){
            throw new MyException("Variable "+name+" is already defined.");
        }
        else{
            symTable.put(name, type.defaultValue());
            state.setSymTable(symTable);
            // return state;
            return null;
        }
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy(){
        return new VarDeclStmt(name, type.deepCopy());
    }

    @Override
    public String toString(){
        return type.toString()+" "+name;
    }
}
