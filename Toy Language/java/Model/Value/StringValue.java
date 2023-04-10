package Model.Value;

import Model.Type.IType;
import Model.Type.StringType;

public class StringValue implements IValue{

    private String val;

    public StringValue(String value){
        val=value;
    }
    public String getVal(){
        return val;
    }

    @Override
    public IType getType(){
        return new StringType();
    }

    @Override //object method
    public boolean equals(Object another) {
        if (!(another instanceof IntValue))
            return false;
        StringValue castVal = (StringValue) another;
        return this.val.equals(castVal.val);
    }

    @Override
    public String toString(){
        return "\"" + this.val + "\"";
    }

    @Override
    public IValue deepCopy(){
        return new StringValue(val);
    }

}
