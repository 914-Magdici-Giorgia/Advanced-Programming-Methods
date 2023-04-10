package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.Map;

public class ForkStmt implements IStmt{
    private IStmt statement;
    public ForkStmt(IStmt stmt){
        this.statement=stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIStack<IStmt> newStack=new MyStack<>();
        newStack.push(statement);
        MyIDictionary<String, IValue> newSymTable=new MyDictionary<>();
        for(Map.Entry<String,IValue> entry:state.getSymTable().getContent().entrySet()){
            newSymTable.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return new PrgState(newStack,newSymTable,state.getOutput(),state.getFileTable(),state.getHeap());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException  {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }
}
