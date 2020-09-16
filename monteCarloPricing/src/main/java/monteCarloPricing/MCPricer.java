package monteCarloPricing;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MCPricer {
	
	private double underlyingPrice;
	private double sigma;
	private double interestRate;
	private int numberOfPaths;
	private int numberOfTradingDaysPerYear;
	private double maxMaturityInYears;
	
	private double deltaUnderlying = 0.01;
	private double deltaVolatility = 0.01;
	
	private int simulatedTradingDays;
	private double timeStepPerTradingDay;
	private double[][] paths;
	private double[][] pathsIncreasedUnderlyingPrice;
	private double[][] pathsDecreasedUnderlyingPrice;
	private double[][] pathsIncreasedVolatility;

	
	public double getUnderlyingPrice() {
		return underlyingPrice;
	}
	public void setUnderlyingPrice(double underlyingPrice) {
		this.underlyingPrice = underlyingPrice;
	}
	public double getSigma() {
		return sigma;
	}
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public int getNumberOfPaths() {
		return numberOfPaths;
	}
	public void setNumberOfPaths(int numberOfPaths) {
		this.numberOfPaths = numberOfPaths;
	}
	public int getNumberOfTradingDaysPerYear() {
		return numberOfTradingDaysPerYear;
	}
	public void setNumberOfTradingDaysPerYear(int numberOfTradingDaysPerYear) {
		this.numberOfTradingDaysPerYear = numberOfTradingDaysPerYear;
	}
	public double getMaxMaturityInYears() {
		return maxMaturityInYears;
	}
	public void setMaxMaturityInYears(double maxMaturityInYears) {
		this.maxMaturityInYears = maxMaturityInYears;
	}
	
	
	public int getSimulatedTradingDays() {
		return simulatedTradingDays;
	}
	public double getTimeStepPerTradingDay() {
		return timeStepPerTradingDay;
	}
	
	
	public double[][] getPaths() {
		return paths;
	}
	public double[][] getPathsIncreasedUnderlyingPrice() {
		return pathsIncreasedUnderlyingPrice;
	}
	public double[][] getPathsDecreasedUnderlyingPrice() {
		return pathsDecreasedUnderlyingPrice;
	}
	public double[][] getPathsIncreasedVolatility() {
		return pathsIncreasedVolatility;
	}
	
	/*
	 * Constructor
	 */
	
	public MCPricer(double underlyingPrice, double sigma, double interestRate, int numberOfPaths,
			int numberOfTradingDaysPerYear, double maxMaturityInYears) {

		this.underlyingPrice = underlyingPrice;
		this.sigma = sigma;
		this.interestRate = interestRate;
		this.numberOfPaths = numberOfPaths;
		this.numberOfTradingDaysPerYear = numberOfTradingDaysPerYear;
		this.maxMaturityInYears = maxMaturityInYears;
	}
	
	/*
	 * Path Generator
	 */
	
	public void calculateSimulatedTradingDays() {
		this.simulatedTradingDays = (int) maxMaturityInYears*numberOfTradingDaysPerYear;
	}

	public void calculateTimeStepPerTradingDay() {
		this.timeStepPerTradingDay = Math.pow(numberOfTradingDaysPerYear, -1);
	}
	
	public double nextDaysUnderlyingEodPrice(double previousUnderlyingEodPrice, double randomCurrent, double volatility) {
		double nextDaysPrice = previousUnderlyingEodPrice*Math.exp(((interestRate-(0.5*Math.pow(volatility, 2)))*timeStepPerTradingDay) + (volatility*Math.sqrt(timeStepPerTradingDay)*randomCurrent));
		return nextDaysPrice;
	}
	
	public void generatePaths() {
		calculateSimulatedTradingDays();
		calculateTimeStepPerTradingDay();
		
		double[][] paths = new double[numberOfPaths][simulatedTradingDays+1];
		double[][] pathsIncreasedUnderlyingPrice = new double[numberOfPaths][simulatedTradingDays+1];
		double[][] pathsDecreasedUnderlyingPrice = new double[numberOfPaths][simulatedTradingDays+1];
		double[][] pathsIncreasedVolatility = new double[numberOfPaths][simulatedTradingDays+1];		

		for (int current_row = 0; current_row<numberOfPaths; current_row++) {
			for(int current_column = 0; current_column<=simulatedTradingDays; current_column++) {
				if (current_column == 0) {
					paths[current_row][current_column] = underlyingPrice;
					pathsIncreasedUnderlyingPrice[current_row][current_column] = underlyingPrice + deltaUnderlying;
					pathsDecreasedUnderlyingPrice[current_row][current_column] = underlyingPrice - deltaUnderlying;
					pathsIncreasedVolatility[current_row][current_column] = underlyingPrice;
					} else {
						Random ran = new Random();
						double gauss = ran.nextGaussian();						
						paths[current_row][current_column] = nextDaysUnderlyingEodPrice(paths[current_row][current_column-1], gauss, sigma);
						pathsIncreasedUnderlyingPrice[current_row][current_column] = nextDaysUnderlyingEodPrice(pathsIncreasedUnderlyingPrice[current_row][current_column-1], gauss, sigma);
						pathsDecreasedUnderlyingPrice[current_row][current_column] = nextDaysUnderlyingEodPrice(pathsDecreasedUnderlyingPrice[current_row][current_column-1], gauss, sigma);
						pathsIncreasedVolatility[current_row][current_column] = nextDaysUnderlyingEodPrice(pathsIncreasedVolatility[current_row][current_column-1], gauss, sigma+deltaVolatility);
					}
				}
			}
		this.paths = paths;
		this.pathsIncreasedUnderlyingPrice = pathsIncreasedUnderlyingPrice;
		this.pathsDecreasedUnderlyingPrice = pathsDecreasedUnderlyingPrice;
		this.pathsIncreasedVolatility = pathsIncreasedVolatility;
	}
	
	/*
	 * Trade Specifics
	 */
	
	public int calculateActualTradingDays(double maturity) {
		return (int) maturity*numberOfTradingDaysPerYear;
		
	}	
	public double[][] restrictPathsByOptionsMaturity(double[][] simulatedPaths, double maturity) {
		int actualTradingDays = calculateActualTradingDays(maturity);
		
		double[][] restrictedPaths = new double[numberOfPaths][actualTradingDays+1];
		
		for (int currentRowNumber = 0; currentRowNumber<numberOfPaths; currentRowNumber++) {
			restrictedPaths[currentRowNumber] = Arrays.copyOfRange(simulatedPaths[currentRowNumber], 0, actualTradingDays+1);
			}
		return restrictedPaths;
	}
	
	public double[] calculateDiscountedPayoffsForRestrictedPaths(double[][] restrictedPaths, double strike, double barrier, double maturity, String optionType, String barrierType) {
		
		double[] discountedPayoffsForPaths = new double[numberOfPaths];
		
		if (barrierType.equals("None")) {
			for (int currentPathNumber = 0; currentPathNumber<numberOfPaths; currentPathNumber++) {
				FinancialInstrument currentInstrument = new EuropeanCallPut(strike, optionType);
				discountedPayoffsForPaths[currentPathNumber] = currentInstrument.payoff(restrictedPaths[currentPathNumber])*Math.exp((-1)*maturity*interestRate);
			}
		} else {
			for (int currentPathNumber = 0; currentPathNumber<numberOfPaths; currentPathNumber++) {
				FinancialInstrument currentInstrument = new KnockInOutBarrierCallPut(strike, barrier, optionType, barrierType);
				discountedPayoffsForPaths[currentPathNumber] = currentInstrument.payoff(restrictedPaths[currentPathNumber])*Math.exp((-1)*maturity*interestRate);
			}
		}
		return discountedPayoffsForPaths;
	}
	
	public double calculateOptionPrice(double strike, double barrier, double maturity, String optionType, String barrierType) {
		
		double[][] restrictedPaths = restrictPathsByOptionsMaturity(paths, maturity);
		double[] discountedPayoffsForPaths = calculateDiscountedPayoffsForRestrictedPaths(restrictedPaths, strike, barrier, maturity, optionType, barrierType);
		double price = Arrays.stream(discountedPayoffsForPaths).average().orElse(Double.NaN);
		
		return price;
	}
	
	/*
	 *  Greeks
	 */
	
	public double calculateDelta(double strike, double barrier, double maturity, String optionType, String barrierType) {
				
		double[][] restrictedPaths = restrictPathsByOptionsMaturity(paths, maturity);
		double[] discountedPayoffsForPaths = calculateDiscountedPayoffsForRestrictedPaths(restrictedPaths, strike, barrier, maturity, optionType, barrierType);
		
		double[][] restrictedPathsIncreasedUnderlyingPrice = restrictPathsByOptionsMaturity(pathsIncreasedUnderlyingPrice, maturity);
		double[] discountedPayoffsForPathsIncreasedUnderlyingPrice = calculateDiscountedPayoffsForRestrictedPaths(restrictedPathsIncreasedUnderlyingPrice, strike, barrier, maturity, optionType, barrierType);

		double[] discountedPayoffDeltas = new double[numberOfPaths];
		
		for (int currentPathNumber = 0; currentPathNumber<numberOfPaths; currentPathNumber++) {
			discountedPayoffDeltas[currentPathNumber] = discountedPayoffsForPathsIncreasedUnderlyingPrice[currentPathNumber] - discountedPayoffsForPaths[currentPathNumber];
		}
		return Arrays.stream(discountedPayoffDeltas).average().orElse(Double.NaN) / (double) deltaUnderlying;		
	}
	
	public double calculateGamma(double strike, double barrier, double maturity, String optionType, String barrierType) {

		double[][] restrictedPaths = restrictPathsByOptionsMaturity(paths, maturity);
		double[] discountedPayoffsForPaths = calculateDiscountedPayoffsForRestrictedPaths(restrictedPaths, strike, barrier, maturity, optionType, barrierType);
		
		double[][] restrictedPathsIncreasedUnderlyingPrice = restrictPathsByOptionsMaturity(pathsIncreasedUnderlyingPrice, maturity);
		double[] discountedPayoffsForPathsIncreasedUnderlyingPrice = calculateDiscountedPayoffsForRestrictedPaths(restrictedPathsIncreasedUnderlyingPrice, strike, barrier, maturity, optionType, barrierType);

		double[][] restrictedPathsDecreasedUnderlyingPrice = restrictPathsByOptionsMaturity(pathsDecreasedUnderlyingPrice, maturity);
		double[] discountedPayoffsForPathsDecreasedUnderlyingPrice = calculateDiscountedPayoffsForRestrictedPaths(restrictedPathsDecreasedUnderlyingPrice, strike, barrier, maturity, optionType, barrierType);
				
		double[] discountedPayoffGammas = new double[numberOfPaths];
		
		for (int currentPathNumber = 0; currentPathNumber<numberOfPaths; currentPathNumber++) {
			discountedPayoffGammas[currentPathNumber] = discountedPayoffsForPathsIncreasedUnderlyingPrice[currentPathNumber] - 2*discountedPayoffsForPaths[currentPathNumber] + discountedPayoffsForPathsDecreasedUnderlyingPrice[currentPathNumber];
		}
		return Arrays.stream(discountedPayoffGammas).average().orElse(Double.NaN) / (double) Math.pow(deltaUnderlying, 2);		
	}

	public double calculateVega(double strike, double barrier, double maturity, String optionType, String barrierType) {

		double[][] restrictedPaths = restrictPathsByOptionsMaturity(paths, maturity);
		double[] discountedPayoffsForPaths = calculateDiscountedPayoffsForRestrictedPaths(restrictedPaths, strike, barrier, maturity, optionType, barrierType);

		double[][] restrictedPathsIncreasedVolatility = restrictPathsByOptionsMaturity(pathsIncreasedVolatility, maturity);
		double[] discountedPayoffsForPathsIncreasedVolatility = calculateDiscountedPayoffsForRestrictedPaths(restrictedPathsIncreasedVolatility, strike, barrier, maturity, optionType, barrierType);
		
		double[] discountedPayoffVegas = new double[numberOfPaths];
		
		for (int currentPathNumber = 0; currentPathNumber<numberOfPaths; currentPathNumber++) {
			discountedPayoffVegas[currentPathNumber] = discountedPayoffsForPathsIncreasedVolatility[currentPathNumber] - discountedPayoffsForPaths[currentPathNumber];
		}
		return Arrays.stream(discountedPayoffVegas).average().orElse(Double.NaN);		
	}
	
	/*
	 * Main
	 */
	
	public static void main(String[] args) {
		
		/*
		 * Simulation Specifics
		 */

		Scanner input = new Scanner(System.in);
		
		// /*
		System.out.println("First please enter simulation specifics...");		
		System.out.print("Enter the number of simulated paths (recommended are not more than 10,000): ");
		int numberOfPaths = input.nextInt();
		
		System.out.print("Enter the maximum maturity in years (recommended are not more than 5): ");
		double maxMaturity = input.nextDouble();

		System.out.print("Enter the underlying's spot price: ");
		double underlyingPrice = input.nextDouble();

		System.out.print("Enter the underlying's volatility: ");
		double volatility = input.nextDouble();
		
		System.out.print("Enter the market's risk free rate: ");
		double riskFreeRate = input.nextDouble();
		// */
						
		/*
		int numberOfPaths = 100;
		double maxMaturity = 3;
		double underlyingPrice = 90;
		double volatility = 0.2;
		double riskFreeRate = 0.05;
		*/
				
		int numberOfTradingDaysPerYear = 252;		
		MCPricer pricer = new MCPricer(underlyingPrice, volatility, riskFreeRate, numberOfPaths, numberOfTradingDaysPerYear, maxMaturity);
		System.out.println("Generating paths...");
		pricer.generatePaths();
		
		boolean anotherTrade = true;
		while (anotherTrade) {
				
			/*
			 *Trade Specifics
			 */
			
			System.out.println("Now please enter the trade specifics...");				
			System.out.println("Enter the option type (choose between 'Call' and 'Put'): ");
			String optionType = input.next();
								
			System.out.print("Enter the " + optionType + "'s strike price: ");
			double strike = input.nextDouble();
			
			System.out.print("Enter the " + optionType + "'s maturity in years: ");
			double maturity = input.nextDouble();
			assert maturity <= pricer.maxMaturityInYears : "\nTrade's maturity exceeds maximum maturity - please correct!";
			
			System.out.print("Enter the barrier type (choose between 'None', 'UpIn', 'DownIn', 'UpOut' and 'DownOut'): ");
			String barrierType = input.next();
					
			double barrier = Double.NaN;
			if (!(barrierType.equals("None"))) {
				System.out.print("Enter the " + barrierType + " barrier value: ");
				barrier = input.nextDouble();			
			}
			
			/*
			 * Option Price
			 */
					
			double price = pricer.calculateOptionPrice(strike, barrier, maturity, optionType, barrierType);
			System.out.println("Please find your results below...");	
			System.out.println("Price: " + price);
			
			/*
			 * Greeks
			 */
			
			double delta = pricer.calculateDelta(strike, barrier, maturity, optionType, barrierType);
			System.out.println("Delta: " + delta);
			
			double gamma = pricer.calculateGamma(strike, barrier, maturity, optionType, barrierType);
			System.out.println("Gamma: " + gamma);
			
			double vega = pricer.calculateVega(strike, barrier, maturity, optionType, barrierType);
			System.out.println("Vega: " + vega);
			
			System.out.print("Do you want to price another trade based on the same underlying paths? (Enter true or false): ");
			anotherTrade = input.nextBoolean();
		
		}	
			
		input.close();
	}
}