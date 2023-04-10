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

public class WhileStmt implements IStmt{
    private IExp expression;
    private IStmt statement;

    public WhileStmt (IExp exp, IStmt stmt){
        expression=exp;
        statement=stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        IValue value=expression.eval(state.getSymTable(),state.getHeap());
        MyIStack<IStmt> stack=state.getExeStack();
        if(!value.getType().equals(new BoolType()))
            throw new MyException(value+" is not of BoolType");
        BoolValue boolValue=(BoolValue) value;
        if(boolValue.getVal()){
            stack.push(this);
            stack.push(statement);
        }
        // return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExpr = expression.typeCheck(typeEnv);
        if (typeExpr.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The condition of WHILE does not have the type Bool.");
    }

    @Override
    public IStmt deepCopy(){
        return new WhileStmt(expression.deepCopy(),statement.deepCopy());
    }

    @Override
    public String toString(){
        return "while( "+expression+" ){ "+statement+" }";
    }
}
