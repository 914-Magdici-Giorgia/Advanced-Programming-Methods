package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.Objects;

public class ArithExp implements IExp{
    IExp e1,e2;
    String op;  //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp( String oper,IExp ex1, IExp ex2){
        e1=ex1;
        e2=ex2;
        op=oper;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("Second operand is not an integer.");
        } else
            throw new MyException("First operand is not an integer.");
    }
    @Override
    public IValue eval(MyIDictionary<String,IValue> symTable, MyIHeap heap) throws  MyException{
        IValue v1,v2;
        v1=e1.eval(symTable,heap);
        if(v1.getType().equals(new IntType())) {
            v2 = e2.eval(symTable,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (Objects.equals(this.op, "+"))
                    return new IntValue(n1+n2);
                if (Objects.equals(this.op, "-"))
                    return new IntValue(n1-n2);
                if (Objects.equals(this.op, "*"))
                    return new IntValue(n1*n2);
                if (Objects.equals(this.op, "/"))
                    if (n2 == 0) throw new MyException("division by zero");
                    else
                        return new IntValue(n1/n2);
            } else
                throw new MyException("second operand is not an integer");
        }
        else throw new MyException("first operand is not an integer");

        return null;
    }

    @Override
    public IExp deepCopy(){
        return new ArithExp(op,e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString(){
             return e1.toString()+op+e2.toString();
    }
}
