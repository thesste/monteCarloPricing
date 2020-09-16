package monteCarloPricing;

/**
 * Class allows for calculation of the payoff of a plain vanilla option.
 * @author thesste
 *
 */
public class EuropeanCallPut implements FinancialInstrument {
	
	private double strike;
	private String optionType;
	private String[] validOptionTypes = {"Call", "Put"};

	public double getStrike() {
		return strike;
	}

	public void setStrike(double strike) {
		this.strike = strike;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String[] getValidOptionTypes() {
		return validOptionTypes;
	}

	/**
	 * It is checked whether the option type is among the 2 valid types 'Call' or 'Put'
	 * @param input option type
	 * @return true if option type is valid, false otherwise
	 */
	public boolean validateInput(String input) {
		boolean valid = false;
		for (int currentValidInputNumber = 0; currentValidInputNumber<validOptionTypes.length; currentValidInputNumber++) {
			if (input.equals(validOptionTypes[currentValidInputNumber])) {
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	/**
	 * This method constructs a European plain vanilla option and validates the option type.
	 * @param strike strike price
	 * @param optionType 'Call' or 'Put'
	 */
	public EuropeanCallPut(double strike, String optionType) {
		
		assert validateInput(optionType)  : "\nPlease select a valid option type!";

		this.strike = strike;		
		this.optionType = optionType;
	}
		
	/**
	 * Calculates the option's payoff given a specific underlying path
	 * @return option's payoff
	 */
	public double payoff(double[] underlyingPath) {
		double underlyingValueAtMaturity = underlyingPath[underlyingPath.length-1];
		if (optionType.equals("Call")) {
			return Math.max(underlyingValueAtMaturity - strike, 0);
		} else if (optionType.equals("Put")) {
			return Math.max(strike - underlyingValueAtMaturity, 0);
		} else {
			return Double.NaN;
		}
	}
}