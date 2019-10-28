package org.sky.vader;

enum Operation {
    PLUS(1, false){
        @Override
        public String toString() {
            return "+";
        }
    },
    MINUS(1, true){
        @Override
        public String toString() {
            return "-";
        }
    },
    DIVIDE(3, true){
        @Override
        public String toString() {
            return "/";
        }
    },
    MULTIPLE(3, false) {
        @Override
        public String toString() {
            return "*";
        }
    };

    Operation(int priority, boolean leftAssoc) {
        this.priority = priority;
        this.leftAssoc = leftAssoc;
    }

    public boolean leftAssoc;

    public int priority;

    public abstract String toString();
}
