package monteCarloPricing;

public class EuropeanCallPut implements FinancialInstrument {
	
	private double underlyingAtMaturity;
	private double strike;
	private String optionType;
	private String[] validOptionTypes = {"Call", "Put"};

	public double getUnderlyingAtMaturity() {
		return underlyingAtMaturity;
	}
	
	public void setUnderlyingAtMaturity(double underlyingAtMaturity) {
		this.underlyingAtMaturity = underlyingAtMaturity;
	}

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

	public EuropeanCallPut(double underlyingAtMaturity, double strike, String optionType) {
		this.underlyingAtMaturity = underlyingAtMaturity;
		this.strike = strike;
		this.optionType = optionType;
	}

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
	
	public double payoff() {
		assert validateInput(optionType)  : "\nPlease select a valid option type!";		
		if (optionType.equals("Call")) {
			return Math.max(underlyingAtMaturity - strike, 0);
		} else if (optionType.equals("Put")) {
			return Math.max(strike - underlyingAtMaturity, 0);
		} else {
			return Double.NaN;
		}
		
	}

}
