package calculator;

import java.util.Stack;

public class CalculatorImpl implements Calculator {

	@Override
	public double calculate(String formula) {
		formula = formula.replaceAll("\\s", "");

		Stack<Double> operands = new Stack<>();
		Stack<Character> operators = new Stack<>();

		for (int i = 0; i < formula.length(); i++) {
			System.out.println("Operands: " + operands);
			System.out.println("Operators: " + operators);
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
				while (!operators.isEmpty() && operators.peek() != '(') {
					evaluate(operands, operators);
				}
				operators.pop(); // Remove the '(' from the stack
			} else if (isOperator(currentChar)) {
				while (!operators.isEmpty() && hasHigherOrEqualPrecedence(operators.peek(), currentChar)) {
					evaluate(operands, operators);
				}
				operators.push(currentChar);
			} else if (currentChar == '^') {
				operators.push(currentChar);
			} else if (currentChar == 's' && i + 3 < formula.length() && formula.substring(i, i + 4).equals("sqrt")) {
				operators.push('s');
				i += 3; // Skip 's', 'q', 'r', 't' characters
			}
		}

		while (!operators.isEmpty()) {
			System.out.println("Sblm evaluate Operands 2: " + operands);
			System.out.println("Sblm evaluate Operators 2: " + operators);

			evaluate(operands, operators);

			System.out.println("Operands 2: " + operands);
			System.out.println("Operators 2: " + operators);

		}
		System.out.println("Final Operands: " + operands);
		System.out.println("Final Operators: " + operators);
		return operands.pop();
	}

	private void evaluate(Stack<Double> operands, Stack<Character> operators) {
		char operator = operators.pop();
		if (operator == 's') {
			double operand = operands.pop();
			double result = Math.sqrt(operand);
			operands.push(result);
		} else {
			double operand2 = operands.pop();
			double operand1 = operands.pop();
			double result = applyOperator(operand1, operand2, operator);
			operands.push(result);
		}
	}

	private boolean isOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
	}

	private boolean hasHigherOrEqualPrecedence(char op1, char op2) {
		if (op1 == '*' || op1 == '/' || op1 == '^') {
			return (op1 == '*' || op1 == '/' || op1 == '^')
					&& (op2 == '^' || op2 == '/' || op2 == '*' || op2 == '+' || op2 == '-');
		} else {
			return (op1 == '+' || op1 == '-') && (op2 == '+' || op2 == '-');
		}
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
		default:
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}

}
