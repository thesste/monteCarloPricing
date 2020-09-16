package monteCarloPricing;

/**
 * Class allows for calculation of the payoff of a barrier option.
 * @author thesste
 *
 */
public class KnockInOutBarrierCallPut implements FinancialInstrument {
	
	private double strike;
	private double barrier;
	private String optionType;
	private String barrierType;
	private String[] validOptionTypes = {"Call", "Put"};
	private String[] validBarrierTypes = {"UpIn", "DownIn", "UpOut", "DownOut"};	

	public double getStrike() {
		return strike;
	}

	public void setStrike(double strike) {
		this.strike = strike;
	}

	public double getBarrier() {
		return barrier;
	}

	public void setBarrier(double barrier) {
		this.barrier = barrier;
	}
	
	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getBarrierType() {
		return barrierType;
	}

	public void setBarrierType(String barrierType) {
		this.barrierType = barrierType;
	}

	public String[] getValidOptionTypes() {
		return validOptionTypes;
	}

	public String[] getValidBarrierTypes() {
		return validBarrierTypes;
	}

	public boolean validateInput(String input, String[] validInputs) {
		boolean valid = false;
		for (int currentValidInputNumber = 0; currentValidInputNumber<validInputs.length; currentValidInputNumber++) {
			if (input.equals(validInputs[currentValidInputNumber])) {
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	/**
	 * This method constructs a path-dependent barrier option and validates the option and barrier type.
	 * @param strike strike price
	 * @param barrier barrier value
	 * @param optionType option type
	 * @param barrierType barrier type
	 */
	public KnockInOutBarrierCallPut(double strike, double barrier, String optionType, String barrierType) {

		assert validateInput(optionType, validOptionTypes)  : "\nPlease select a valid option type!";
		assert validateInput(barrierType, validBarrierTypes)  : "\nPlease select a valid barrier type!";
		
		this.strike = strike;
		this.barrier = barrier;
		this.optionType = optionType;
		this.barrierType = barrierType;
	}
	
	/**
	 * It is checked whether the barrier option was activated / was not deactivated at maturity.
	 * @param underlyingPath underlying path
	 * @return true if option is (still) active at maturity, false otherwise
	 */
	public boolean isActive(double[] underlyingPath) {	
		boolean active;
		if (barrierType.equals("UpIn")) {
			active = false;
			for (int currentTradingDay = 0; currentTradingDay<underlyingPath.length; currentTradingDay++) {
				if (underlyingPath[currentTradingDay]>=barrier) {
					active = true;
					break;
				}
			}
		} else if (barrierType.equals("DownIn")) {
			active = false;
			for (int currentTradingDay = 0; currentTradingDay<underlyingPath.length; currentTradingDay++) {
				if (underlyingPath[currentTradingDay]<=barrier) {
					active = true;
					break;
				}
			}
		} else if (barrierType.equals("UpOut")) {
			active = true;
			for (int currentTradingDay = 0; currentTradingDay<underlyingPath.length; currentTradingDay++) {
				if (underlyingPath[currentTradingDay]>=barrier) {
					active = false;
					break;
				}
			}
		} else if (barrierType.equals("DownOut")) {
			active = true;
			for (int currentTradingDay = 0; currentTradingDay<underlyingPath.length; currentTradingDay++) {
				if (underlyingPath[currentTradingDay]<=barrier) {
					active = false;
					break;
				}
			}			
		} else {			
			active = true;
		}
		return active;
	}
	
	/**
	 * Calculates the option's payoff given a specific underlying path
	 * @return option's payoff
	 */
	public double payoff(double[] underlyingPath) {
		if (optionType.equals("Call")) {
			if (isActive(underlyingPath)) {
				return Math.max(underlyingPath[underlyingPath.length-1] - strike, 0);
			} else {
				return 0;
			}
		} else if (optionType.equals("Put")) {
			if (isActive(underlyingPath)) {
				return Math.max(strike - underlyingPath[underlyingPath.length-1], 0);
			} else {
				return 0;
			}
		} else {
			return Double.NaN;
		}
	}

}
