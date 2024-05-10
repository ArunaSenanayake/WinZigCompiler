import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class WinZigParser {
    private Stack<TreeNode> stack;
    private ArrayList<Token> screenedTokens;
    private Token nextToken;

    public WinZigParser(){
        this.screenedTokens = WinZigScreener.screenedTokens;
        this.stack = new Stack();
    }

    private void read(String tokenName) {
        if(nextToken.getTokenName().equals("Keyword") && nextToken.getValue().equals(tokenName)){
            nextToken = WinZigScreener.getNextToken();
        } else {
            if (nextToken.getTokenName().equals(tokenName)) {
                nextToken = WinZigScreener.getNextToken();
            } else {
                System.out.println("Error: expected " + tokenName + ", but found " + nextToken.getTokenName() + ".");
                throw new Error();

            }
        }
    }

    private void buildTree(String x, int n) {
        TreeNode p = null;
        for (int i = 0; i < n; i++) {
            TreeNode c = stack.pop();
            c.setRightChild(p);
            p = c;
        }
        stack.push(new TreeNode(x, p, null, n));
    }

    public AbstractSyntaxTree getAbstractSyntaxTree() {
        nextToken = WinZigScreener.getNextToken();
        WinZig();
        return new AbstractSyntaxTree(stack.pop());
    }

    private void WinZig(){
        read("Program");
        Name();
        read("Colon");
        Consts();
        Types();
        Dclns();
        SubProgs();
        Body();
        Name();
        read("EOF");
        buildTree("program", 7);
    }

    private void Name(){
        if (nextToken.getTokenName().equals("Identifier")) {
            buildTree(nextToken.getValue(), 0);
            buildTree("<identifier>", 1);
            nextToken = WinZigScreener.getNextToken();
        } else {
            System.out.println("Error: expected " + "Identifier" + ". but found " + nextToken.getTokenName() + ".");
            throw new Error();
        }
    }

    private void Consts(){
        if (nextToken.getTokenName() == "Constant") {
            read("Constant");
            Const();
            int n = 1;
            while (nextToken.getTokenName() == "Comma") {
                read("Comma");
                Const();
                n++;
            }
            read("Semi colon");
            buildTree("consts", n);
        } else {
            buildTree("consts", 0);
        }
    }

    private void Const(){
        Name();
        read("Equal");
        ConstValue();
        buildTree("const", 2);
    }

    private void ConstValue(){
        if(nextToken.getTokenName().equals("Integer")){
            buildTree(nextToken.getValue(), 0);
            buildTree("<integer>", 1);
            nextToken = WinZigScreener.getNextToken();
        } else if(nextToken.getTokenName().equals("Char")){
            buildTree(nextToken.getValue(), 0);
            buildTree("<char>", 1);
            nextToken = WinZigScreener.getNextToken();
        } else if(nextToken.getTokenName().equals("Identifier")){
            Name();
        } else{
            System.out.println("Error :- Unexpected token " + nextToken.getValue());
            throw new Error();
        }
    }

    private void Types(){
        if (nextToken.getTokenName().equals("Type Definition")) {
            read("Type definition");
            Type();
            read("Semi colon");
            int n = 1;
            while (nextToken.getTokenName().equals("Identifier")) {
                Type();
                read("Semi colon");
                n++;
            }
            buildTree("types", n);
        } else {
            buildTree("types", 0);
        }
    }

    private void Type(){
        Name();
        read("Equal");
        LitList();
        buildTree("type", 2);
    }

    private void LitList(){
        read("Opening bracket");
        Name();
        int n = 1;
        while (nextToken.getTokenName().equals("Comma")) {
            read("Comma");
            Name();
            n++;
        }
        buildTree("lit", n);
        read("Closing bracket");
    }

    private void Dclns(){
        if (nextToken.getTokenName().equals("Variable")) {
            read("Variable");
            Dcln();
            read("Semi colon");
            int n = 1;
            while (nextToken.getTokenName().equals("Identifier")) {
                Dcln();
                read("Semi colon");
                n++;
            }
            buildTree("dclns", n);
        } else {
            buildTree("dclns", 0);
        }
    }

    private void Dcln(){
        Name();
        int n = 1;
        while (nextToken.getTokenName().equals("Comma")) {
            read("Comma");
            Name();
            n++;
        }
        read("Colon");
        Name();
        buildTree("var", n+1);
    }

    private void SubProgs(){
        int n = 0;
        while (nextToken.getTokenName().equals("Function definition")) {
            Fcn();
            n++;
        }
        buildTree("subprogs", n);
    }

    private void Fcn(){
        read("Function definition");
        Name();
        read("Opening bracket");
        Params();
        read("Closing bracket");
        read("Colon");
        Name();
        read("Semi colon");
        Consts();
        Types();
        Dclns();
        Body();
        Name();
        read("Semi colon");
    }

    private void Params(){
        Dcln();
        int n = 1;
        while (nextToken.getTokenName().equals("Semi colon")) {
            read("Semi colon");
            Dcln();
            n++;
        }
        buildTree("params", n);
    }

    private void Body(){
        read("Block start");
        Statement();
        int n = 1;
        while (nextToken.getTokenName().equals("Semi colon")) {
            read("Semi colon");
            Statement();
            n++;
        }
        read("Block end");
        buildTree("block", n);
    }

    private void Statement() {
        int n;
        if(nextToken.getTokenName().equals("Identifier")){
            Assignment();
        } else if(nextToken.getTokenName().equals("Output")){
            read("Output");
            read("Opening bracket");
            OutExp();
            n = 1;
            while (nextToken.getTokenName().equals("Comma")) {
                read("Comma");
                OutExp();
                n++;
            }
            read("Closing bracket");
            buildTree("output", n);
        } else if(nextToken.getTokenName().equals("Read identifier")){
            read("Read identifier");
            read("Opening bracket");
            Name();
            n = 1;
            while (nextToken.getTokenName().equals("Comma")) {
                read("Comma");
                Name();
                n++;
            }
            read("Closing bracket");
            buildTree("read", n);
        } else if(nextToken.getTokenName().equals("Function return")){
            read("Function return");
            Expression();
            buildTree("return", 1);
        } else if(nextToken.getTokenName().equals("Block start")){
            Body();
        } else if(nextToken.getTokenName().equals("Keyword")){
            if(nextToken.getValue().equals("if")){
                read("if");
                Expression();
                read("then");
                Statement();
                n = 0;
                if (nextToken.getTokenName().equals("Keyword") && nextToken.getValue().equals("else")) {
                    read("else");
                    Statement();
                    n++;
                }
                buildTree("if", n + 2);
            } else if(nextToken.getValue().equals("while")){
                read("while");
                Expression();
                read("do");
                Statement();
                buildTree("while", 2);
            } else if(nextToken.getValue().equals("repeat")){
                read("repeat");
                Statement();
                n = 1;
                while (nextToken.getTokenName().equals("Semi colon")) {
                    read("Semi colon");
                    Statement();
                    n++;
                }
                read("until");
                Expression();
                buildTree("repeat", n + 1);
            } else if(nextToken.getValue().equals("for")){
                read("for");
                read("Opening bracket");
                ForStat();
                read("Semi colon");
                ForExp();
                read("Semi colon");
                ForStat();
                read("Closing bracket");
                Statement();
                buildTree("for", 4);
            } else if(nextToken.getValue().equals("loop")){
                Statement();
                n = 1;
                while (nextToken.getTokenName().equals("Semi colon")) {
                    read("Semi colon");
                    Statement();
                    n++;
                }
                read("pool");
                buildTree("loop", n);
            } else if(nextToken.getValue().equals("case")){
                read("case");
                Expression();
                read("of");
                n = Caseclauses() + OtherwiseClause();
                read("Block end");
                buildTree("case", n + 1);
            } else if(nextToken.getValue().equals("exit")){
                read("exit");
                buildTree("exit", 0);
            }

        } else {
            buildTree("<null>", 0);
        }
    }

    private void Expression(){
        Term();
        if (Arrays.asList(
                "Less than or equal",
                "Less than",
                "Equal",
                "Greater than or equal",
                "Greater than",
                "Not equal"
        ).contains(nextToken.getTokenName())) {
            switch (nextToken.getTokenName()) {
                case "Less than or equal":
                    read("Less than or equal");
                    Term();
                    buildTree("<=", 2);
                    break;
                case "Less than":
                    read("Less than");
                    Term();
                    buildTree("<", 2);
                    break;
                case "Equal":
                    read("Equal");
                    Term();
                    buildTree("=", 2);
                    break;
                case "Greater than or equal":
                    read("Greater than or equal");
                    Term();
                    buildTree(">=", 2);
                    break;
                case "Greater than":
                    read("Greater than");
                    Term();
                    buildTree(">", 2);
                    break;
                case "Not equal":
                    read("Not equal");
                    Term();
                    buildTree("<>", 2);
                    break;
            }
        }
    }

    private void Term(){
        Factor();
        while (Arrays.asList("Add", "Subtract", "Or").contains(nextToken.getTokenName())) {
            switch (nextToken.getTokenName()) {
                case "Add":
                    read("Add");
                    Factor();
                    buildTree("+", 2);
                    break;
                case "Subtract":
                    read("Subtract");
                    Factor();
                    buildTree("-", 2);
                    break;
                case "Or":
                    read("Or");
                    Factor();
                    buildTree("or", 2);
                    break;
            }
        }
    }

    private void Factor(){
        Primary();
        while (Arrays.asList("Multiply", "Divide", "And", "Mod").contains(nextToken.getTokenName())) {
            switch (nextToken.getTokenName()) {
                case "Multiply":
                    read("Multiply");
                    Primary();
                    buildTree("*", 2);
                    break;
                case "Divide":
                    read("Divide");
                    Primary();
                    buildTree("/", 2);
                    break;
                case "And":
                    read("And");
                    Primary();
                    buildTree("and", 2);
                    break;
                case "Mod":
                    read("Mod");
                    Primary();
                    buildTree("mod", 2);
                    break;
            }
        }
    }

    private void Primary(){
        switch (nextToken.getTokenName()) {
            case "Subtract":
                read("Subtract");
                Primary();
                buildTree("-", 1);
                break;
            case "Add":
                read("Add");
                Primary();
                break;
            case "Not":
                read("Not");
                Primary();
                buildTree("not", 1);
                break;
            case "EOF":
                read("EOF");
                buildTree("eof", 0);
                break;
            case "Identifier":
                Name();
                if (nextToken.getTokenName().equals("Opening bracket")) {
                    read("Opening bracket");
                    Expression();
                    int n = 1;
                    while (nextToken.getTokenName().equals("Comma")) {
                        read("Comma");
                        Expression();
                        n++;
                    }
                    read("Closing bracket");
                    buildTree("call", n + 1);
                }
                break;
            case "Integer":
                buildTree(nextToken.getValue(), 0);
                buildTree("<integer>", 1);
                nextToken = WinZigScreener.getNextToken();
                break;
            case "Char":
                buildTree(nextToken.getValue(), 0);
                buildTree("<char>", 1);
                nextToken = WinZigScreener.getNextToken();
                break;
            case "Opening bracket":
                read("Opening bracket");
                Expression();
                read("Closing bracket");
                break;
            case "Succ":
                read("Succ");
                read("Opening bracket");
                Expression();
                read("Closing bracket");
                buildTree("succ", 1);
                break;
            case "Pred":
                read("pred");
                read("Opening bracket");
                Expression();
                read("Closing bracket");
                buildTree("pred", 1);
                break;
            case "Chr":
                read("chr");
                read("Opening bracket");
                Expression();
                read("Closing bracket");
                buildTree("chr", 1);
                break;
            default:
                System.out.println("Error: unexpected token " + nextToken.getTokenName());
                throw new Error();
        }
    }

    private void Assignment(){
        Name();
        if(nextToken.getTokenName().equals("Assignment")){
            read("Assignment");
            Expression();
            buildTree("assign", 2);
        } else if(nextToken.getTokenName().equals("Swap")){
            read("Swap");
            Name();
            buildTree("swap", 2);
        } else{
            System.out.println("Error: unexpected token " + nextToken.getTokenName());
            throw new Error();
        }
    }

    private void OutExp(){
        if (nextToken.getTokenName().equals("String")) {
            StringNode();
            buildTree("string", 1);
        } else {
            Expression();
            buildTree("integer", 1);
        }
    }

    private void ForStat(){
        if (nextToken.getTokenName().equals("Identifier")) {
            Assignment();
        } else {
            buildTree("<null>", 0);
        }
    }

    private void ForExp(){
        if (Arrays.asList(
                "Subtract",
                "Add",
                "not",
                "EOF",
                "Identifier",
                "Keyword",
                "Char",
                "Opening bracket",
                "Succ",
                "Pred",
                "Chr",
                "Ord"
        ).contains(nextToken.getTokenName())) {
            Expression();
        } else {
            buildTree("true", 0);
        }
    }

    private void StringNode(){
        buildTree(nextToken.getValue(), 0);
        buildTree("<string>", 1);
        nextToken = WinZigScreener.getNextToken();
    }

    private int Caseclauses(){
        Caseclause();
        read("Semi colon");
        int n = 1;
        while (Arrays.asList("Integer", "Char", "Identifier").contains(nextToken.getTokenName())) {
            Caseclause();
            read("Semi colon");
            n++;
        }
        return n;
    }

    private void Caseclause(){
        CaseExpression();
        int n = 1;
        while (nextToken.getTokenName().equals("Comma")) {
            read("Comma");
            CaseExpression();
            n++;
        }
        read("Colon");
        Statement();
        buildTree("case_clause", n + 1);
    }

    private void CaseExpression(){
        ConstValue();
        if (nextToken.getTokenName().equals("Case dots")) {
            read("Case dots");
            ConstValue();
            buildTree("..", 2);
        }
    }

    private int OtherwiseClause(){
        if (nextToken.getTokenName().equals("Keyword") && nextToken.getValue().equals("otherwise")) {
            read("otherwise");
            Statement();
            buildTree("otherwise", 1);
            return 1;
        }
        return 0;
    }

}
