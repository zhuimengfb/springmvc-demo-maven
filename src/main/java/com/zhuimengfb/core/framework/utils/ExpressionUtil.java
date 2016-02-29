package com.zhuimengfb.core.framework.utils;

/**
 * Created by bo on 2016/2/28.
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionUtil {
    public double eval(String exp) {
        List<String> list = infixExpToPostExp(exp);
        return doEval(list);
    }

    private double doEval(List<String> list) {
        Stack<String> stack = new Stack();
        try {
            for (int i = 0; i < list.size(); i++) {
                String element = (String) list.get(i);
                if (isOperator(element)) {
                    double n1 = Double.parseDouble((String) stack.pop());
                    double n2 = Double.parseDouble((String) stack.pop());
                    double result = doOperate(n2, n1, element);
                    stack.push(new Double(result).toString());
                } else {
                    stack.push(element);
                }
            }
            return Double.parseDouble((String) stack.pop());
        } catch (RuntimeException e) {
            throw new IllegalExpressionException(e.getMessage());
        }
    }

    private double doOperate(double n1, double n2, String operator) {
        if (operator.equals("+")) {
            return n1 + n2;
        }
        if (operator.equals("-")) {
            return n1 - n2;
        }
        if (operator.equals("*")) {
            return n1 * n2;
        }
        return n1 / n2;
    }

    private boolean isOperator(String str) {
        return (str.equals("+")) || (str.equals("-")) || (str.equals("*")) || (str.equals("/"));
    }

    private List<String> infixExpToPostExp(String exp) {
        List<String> postExp = new ArrayList();
        StringBuffer numBuffer = new StringBuffer();
        Stack<Character> opStack = new Stack();

        opStack.push(Character.valueOf('#'));
        try {
            for (int i = 0; i < exp.length(); ) {
                char ch = exp.charAt(i);
                switch (ch) {
                    case '+':
                    case '-':
                        if (i - 1 < 0) {
                            numBuffer.append(ch);
                            ch = exp.charAt(++i);
                        } else {
                            char c = exp.charAt(i - 1);
                            if (!Character.isDigit(c)) {
                                numBuffer.append(ch);
                                ch = exp.charAt(++i);
                            }
                        }
                        break;
                    case '*':
                    case '/':
                        char preChar = ((Character) opStack.peek()).charValue();
                        while (priority(preChar) >= priority(ch)) {
                            postExp.add("" + preChar);
                            opStack.pop();
                            preChar = ((Character) opStack.peek()).charValue();
                        }
                        opStack.push(Character.valueOf(ch));
                        i++;
                        break;
                    case '(':
                        opStack.push(Character.valueOf(ch));
                        i++;
                        break;
                    case ')':
                        char c = ((Character) opStack.pop()).charValue();
                        while (c != '(') {
                            postExp.add("" + c);
                            c = ((Character) opStack.pop()).charValue();
                        }
                        i++;
                        break;
                    case '#':
                        while (!opStack.isEmpty()) {
                            char c1 = ((Character) opStack.pop()).charValue();
                            if (c1 != '#') {
                                postExp.add("" + c1);
                            }
                        }
                        i++;
                        break;
                    case '\t':
                    case ' ':
                        i++;
                        break;
                }
                if (('.' == ch) || (Character.isDigit(ch))) {
                    while (Character.isDigit(ch)) {
                        numBuffer.append(ch);
                        ch = exp.charAt(++i);
                    }
                    if ('.' == ch) {
                        numBuffer.append('.');
                        ch = exp.charAt(++i);
                    } else {
                        postExp.add(numBuffer.toString());
                        numBuffer = new StringBuffer();
                    }
                } else {
                    throw new IllegalExpressionException("illegal operator");
                }
            }
        } catch (RuntimeException e) {
            int i;
            throw new IllegalExpressionException(e.getMessage());
        }
        return postExp;
    }

    private int priority(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '#':
            case '(':
                return 0;
        }
        throw new IllegalExpressionException("Illegal operator");
    }

    class IllegalExpressionException
            extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public IllegalExpressionException() {
        }

        public IllegalExpressionException(String info) {
            super();
        }
    }

    public static void main(String[] args) {
        System.out.println("AB+AB-BC+ABC*DAB+AB".replaceAll("\\bAB\\b", "1"));
        ExpressionUtil eval = new ExpressionUtil();
        double result = eval.eval("(A*8)/6+6#");
        System.out.println(result);
    }
}

