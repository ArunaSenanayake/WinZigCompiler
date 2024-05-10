public class Token {
    private String tokenName;
    private String value;

    public Token(String tokenName, String value){
        this.tokenName = tokenName;
        this.value = value;
    }

    public String getTokenName(){
        return this.tokenName;
    }

    public String getValue(){
        return this.value;
    }

    public void setTokenName(String tokenName){
        this.tokenName = tokenName;
    }

    public void setValue(String value){
        this.value = value;
    }
}
