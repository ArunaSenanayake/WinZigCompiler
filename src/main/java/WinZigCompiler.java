import java.util.ArrayList;

public class WinZigCompiler {
    public static void main(String[] args) {
        //String fileName = args[0];
        ArrayList<Token> scannedTokens = WinZigScanner.scan("winzig_06");

        ArrayList<Token> screenedTokens = WinZigScreener.screen(scannedTokens);

        WinZigParser parser = new WinZigParser();
        AbstractSyntaxTree tree = parser.getAbstractSyntaxTree();
        tree.printAST();
    }
}
