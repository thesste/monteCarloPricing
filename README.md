# monteCarloPricing
Option Pricing Per Monte-Carlo Simulation

In order to run the .jar file please follow these steps:
1) Make sure you have JDK/JRE installed on your machine
2) Open your command prompt/terminal
3) Navigate to the location of the .jar file
4) Execute the following line:
java -jar -ea monteCarloPricing-0.0.1-SNAPSHOT.jar

Assumptions while Developing the Pricer
- Number of Trading Days per Year: 253 (as per average at NYSE/NASDAQ)
- If maturity entered by the user does not translate to an integer in terms of trading days, the number of trading days is rounded down to the previous integer.
- Delta and vega calculations are based on forward difference approximation.
- Gamma calculation is based on central difference approximation.

Things to be improved...
- Memory efficiency to allow for more and longer simulation paths and therefore for more precise results
- GUI also allowing for easier trade comparison
- Additional option to fix the random seed in order to replicate the results

...please let me know what else could be improved

Looking forward to your feedback!
