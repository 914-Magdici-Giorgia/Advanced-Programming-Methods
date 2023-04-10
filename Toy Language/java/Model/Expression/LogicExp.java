package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

import java.util.Objects;

public class LogicExp implements IExp{
    IExp e1,e2;
    String op;
    public LogicExp(IExp exp1, IExp exp2, String ope){
        e1=exp1;
        e2=exp2;
        op=ope;
    }
    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("Second operand is not a boolean.");
        } else
            throw new MyException("First operand is not a boolean.");

    }

    @Override
    public IValue eval(MyIDictionary<String,IValue> symTable, MyIHeap heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(symTable,heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(symTable,heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue bool1 = (BoolValue) v1;
                BoolValue bool2 = (BoolValue) v2;
                boolean b1, b2;
                b1 = bool1.getVal();
                b2 = bool2.getVal();
                if (Objects.equals(op, "and"))
                    return new BoolValue(b1 && b2);
                else if (Objects.equals(this.op, "or"))
                    return new BoolValue(b1 || b2);
            } else throw new MyException("Second operand is not a boolean.");
        } else throw new MyException("First operand is not a boolean");

    return null;
    }
    @Override
    public IExp deepCopy(){
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }
    @Override
    public String toString(){
        return e1.toString()+" "+op+" "+e2.toString();
    }
}
