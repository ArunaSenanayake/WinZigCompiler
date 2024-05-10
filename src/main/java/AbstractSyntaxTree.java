public class AbstractSyntaxTree {
    TreeNode rootNode;

    public AbstractSyntaxTree(TreeNode rootNode){
        this.rootNode = rootNode;
    }

    public void printAST(){
        this.traverse(rootNode, 0);
    }

    private void traverse(TreeNode node, int n){
        if(node != null){
            for(int i=0; i<n; i++){
                System.out.print(". ");
            }
            System.out.println(node.getData() + "(" + node.getN() + ")");
            this.traverse(node.getLeftChild(), n+1);
            this.traverse(node.getRightChild(), n);
        }
    }
}
