package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.PrgState.PrgState;
import Model.Type.IType;

public class CompStmt implements IStmt{
    IStmt firstStmt, secondStmt;

    public CompStmt (IStmt fStmt, IStmt sStmt){
        firstStmt=fStmt;
        secondStmt=sStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(secondStmt);
        stack.push(firstStmt);
        state.setExeStack(stack);
        //return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return secondStmt.typeCheck(firstStmt.typeCheck(typeEnv));
    }


    @Override
    public IStmt deepCopy(){
        return new CompStmt(firstStmt.deepCopy(), secondStmt.deepCopy());
    }

    @Override
    public String toString() {
        return firstStmt.toString() + ";" + secondStmt.toString();
    }
}
