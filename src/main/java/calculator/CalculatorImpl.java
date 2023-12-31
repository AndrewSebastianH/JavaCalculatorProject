package calculator;

import java.util.Stack;

public class CalculatorImpl implements Calculator {

	@Override
	public double calculate(String formula) {
		formula = formula.replaceAll("\\s", "");
		if (formula.isEmpty()) {
			throw new IllegalArgumentException("Formula is empty");
		}

		Stack<Double> operands = new Stack<>();
		Stack<Character> operators = new Stack<>();

		for (int i = 0; i < formula.length(); i++) {
			char currentChar = formula.charAt(i);

			if (Character.isDigit(currentChar)
					|| (currentChar == '-' && (i == 0 || (!Character.isDigit(formula.charAt(i - 1))
							&& formula.charAt(i - 1) != '.' && formula.charAt(i - 1) != ')')))) {
				// If the current character is a digit or a negative sign not in the middle of a
				// number

				StringBuilder numBuilder = new StringBuilder();
				numBuilder.append(currentChar);

				// Continue to append digits and decimal points if present
				while (i + 1 < formula.length()
						&& (Character.isDigit(formula.charAt(i + 1)) || formula.charAt(i + 1) == '.')) {
					numBuilder.append(formula.charAt(++i));

				}

				operands.push(Double.parseDouble(numBuilder.toString()));
			} else if (currentChar == '(') {
				if (i > 0 && Character.isDigit(formula.charAt(i - 1))) {
					// If the previous character is a digit, add a multiplication operator
					operators.push('*');
				}
				operators.push(currentChar);
			} else if (currentChar == ')') {
				// Throw exception if there are unused closing bracket
				if (operators.isEmpty()) {
					throw new IllegalArgumentException("Invalid bracket.");

				}
				while (!operators.isEmpty() && operators.peek() != '(') {
					evaluate(operands, operators);
				}
				operators.pop(); // Remove the '(' from the stack

				if (i + 1 < formula.length() && Character.isDigit(formula.charAt(i + 1))) {
					// If the next character is a digit, add a multiplication operator
					operators.push('*');
				}

			} else if (isOperator(currentChar)) {
				if (i > 0 && currentChar == '√' && Character.isDigit(formula.charAt(i - 1))) {
					// If the previous character is a digit, add a multiplication operator
					operators.push('*');
				}
				if (i == 0 && (currentChar == '+' || currentChar == '/' || currentChar == '*' || currentChar == '.'
						|| currentChar == '^')) {
					throw new IllegalArgumentException("Invalid operator position");

				}
				while (!operators.isEmpty() && hasHigherOrEqualPrecedence(operators.peek(), currentChar)) {
					evaluate(operands, operators);
				}
				operators.push(currentChar);
			}

		}

		while (!operators.isEmpty()) {
			// Throw exception if there are still not closed brackets
			if (operators.contains('(')) {
				throw new IllegalArgumentException("Brackets not closed.");
			}
			evaluate(operands, operators);

		}
		return operands.pop();
	}

	private void evaluate(Stack<Double> operands, Stack<Character> operators) {
		char operator = operators.pop();

		double operand2 = operands.pop();
		double operand1 = (operator != '√') ? operands.pop() : 0;

		double result = applyOperator(operand1, operand2, operator);
		operands.push(result);
	}

	private boolean isOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == '√';
	}

	private int getPrecedence(char operator) {
		if (operator == '+' || operator == '-') {
			return 1;
		} else if (operator == '*' || operator == '/') {
			return 2;
		} else if (operator == '^' || operator == '√') {
			return 3;
		} else {
			return 0; // Default precedence for unknown operators
		}
	}

	private boolean hasHigherOrEqualPrecedence(char op1, char op2) {
		return getPrecedence(op1) >= getPrecedence(op2);
	}

	private double applyOperator(double operand1, double operand2, char operator) {

		switch (operator) {
		case '+':
			return operand1 + operand2;
		case '-':
			return operand1 - operand2;
		case '*':
			return operand1 * operand2;
		case '/':
			if (operand2 == 0) {
				throw new ArithmeticException("Division by zero");
			}
			return operand1 / operand2;
		case '^':
			return Math.pow(operand1, operand2);
		case '√':
			return Math.sqrt(operand2);
		default:
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}

}
