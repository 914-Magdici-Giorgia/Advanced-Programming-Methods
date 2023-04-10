package Model.Statement;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.Expression.IExp;
import Model.PrgState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    private IExp exp;
    private String var_name;

    public ReadFile(IExp expression, String name){
        exp=expression;
        var_name=name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException {
        MyIDictionary<String, IValue> symTable=state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable=state.getFileTable();

        if(symTable.isVarDef(var_name)){
            IValue val=symTable.lookUp(var_name);
            if(val.getType().equals(new IntType())){
                val=exp.eval(symTable, state.getHeap());
                if(val.getType().equals(new StringType())){
                    StringValue castVal=(StringValue)val;
                    if(fileTable.isVarDef(castVal)){
                        BufferedReader br=fileTable.lookUp(castVal);
                        try{
                            String line=br.readLine();
                            if(line==null)
                                line="0";
                            symTable.put(var_name,new IntValue(Integer.parseInt(line)));
                        }
                        catch (IOException e){
                            throw new MyException("Could not read from file "+castVal);
                        }
                        }
                    else
                        throw new MyException(castVal+" was not found in the fileTable");
                }
                else
                    throw new MyException(val+" is not of StringType");
                }
            else
                throw new MyException(val+"is not of IntType");
            }
        else
            throw new MyException(var_name+"was not found in the symTable");

        // return state;
        return null;
        }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (exp.typeCheck(typeEnv).equals(new StringType()))
            if (typeEnv.lookUp(var_name).equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("ReadFile requires an int as its variable parameter.");
        else
            throw new MyException("ReadFile requires a string as es expression parameter.");
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), var_name);
    }

    @Override
    public String toString() {
        return "ReadFile "+exp.toString()+" "+var_name;
    }
    }


