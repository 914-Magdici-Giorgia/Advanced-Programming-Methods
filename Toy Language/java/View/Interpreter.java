package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.MyDictionary;
import Model.ADT.MyHeap;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Expression.*;
import Model.PrgState.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;

class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        try {
            ex1.typeCheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex2 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new VarDeclStmt("b", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ArithExp(
                                    "+",
                                    new ValueExp(new IntValue(2)),
                                    new ArithExp(
                                            "*",
                                            new ValueExp(new IntValue(3)),
                                            new ValueExp(new IntValue(5))))
                            ),
                            new CompStmt(
                                    new AssignStmt("b", new ArithExp(
                                            "+",
                                            new VarExp("a"),
                                            new ValueExp(new IntValue(1)))),
                                    new PrintStmt(new VarExp("b")))
                    )
            )
        );
        try {
            ex2.typeCheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);
            menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
        try {
            ex3.typeCheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }




        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new closeRFile(new VarExp("varf"))))))))));

        try {
            ex4.typeCheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                                        new IfStmt(new RelationalExp(">", new VarExp("a"),
                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
                                                new PrintStmt(new VarExp("b")))))));
        try {
            ex5.typeCheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        try {
            ex6.typeCheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        try {
            ex7.typeCheck(new MyDictionary<>());
            PrgState prg7= new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp("+",new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            ex8.typeCheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp("+", new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            ex9.typeCheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex9);
            IRepository repo9 = new Repository(prg9, "log9.txt");
            Controller ctr9 = new Controller(repo9);
            menu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }



        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex10.typeCheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap(), ex10);
            IRepository repo10 = new Repository(prg10, "log10.txt");
            Controller ctr10 = new Controller(repo10);
            menu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
        }catch ( MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        //  Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex11.typeCheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            IRepository repo11;
            repo11 = new Repository(prg11, "log11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), controller11));
        } catch (MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex12 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        try {
            ex12.typeCheck(new MyDictionary<>());
            PrgState prg12 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex12);
            IRepository repo12;
            repo12 = new Repository(prg12, "log12.txt");
            Controller controller12 = new Controller(repo12);
            menu.addCommand(new RunExampleCommand("12", ex12.toString(), controller12));
        } catch (MyException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }




        menu.show();
    }
    }