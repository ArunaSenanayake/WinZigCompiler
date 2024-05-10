import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WinZigScanner {
    static Scanner scanner = null;
    static int startIndex = 0;
    static int currentIndex = 0;
    static String contents = "";
    static ArrayList<Character> allLetters= new ArrayList<>(Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'T', 'S', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '_'
    ));
    static ArrayList<Character> allDigits= new ArrayList<>(Arrays.asList(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    ));
    static ArrayList<Character> allWhiteSpaceCharacters= new ArrayList<>(Arrays.asList(
            ' ', '\t', '\r', '\f'
    ));

    public static ArrayList<Token> scan(String fileName){
        ArrayList<Token> tokens = new ArrayList<>();
        try {
            File programFile = new File("C:\\Users\\Hp\\Documents\\Java Projects\\WinZig Compiler\\src\\main\\resources\\winzig_test_programs\\" + fileName);
            scanner = new Scanner(programFile);
            while(scanner.hasNextLine()){
                contents += scanner.nextLine();
                if(scanner.hasNextLine()){
                    contents += '\n';
                }
            }
            //System.out.println(contents);

            currentIndex = startIndex;
            while(currentIndex<contents.length()){
                char currentChar = contents.charAt(currentIndex);

                if(contents.charAt(currentIndex) == '.'){
                    tokens.add(new Token("Dot", "."));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                }

                if(allLetters.contains(currentChar)){
                    currentIndex++;
                    while(allLetters.contains(contents.charAt(currentIndex)) || allDigits.contains(contents.charAt(currentIndex))){
                        currentIndex++;
                    }
                    tokens.add(new Token("Identifier", contents.substring(startIndex, currentIndex)));
                    startIndex = currentIndex;
                }

                if(allDigits.contains(currentChar)){
                    currentIndex++;
                    while(allDigits.contains(contents.charAt(currentIndex))){
                        currentIndex++;
                    }
                    tokens.add(new Token("Integer", contents.substring(startIndex, currentIndex)));
                    startIndex = currentIndex;
                }

                if(allWhiteSpaceCharacters.contains(currentChar)){
                    currentIndex++;
                    while(allWhiteSpaceCharacters.contains(contents.charAt(currentIndex))){
                        currentIndex++;
                    }
                    tokens.add(new Token("Whitespace", " "));
                    startIndex = currentIndex;
                }

                if(contents.charAt(currentIndex) == '\''){
                    currentIndex++;
                    if(contents.charAt(currentIndex) == '\''){
                        tokens.add(new Token("Char", ""));
                        currentIndex++;
                        startIndex = currentIndex;
                    }
                    currentIndex++;
                    if(contents.charAt(currentIndex) == '\''){
                        tokens.add(new Token("Char", contents.substring(startIndex + 1, currentIndex)));
                        currentIndex++;
                        startIndex = currentIndex;
                    }
                }

                if(contents.charAt(currentIndex) == '\"'){
                    currentIndex++;
                    while(contents.charAt(currentIndex) != '\"'){
                        currentIndex++;
                    }
                    tokens.add(new Token("String", contents.substring(startIndex + 1, currentIndex)));
                    currentIndex++;
                    startIndex = currentIndex;
                }

                if(contents.charAt(currentIndex) == '#'){
                    currentIndex++;
                    while(contents.charAt(currentIndex) != '\n'){
                        currentIndex++;
                    }
                    tokens.add(new Token("Type 1 Comment", contents.substring(startIndex + 1, currentIndex)));
                    currentIndex++;
                    startIndex = currentIndex;
                }

                if(contents.charAt(currentIndex) == '{'){
                    currentIndex++;
                    while(contents.charAt(currentIndex) != '}'){
                        currentIndex++;
                    }
                    tokens.add(new Token("Type 2 Comment", contents.substring(startIndex + 1, currentIndex)));
                    currentIndex++;
                    startIndex = currentIndex;
                }

                if(":=:".equals(contents.substring(currentIndex, currentIndex + 3))){
                    tokens.add(new Token("Swap", ":=:"));
                    currentIndex = currentIndex + 3;
                    startIndex = currentIndex;
                }

                if(":=".equals(contents.substring(currentIndex, currentIndex + 2))){
                    tokens.add(new Token("Assignment", ":="));
                    currentIndex = currentIndex + 2;
                    startIndex = currentIndex;
                } else if("..".equals(contents.substring(currentIndex, currentIndex + 2))){
                    tokens.add(new Token("Case", ".."));
                    currentIndex = currentIndex + 2;
                    startIndex = currentIndex;
                } else if("<=".equals(contents.substring(currentIndex, currentIndex + 2))){
                    tokens.add(new Token("Less than or equal", "<="));
                    currentIndex = currentIndex + 2;
                    startIndex = currentIndex;
                } else if("<>".equals(contents.substring(currentIndex, currentIndex + 2))){
                    tokens.add(new Token("Not equal", "<>"));
                    currentIndex = currentIndex + 2;
                    startIndex = currentIndex;
                } else if(">=".equals(contents.substring(currentIndex, currentIndex + 2))){
                    tokens.add(new Token("Greater than or equal", ">="));
                    currentIndex = currentIndex + 2;
                    startIndex = currentIndex;
                }

                if(contents.charAt(currentIndex) == '('){
                    tokens.add(new Token("Opening bracket", "("));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == ')'){
                    tokens.add(new Token("Closing bracket", ")"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '<'){
                    tokens.add(new Token("Less than", "<"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '='){
                    tokens.add(new Token("Equal", "="));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == ':'){
                    tokens.add(new Token("Colon", ":"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == ';'){
                    tokens.add(new Token("Semi colon", ";"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '.'){
                    tokens.add(new Token("Dot", "."));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == ','){
                    tokens.add(new Token("Comma", ","));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '>'){
                    tokens.add(new Token("Greater than", ">"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '+'){
                    tokens.add(new Token("Add", "+"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '-'){
                    tokens.add(new Token("Subtract", "-"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '*'){
                    tokens.add(new Token("Multiply", "*"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                } else if(contents.charAt(currentIndex) == '/'){
                    tokens.add(new Token("Divide", "/"));
                    currentIndex = currentIndex + 1;
                    startIndex = currentIndex;
                }

                if(contents.charAt(currentIndex) == '\n'){
                    tokens.add(new Token("New line", ""));
                    currentIndex++;
                    startIndex = currentIndex;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } finally {
            scanner.close();
            tokens.add(new Token("EOP", null));
            return tokens;
        }
    }

    private static char getCurrentChar(){
        if(currentIndex < contents.length()){
            return contents.charAt(currentIndex);
        }
        return '\0';
    }
}
