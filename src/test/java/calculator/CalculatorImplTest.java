package calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorImplTest {

	Calculator formula;

	@BeforeEach
	void setup() {
		// TODO set the class to test!
		formula = new CalculatorImpl();
	}

	@Test
	void testAddition() {
		double result = formula.calculate("3 + 4");
		Assertions.assertEquals(7, result, 0);
	}

	@Test
	void testAddition2() {
		double result = formula.calculate("3 + 4 + 5");
		Assertions.assertEquals(12, result, 0);
	}

	@Test
	void testAddition3() {
		double result = formula.calculate("3.6 + 4.41");
		Assertions.assertEquals(8.01, result, 0);
	}

	// this test is disabled due to german comma
	// @Test
	// void testAddition4() {
	// double result = formula.calculate("3,6 + 4,41");
	// Assertions.assertEquals(8.01, result, 0);
	// }

	@Test
	void testAdditionWithNegativeNumber() {
		double result = formula.calculate("3 + -2");
		Assertions.assertEquals(1, result, 0);
	}

	@Test
	void testSubtraction() {
		double result = formula.calculate("6 - 4");
		Assertions.assertEquals(2, result, 0);
	}

	@Test
	void testSubtraction2() {
		double result = formula.calculate("6 - 4 - 1");
		Assertions.assertEquals(1, result, 0);
	}

	@Test
	void testSubtractionNegative() {
		double result = formula.calculate("4 - 6");
		Assertions.assertEquals(-2, result, 0);
	}

	@Test
	void testSubtractionNegative2() {
		double result = formula.calculate("4 - 6 - 3");
		Assertions.assertEquals(-5, result, 0);
	}

	@Test
	void testMultiplication() {
		double result = formula.calculate("3 * 8");
		Assertions.assertEquals(24, result, 0);
	}

	@Test
	void testMultiplication2() {
		double result = formula.calculate("3 * 8 * 2");
		Assertions.assertEquals(48, result, 0);
	}

	@Test
	void testMultiplicationNegative() {
		double result = formula.calculate("3 * 8 * -2");
		Assertions.assertEquals(-48, result, 0);
	}

	@Test
	void testDivision() {
		double result = formula.calculate("12 / 3");
		Assertions.assertEquals(4, result, 0);
	}

	@Test
	void testDivision2() {
		double result = formula.calculate("12 / 3 / 2");
		Assertions.assertEquals(2, result, 0);
	}

	@Test
	void testDivisionNegative() {
		double result = formula.calculate("12 / 3 / -2");
		Assertions.assertEquals(-2, result, 0);
	}

	@Test
	void testDivisionDecimalResult() {
		double result = formula.calculate("12 / 5");
		Assertions.assertEquals(2.4, result, 0);
	}

	@Test
	void testMixed1a() {
		double result = formula.calculate("4 * 10 + 3");
		Assertions.assertEquals(43, result, 0);
	}

	@Test
	void testMixed1b() {
		double result = formula.calculate("3 + 4 * 10 ");
		Assertions.assertEquals(43, result, 0);
	}

	@Test
	void testMixed2a() {
		double result = formula.calculate("16 + 4/2");
		Assertions.assertEquals(18, result, 0);
	}

	@Test
	void testMixed2b() {
		double result = formula.calculate("4 / 2 + 16");
		Assertions.assertEquals(18, result, 0);
	}

	@Test
	void testMixed2c() {
		double result = formula.calculate("16 - 4 / 2");
		Assertions.assertEquals(14, result, 0);
	}

	@Test
	void testMixed2d() {
		double result = formula.calculate("4 / 2 - 16");
		Assertions.assertEquals(-14, result, 0);
	}

	@Test
	void testMixedBracket() {
		double result = formula.calculate("( 3 + 4 ) * 10 ");
		Assertions.assertEquals(70, result, 0);
	}

	@Test
	void testMixedBracket2() {
		double result = formula.calculate("( 3 + 4 ) * 10 / 5");
		Assertions.assertEquals(14, result, 0);
	}

	@Test
	void testMixedBracket3() {
		double result = formula.calculate("( 3 + 4 ) * ( 10 / 5 + 1) - 11");
		Assertions.assertEquals(10, result, 0);
	}

	@Test
	void testMixedBracket4() {
		double result = formula.calculate("(3 + (1 + (1 + 1 / 2) + (2 * 1 / 4 + 1))) * ( 10 / 5 + (1 / 4 + 3 / 4 ) )");
		Assertions.assertEquals(21, result, 0);
	}

	@Test
	void testGoogle() {
		double result = formula.calculate("6/2*(1+2)");
		Assertions.assertEquals(9, result, 0);
	}

	@Test
	void testGoogle2() {
		double result = formula.calculate("4 + 6 + 9 - 3 * 20 / (4)");
		Assertions.assertEquals(4, result, 0);
	}

	@Test
	void testAufgabeWeiredOne() {
		double result = formula.calculate("10--10");
		Assertions.assertEquals(20, result, 0);
	}

	@Test
	void testAufgabeWeiredTwo() {
		double result = formula.calculate("1.+1");
		Assertions.assertEquals(2, result, 0);
	}

	@Test
	void testExponentiation() {
		double result = formula.calculate("2^3");
		Assertions.assertEquals(8, result, 0);
	}

	@Test
	void testSquareRoot() {
		double result = formula.calculate("sqrt(16)");
		Assertions.assertEquals(4, result, 0);
	}

	@Test
	void testMixedExponentiationAndSquareRoot() {
		double result = formula.calculate("2^3 + sqrt(25)");
		Assertions.assertEquals(13, result, 0);
	}
}
