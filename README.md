# monteCarloPricing
Option Pricing Per Monte-Carlo Simulation

In order to run the .jar file please follow these steps:
1) Navigate to the location of the .jar file
2) Execute the following line:
java -jar -ea monteCarloPricing-0.0.1-SNAPSHOT.jar

Assumptions while Developing the Pricer
- Number of Trading Days per Year: 253 (as per NYSE / NASDAQ)
- If user's maturity does not translate to an integer in terms of trading days, the number of trading days is rounded down to the previous integer.
- Delta and vega calculations are based on forward difference approximation.
- Gamma calculation is based on central difference approximation.

Things to be improved...
- Memory efficiency to allow for more simulation paths and therefore for more precise results
- GUI also allowing for easier trade comparison
...please let me know what else can be improved

Looking forward to your feedback!
