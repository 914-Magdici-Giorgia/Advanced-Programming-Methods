package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStmt implements IStmt{

    IExp exp;
    IStmt thenS, elseS;
    public IfStmt(IExp e, IStmt t, IStmt el){
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, IValue> symTable=state.getSymTable();
        MyIStack<IStmt>stack=state.getExeStack();

        IValue evalResult=this.exp.eval(symTable, state.getHeap());
        if(evalResult instanceof BoolValue boolResult){
            IStmt statement;
            if(boolResult.getVal()){
                statement=thenS;
            }
            else {
                statement=elseS;
            }
           stack.push(statement);
        }
        else{
            throw new MyException("The expression from the if statement is not boolean");
        }

        state.setExeStack(stack);
        //return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExpr = exp.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The condition of IF does not have the type Bool.");
    }


    @Override
    public IStmt deepCopy(){
     return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

}
