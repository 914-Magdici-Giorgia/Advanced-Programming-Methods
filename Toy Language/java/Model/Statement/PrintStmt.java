package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStmt implements IStmt {
    IExp expression;
    //public PrintStmt()

    public PrintStmt(IExp exp){
        expression=exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIList<IValue> out=state.getOutput();
        IValue val=expression.eval(state.getSymTable(), state.getHeap());
        out.add(val);
        state.setOutput(out);
        // return state;
        return null;    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }


    @Override
    public IStmt deepCopy(){
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "print(" +expression.toString()+")";
    }
}
