package org.sky.vader;

abstract class ExpressionTree {

    public ExpressionTree(ExpressionTree left, ExpressionTree right) {
        this.left = left;
        this.right = right;
    }

    private ExpressionTree left;
    private ExpressionTree right;

    protected abstract int priority();

    public ExpressionTree getLeft(){
        return left;
    }

    public ExpressionTree getRight(){
        return right;
    }

    public void setLeft(ExpressionTree l){
        left = l;
    }

    public void setRight(ExpressionTree r){
        right = r;
    }

    public static final class Expression extends ExpressionTree{
        private Operation operation;

        public Expression(Operation operation, ExpressionTree left, ExpressionTree right) {
            super(left, right);
            this.operation = operation;
        }

        public Expression(Operation operation, int left, int right) {
            super(Scalar.of(left), Scalar.of(right));
            this.operation = operation;
        }

        public Expression(Operation operation, Expression expression, int right) {
            super(expression, Scalar.of(right));
            this.operation = operation;
        }

        public Expression(Operation operation, int left, Expression expression) {
            super(Scalar.of(left), expression);
            this.operation = operation;
        }

        @Override
        protected int priority() {
            return operation.priority;
        }

        @Override
        protected String type() {
            return "expression";
        }

        private String leftWrap(ExpressionTree exp){
            String str = "";
            if(exp.priority() < operation.priority){
                str = "(" + exp + ")";
            } else {
                str = exp + " ";
            }
            return str;

        }

        private String rightWrap(ExpressionTree node){
            String str = "";
            if(node.priority() < operation.priority ||
                    (operation.leftAssoc && isExpression(node))){
                str = "(" + node + ")";
            } else {
                str = " " + node;
            }
            return str;
        }

        private boolean isExpression(ExpressionTree node) {
            return node.type().equals(this.type());
        }

        @Override
        public String toString() {
            return leftWrap(getLeft()) + operation + rightWrap(getRight());
        }
    }

    protected abstract String type();

    public static final class Scalar extends ExpressionTree{

        private Integer value;

        private Scalar(Integer value) {
            super(null, null);
            this.value = value;
        }

        public static Scalar of(Integer value){
            return new Scalar(value);
        }

        @Override
        protected int priority() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected String type() {
            return "scalar";
        }

        @Override
        public String toString() {
            return value.toString();
        }

        public int value(){
            return value;
        };
    }
}
