import java.util.ArrayList;

public class WinZigScreener {
    public static ArrayList<Token> screenedTokens = new ArrayList<>();
    public static int currentTokenIndex = 0;

    public static ArrayList<Token> screen(ArrayList<Token> scannedTokens){
        for(int i=0; i<scannedTokens.size(); i++){
            if(scannedTokens.get(i).getTokenName().equals("Whitespace") || scannedTokens.get(i).getTokenName().equals("Type 1 Comment") || scannedTokens.get(i).getTokenName().equals("Type 2 Comment")){
                continue;
            }

            if(scannedTokens.get(i).getTokenName().equals("New line")){
                //screenedTokens.add(scannedTokens.get(i));
            } else if(scannedTokens.get(i).getTokenName().equals("Identifier")){
                if(scannedTokens.get(i).getValue().equals("program")){
                    screenedTokens.add(new Token("Program", "program"));
                } else if(scannedTokens.get(i).getValue().equals("var")){
                    screenedTokens.add(new Token("Variable", "var"));
                } else if(scannedTokens.get(i).getValue().equals("const")){
                    screenedTokens.add(new Token("Constant", "const"));
                } else if(scannedTokens.get(i).getValue().equals("type")){
                    screenedTokens.add(new Token("Type definition", "type"));
                } else if(scannedTokens.get(i).getValue().equals("function")){
                    screenedTokens.add(new Token("Function definition", "function"));
                } else if(scannedTokens.get(i).getValue().equals("retunr")){
                    screenedTokens.add(new Token("Function return", "return"));
                } else if(scannedTokens.get(i).getValue().equals("begin")){
                    screenedTokens.add(new Token("Block start", "begin"));
                } else if(scannedTokens.get(i).getValue().equals("end")){
                    screenedTokens.add(new Token("Block end", "send"));
                } else if(scannedTokens.get(i).getValue().equals("output")){
                    screenedTokens.add(new Token("Output", "output"));
                } else if(scannedTokens.get(i).getValue().equals("if")){
                    screenedTokens.add(new Token("Keyword", "if"));
                } else if(scannedTokens.get(i).getValue().equals("then")){
                    screenedTokens.add(new Token("Keyword", "then"));
                } else if(scannedTokens.get(i).getValue().equals("else")){
                    screenedTokens.add(new Token("Keyword", "else"));
                } else if(scannedTokens.get(i).getValue().equals("while")){
                    screenedTokens.add(new Token("Keyword", "while"));
                } else if(scannedTokens.get(i).getValue().equals("do")){
                    screenedTokens.add(new Token("Keyword", "do"));
                } else if(scannedTokens.get(i).getValue().equals("case")){
                    screenedTokens.add(new Token("Keyword", "case"));
                } else if(scannedTokens.get(i).getValue().equals("of")){
                    screenedTokens.add(new Token("Keyword", "of"));
                } else if(scannedTokens.get(i).getValue().equals("..")){
                    screenedTokens.add(new Token("Case dots", ".."));
                } else if(scannedTokens.get(i).getValue().equals("otherwise")){
                    screenedTokens.add(new Token("Keyword", "otherwise"));
                } else if(scannedTokens.get(i).getValue().equals("repeat")){
                    screenedTokens.add(new Token("Keyword", "repeat"));
                } else if(scannedTokens.get(i).getValue().equals("for")){
                    screenedTokens.add(new Token("Keyword", "for"));
                } else if(scannedTokens.get(i).getValue().equals("until")){
                    screenedTokens.add(new Token("Keyword", "until"));
                } else if(scannedTokens.get(i).getValue().equals("loop")){
                    screenedTokens.add(new Token("Keyword", "loop"));
                } else if(scannedTokens.get(i).getValue().equals("pool")){
                    screenedTokens.add(new Token("Keyword", "pool"));
                } else if(scannedTokens.get(i).getValue().equals("exit")){
                    screenedTokens.add(new Token("Keyword", "exit"));
                } else if(scannedTokens.get(i).getValue().equals("mod")){
                    screenedTokens.add(new Token("Mod", "mod"));
                } else if(scannedTokens.get(i).getValue().equals("and")){
                    screenedTokens.add(new Token("And", "and"));
                } else if(scannedTokens.get(i).getValue().equals("or")){
                    screenedTokens.add(new Token("Or", "or"));
                } else if(scannedTokens.get(i).getValue().equals("not")){
                    screenedTokens.add(new Token("Not", "not"));
                } else if(scannedTokens.get(i).getValue().equals("read")){
                    screenedTokens.add(new Token("Read identifier", "read"));
                } else if(scannedTokens.get(i).getValue().equals("succ")){
                    screenedTokens.add(new Token("Succ", "succ"));
                } else if(scannedTokens.get(i).getValue().equals("pred")){
                    screenedTokens.add(new Token("Pred", "pred"));
                } else if(scannedTokens.get(i).getValue().equals("chr")){
                    screenedTokens.add(new Token("Chr", "chr"));
                } else if(scannedTokens.get(i).getValue().equals("ord")){
                    screenedTokens.add(new Token("Ord", "ord"));
                } else{
                    screenedTokens.add(scannedTokens.get(i));
                }
            } else if(scannedTokens.get(i).getTokenName().equals("EOP")){
                screenedTokens.add(new Token("EOF", null));
            } else{
                screenedTokens.add(scannedTokens.get(i));
            }
        }
        return screenedTokens;
    }

    public static Token getNextToken(){
        if(currentTokenIndex < screenedTokens.size()){
            Token token = screenedTokens.get(currentTokenIndex);
            currentTokenIndex++;
            return token;
        } else{
            return null;
        }
    }
}
