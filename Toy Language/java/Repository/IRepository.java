package Repository;


import Exceptions.MyException;
import Model.PrgState.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
//    PrgState getCrtPrg();
    void logPrgStateExec(PrgState prg) throws MyException, IOException;
    List<PrgState> getProgramList();
    void setProgramStates(List<PrgState> programStates);

}
