package pt.uc.dei.aor.paj;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MathExpressionTest {
	private MathExpression exp;
	
	@Before
	public void init() {
		exp = new MathExpression();
		
	}
	
	@Test
	public void testClone() {
		exp.setReset(1);
		exp.setExpression("4+5/4");
		MathExpression exp2 = exp.getClone();
		
		assertThat(exp2, is(equalTo(exp)));
	}

	@Test
	public void testDivisionByZero() {
		exp.setExpression("1/0");
		
		exp.evaluate();
		assertThat(exp.getExpression(), is(equalTo("Divisão por zero")));
	}
}
