package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStmt implements IStmt{

    private String key;
    private IExp expression;

    public AssignStmt (String k, IExp e){
        key=k;
        expression=e;
    }



    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable=state.getSymTable();
      //  MyIStack<IStmt> stack=state.getExeStack();
        if(symTable.isVarDef(key)){
            IValue val=expression.eval(symTable, state.getHeap());
            IType typeId= symTable.lookUp(key).getType();
            if(val.getType().equals(typeId))
                symTable.update(key,val);
            else throw new MyException("declared type of variable"+key+" and type of  the assigned expression do not match");
        }
        else throw new MyException("the used variable" +key + " was not declared before");
        state.setSymTable(symTable);
        //return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        IType typeVar = typeEnv.lookUp(key);
        IType typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types.");
    }

    @Override
    public IStmt deepCopy(){
        return new AssignStmt(key, expression.deepCopy());
    }

    @Override
    public String toString(){
        return key+"="+expression.toString();
    }
}
