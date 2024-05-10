public class TreeNode {
    private String data;
    private TreeNode leftChild;
    private TreeNode rightChild;
    private int n;

    private String codeL;
    private int nextL;
    private int topL;
    private String codeR;
    private int nextR;
    private int topR;

    public TreeNode(String data, TreeNode leftChild, TreeNode rightChild, int n) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.n = n;
    }

    public String getData() {
        return data;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public int getN() {
        return n;
    }

    public String getCodeL() {
        return codeL;
    }

    public int getNextL() {
        return nextL;
    }

    public int getTopL() {
        return topL;
    }

    public String getCodeR() {
        return codeR;
    }

    public int getNextR() {
        return nextR;
    }

    public int getTopR() {
        return topR;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public void setCodeL(String codeL) {
        this.codeL = codeL;
    }

    public void setNextL(int nextL) {
        this.nextL = nextL;
    }

    public void setTopL(int topL) {
        this.topL = topL;
    }

    public void setCodeR(String codeR) {
        this.codeR = codeR;
    }

    public void setNextR(int nextR) {
        this.nextR = nextR;
    }

    public void setTopR(int topR) {
        this.topR = topR;
    }
}
