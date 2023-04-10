package Model.PrgState;

import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.Statement.IStmt;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String,IValue> symTable;
    private MyIList<IValue> output;
    private IStmt originalProgram;
    private MyIDictionary<StringValue, BufferedReader>fileTable;
    private MyIHeap heap;
    private int id;
    private static int lastId = 0;

    public PrgState(MyIStack<IStmt> exeS,
                     MyIDictionary<String, IValue> symT,
                     MyIList<IValue> out,
                     MyIDictionary<StringValue, BufferedReader> fileT,
                     MyIHeap h,
                    IStmt program){
        exeStack=exeS;
        symTable=symT;
        output=out;
        fileTable=fileT;
        heap=h;
        this.originalProgram=program.deepCopy();
        this.exeStack.push(this.originalProgram);
        this.id=setId();
    }
    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,IValue> symTable, MyIList<IValue> out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    public int getId() {
        return id;
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIList<IValue> getOutput() {
        return output;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOutput(MyIList<IValue> output) {
        this.output = output;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    public boolean isNotCompleted() {
        return exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, IOException {
        if(exeStack.isEmpty())
            throw  new MyException("prgstate stack is empty");
        IStmt  crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public String toString(){
        return "Id:"+id+"\n"+
                "ExeStack:"+exeStack.toString()+"\n"+
                "SymTable:"+symTable.toString()+"\n"+
                "HeapTable:"+heap.toString()+"\n"+
                "Output:"+output.toString()+"\n"+
                "FileTable"+fileTable.toString()+"\n";
    }


}
