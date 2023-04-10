package Controller;

import Exceptions.MyException;
import Model.PrgState.PrgState;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    boolean displayFlag = true;
    ExecutorService executor;


    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public Controller(IRepository r){
        repo=r;
    }

    public void oneStep() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repo.getProgramList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executor.shutdownNow();
        //repository.setProgramStates(programStates);
    }

    public void oneStepForAllPrograms(List<PrgState> prgList) throws MyException, IOException, InterruptedException {
        prgList.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
                display(programState);
            } catch (IOException | MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull).toList();

        prgList.addAll(newProgramList);

        prgList.forEach(programState -> {
            try {
                repo.logPrgStateExec(programState);
            } catch (IOException | MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repo.setProgramStates(prgList);
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer>heapAddr, Map<Integer,IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey())|| heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues ){
        return heapValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

//    public void allSteps() throws MyException, IOException {
//        PrgState prg=repo.getCrtPrg();
//        repo.logPrgStateExec();
//        while(!prg.getExeStack().isEmpty()){
//            oneStep(prg);
//            repo.logPrgStateExec();
//            prg.getHeap().setContent((HashMap<Integer, IValue>) safeGarbageCollector(
//                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
//                    getAddrFromHeap(prg.getHeap().getContent().values()),
//                    prg.getHeap().getContent()));
//
//        }
//    }

    public void allSteps() throws InterruptedException, IOException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repo.getProgramList());
        while (programStates.size() > 0) {
            conservativeGarbageCollector(programStates);
            programStates = removeCompletedPrg(repo.getProgramList());
            programStates=removeDuplicatesStates(programStates);
            oneStepForAllPrograms(programStates);
        }

        oneStepForAllPrograms(programStates);
        programStates = removeCompletedPrg(repo.getProgramList());
        programStates=removeDuplicatesStates(programStates);

        executor.shutdownNow();
        repo.setProgramStates(programStates);
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().getContent().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, IValue>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }

    public List<PrgState> removeDuplicatesStates(List<PrgState> prgList){
        return prgList.stream().distinct().collect(Collectors.toList());
    }

    private void display(PrgState prg){
        if(displayFlag) {
            System.out.println(prg.toString());
        }
    }
    public List<PrgState> getProgramStates() {
        return this.repo.getProgramList();
    }
    public void setProgramStates(List<PrgState> programStates) {
        this.repo.setProgramStates(programStates);
    }
}
