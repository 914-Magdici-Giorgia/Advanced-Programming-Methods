package Repository;

import Exceptions.MyException;
import Model.PrgState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> repo;
    String logFilePath;
    public Repository(PrgState prg,String lfp){
        repo=new LinkedList<>();
        repo.add(prg);
        logFilePath=lfp;

    }
//    public PrgState getCrtPrg(){
//        return repo.get(0);
//    }

    @Override
    public void logPrgStateExec(PrgState prg) throws MyException, IOException {
        PrintWriter logFile=new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
        logFile.println(prg.toString());
        logFile.close();
    }

    @Override
    public List<PrgState> getProgramList() {
        return this.repo;
    }

    @Override
    public void setProgramStates(List<PrgState> programStates) {
        this.repo = programStates;
    }

}
