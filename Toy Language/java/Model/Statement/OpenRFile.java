package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    private IExp exp;

    public OpenRFile(IExp expression){
        exp=expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, FileNotFoundException {
        IValue val=exp.eval(state.getSymTable(), state.getHeap());
        if(val.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) val;
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isVarDef(fileName)) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader((fileName.getVal())));
                } catch (FileNotFoundException e) {
                    throw new MyException(fileName.getVal() + "could not be opened");
                }
                fileTable.put(fileName, br);
                state.setFileTable(fileTable);
            } else {
                throw new MyException(fileName.getVal() + "is already opened");
            }
        }
        else
            throw new MyException(exp.toString()+"is not a StringType");

        // return state;
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("OpenReadFile requires a string expression.");
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenReadFile("+exp.toString()+")";
    }
}
