

class NaryTree {
    private Node root;

    public NaryTree(String rootValue) {
        this.root = new Node(null, rootValue);
    }

    public Node getRoot() {
        return root;
    }
}