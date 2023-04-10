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
import java.io.IOException;

public class closeRFile implements IStmt{
    private IExp exp;

    public closeRFile(IExp expr){
        exp=expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        IValue value = exp.eval(state.getSymTable(),state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new MyException(exp.toString()+" does not evaluate to StringValue");
        StringValue fileName = (StringValue) value;
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isVarDef(fileName))
            throw new MyException(value+"is not present in the FileTable");
        BufferedReader br = fileTable.lookUp(fileName);
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Unexpected error in closing"+value);
        }
        fileTable.remove(fileName);
        state.setFileTable(fileTable);
        return null;
    }
    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            return typeEnv;
        else
            throw new MyException("CloseReadFile requires a string expression.");
    }



    @Override
    public IStmt deepCopy() {
        return new closeRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "CloseReadFile "+ exp.toString();
    }

}
