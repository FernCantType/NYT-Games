import java.util.*;

class Node {
        private Node parent;
        private String value;
        private ArrayList<Node> children;

        public Node(Node parent, String value) {
            this.parent = parent;
            this.value = value;
            this.children = new ArrayList<>();
            if (parent != null) {
                parent.addChild(this);
            }
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public String getValue() {
            return value;
        }

        public void addChild(Node child) {
            children.add(child);
            child.setParent(this);
        }

        public List<Node> getChildren() {
            return children;
        }
    }
