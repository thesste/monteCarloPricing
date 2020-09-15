package monteCarloPricing;

public class KnockInOutBarrierCallPut implements FinancialInstrument {
	
	private double[] path;
	private double strike;
	private double barrier;
	private String optionType;
	private String barrierType;
	private String[] validOptionTypes = {"Call", "Put"};
	private String[] validBarrierTypes = {"UpIn", "DownIn", "UpOut", "DownOut"};	

	public double[] getPath() {
		return path;
	}

	public void setPath(double[] path) {
		this.path = path;
	}

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

	public KnockInOutBarrierCallPut(double[] path, double strike, double barrier, String optionType, String barrierType) {
		this.path = path;
		this.strike = strike;
		this.barrier = barrier;
		this.optionType = optionType;
		this.barrierType = barrierType;
	}
	
	public boolean isActive() {	
		boolean active;
		if (barrierType.equals("UpIn")) {
			active = false;
			for (int currentTradingDay = 0; currentTradingDay<path.length; currentTradingDay++) {
				if (path[currentTradingDay]>=barrier) {
					active = true;
					break;
				}
			}
		} else if (barrierType.equals("DownIn")) {
			active = false;
			for (int currentTradingDay = 0; currentTradingDay<path.length; currentTradingDay++) {
				if (path[currentTradingDay]<=barrier) {
					active = true;
					break;
				}
			}
		} else if (barrierType.equals("UpOut")) {
			active = true;
			for (int currentTradingDay = 0; currentTradingDay<path.length; currentTradingDay++) {
				if (path[currentTradingDay]>=barrier) {
					active = false;
					break;
				}
			}
		} else if (barrierType.equals("DownOut")) {
			active = true;
			for (int currentTradingDay = 0; currentTradingDay<path.length; currentTradingDay++) {
				if (path[currentTradingDay]<=barrier) {
					active = false;
					break;
				}
			}			
		} else {			
			active = true;
		}
		return active;
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

	public double payoff() {
		assert validateInput(optionType, validOptionTypes)  : "\nPlease select a valid option type!";
		assert validateInput(barrierType, validBarrierTypes)  : "\nPlease select a valid barrier type!";
		if (optionType.equals("Call")) {
			if (isActive()) {
				return Math.max(path[path.length-1] - strike, 0);
			} else {
				return 0;
			}
		} else if (optionType.equals("Put")) {
			if (isActive()) {
				return Math.max(strike - path[path.length-1], 0);
			} else {
				return 0;
			}
		} else {
			return Double.NaN;
		}
	}

}
