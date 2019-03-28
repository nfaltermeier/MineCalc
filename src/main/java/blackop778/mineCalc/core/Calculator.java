package blackop778.mineCalc.core;

import blackop778.mineCalc.core.CalcExceptions.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Calculator {
    @Nonnull
    public static List<IBinaryOperation> operations = Operations.addOperations(new ArrayList<IBinaryOperation>());
    public static Double consoleLastOutput = Double.NaN;

    public static double evaluate(String math, boolean useOOPS, Double last, boolean fancyRemainders)
            throws CalcExceptions {
        // Remove spaces from input
        math = math.replaceAll("\\s", "");
        math = math.toLowerCase();

        HashMap<String, IBinaryOperation> operators = new HashMap<String, IBinaryOperation>();
        for (IBinaryOperation op : operations)
        {
            for (String s : op.getOperators())
            {
                operators.put(s, op);
            }
        }

        ArrayList<String> parts = splitMath(math, operators);
        Queue<String> rpn = infixToRPN(parts, operators);
        return evaluateRPN(rpn, operators, last, fancyRemainders);
    }

    private static double getDoubleValue(String number, @Nullable Double last)
            throws PreviousOutputException, MultiplePointsException, InvalidNumberException, UsageException {
        boolean negative = false;
        double toReturn;
        if (number.startsWith("-")) {
            negative = true;
            number = number.substring(1);
        }

        if (number.equalsIgnoreCase("pi")) {
            toReturn = Math.PI;
        } else if (number.equalsIgnoreCase("e")) {
            toReturn = Math.E;
        } else if (number.equalsIgnoreCase("l")) {
            if (last == null || last.equals(Double.NaN))
                throw new PreviousOutputException();
            else {
                toReturn = last;
            }
        } else if (number.equals(""))
            throw new UsageException();
        else {
            try {
                toReturn = Double.valueOf(number);
            } catch (NumberFormatException e) {
                if (e.getMessage().equals("multiple points"))
                    throw new MultiplePointsException();
                else
                    throw new InvalidNumberException(" " + number);
            }
        }

        return negative ? -toReturn : toReturn;
    }

    private static ArrayList<String> splitMath(String math, HashMap<String, IBinaryOperation> operators) throws UsageException {
        Matcher splitter = Pattern.compile("-?([0-9.]+|l|pi|e)|\\(|\\)").matcher(math);
        ArrayList<String> parts = new ArrayList<String>();
        int lastGroupEnd = 0;
        while (splitter.find())
        {
            int start = splitter.start();
            parts.add(math.substring(lastGroupEnd, start));

            int end = splitter.end();
            String number = math.substring(start, end);
            // if the minus in the number should actually be a minus operator instead of unary negation operator
            if (number.charAt(0) == '-' && parts.size() != 1 && !operators.containsKey(parts.get(parts.size() - 1)))
            {
                parts.add("-");
                number = number.substring(1);
            }
            parts.add(number);

            lastGroupEnd = end;
        }

        // The last operator doesn't have two operands
        if (lastGroupEnd != math.length())
            throw new UsageException();

        // Remove empty strings
        Iterator<String> partIterator = parts.iterator();
        while (partIterator.hasNext())
        {
            String part = partIterator.next();

            if (part.equals(""))
                partIterator.remove();
        }

        return parts;
    }

    private static Queue<String> infixToRPN(ArrayList<String> tokens, HashMap<String, IBinaryOperation> operators) throws ParenthesisException, UsageException {
        Stack<String> stack = new Stack<String>();
        Queue<String> result = new ArrayDeque<String>();

        for (String token : tokens)
        {
            if (token.matches("-?([0-9.]+|l|pi|e)"))
                result.add(token);
            else if (operators.containsKey(token))
            {
                IBinaryOperation op = operators.get(token);
                while (stack.size() > 0 && !stack.peek().equals("("))
                {
                    IBinaryOperation stkCur = operators.get(stack.peek());
                    int precedenceDiff = stkCur.getPrecedence() - op.getPrecedence();
                    if ( precedenceDiff > 0 ||
                            // operator on stack has equal precedence and is left associative
                            precedenceDiff == 0 && ((IBinaryOperation) stkCur).getAssociativity() == IBinaryOperation.Associativity.Left)
                        result.add(stack.pop());
                    else
                        break;
                }

                stack.add(token);
            }
            else if (token.equals("("))
                stack.push(token);
            else if (token.equals(")"))
            {
                while (stack.size() > 0 && !stack.peek().equals("("))
                {
                    result.add(stack.pop());
                }

                if (stack.size() == 0 || !stack.pop().equals("("))
                    throw new ParenthesisException(true);
            }
            else
            {
                throw new UsageException();
            }
        }

        while (stack.size() > 0)
        {
            String cur = stack.pop();

            if (cur.equals("("))
                throw new ParenthesisException(true);

            result.add(cur);
        }

        return result;
    }

    private static double evaluateRPN(Queue<String> rpn, HashMap<String, IBinaryOperation> operators, Double lastOutput,
                                      boolean fancyRemainders) throws CalcExceptions {
        Stack<Double> numbers = new Stack<Double>();

       for (Iterator<String> iterator = rpn.iterator(); iterator.hasNext(); ) {
            String part = iterator.next();
            if (operators.containsKey(part)) {
                IBinaryOperation operator = operators.get(part);
                double operand2 = numbers.pop();
                double operand1 = numbers.pop();

                if (fancyRemainders && operator instanceof Operations.Modulus && !iterator.hasNext())
                    throw new FancyRemainderException(operand1, operand2);

                numbers.push(operator.evaluateFunction(operand1, operand2));
            } else {
                numbers.push(getDoubleValue(part, lastOutput));
            }
        }

        // Something went wrong
        if (numbers.size() > 1)
            throw new UsageException();

        return numbers.peek();
    }
}
