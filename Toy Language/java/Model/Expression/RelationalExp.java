package Model.Expression;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.Objects;

public class RelationalExp implements IExp{
    IExp expression1;
    IExp expression2;
    String operator;

    public RelationalExp(String operator, IExp expression1, IExp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("Second operand is not an integer.");
        } else
            throw new MyException("First operand is not an integer.");

    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws MyException {
        IValue value1, value2;
        value1 = this.expression1.eval(symTable,heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.expression2.eval(symTable,heap);
            if (value2.getType().equals(new IntType())) {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getVal();
                v2 = val2.getVal();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            } else
                throw new MyException("Second operand is not an integer.");
        } else
            throw new MyException("First operand in not an integer.");
        return null;
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(operator, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return this.expression1.toString() + " " + this.operator + " " + this.expression2.toString();
    }
}