package monteCarloPricing;

public interface FinancialInstrument {
	
	public double payoff(double[] underlyingPath);
	
}