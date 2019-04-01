import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoGain {
	
	static int DEPT_MATH = 1; 
	static int DEPT_HISTORY = 2;
	static int DEPT_CS = 3; 
	
	static int YES = 1; 
	static int NO = 2; 
	
	static String [] deptAttributeString = {"Math", "History", "CS"};
	static int [] deptAttribute = {DEPT_MATH, DEPT_HISTORY, DEPT_CS};
	static int [] Y_values= {YES, NO};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int [] X = {1, 2, 3, 1, 1, 3, 2, 1};
		int [] Y = {1, 2, 1, 2, 2, 1, 2, 1};
		
		double [] infoGain = new double [deptAttribute.length];
		
		double entY = entropyOfY(Y);
		
		double [] attrVals = new double [deptAttribute.length * 2];
		
		int yesMCount = 0;
		int noMCount = 0;
		int yesHCount = 0;
		int noHCount = 0;
		int yesCSCount = 0;
		int noCSCount = 0;
		
		
		for (int i=0; i<X.length; i++)
		{
			switch(X[i])
			{
				case 1:
					yesMCount += (Y[i] == 1 ? 1 : 0);
					noMCount += (Y[i] == 2 ? 1 : 0);
					break;
					
				case 2:
					yesHCount += (Y[i] == 1 ? 1 : 0);
					noHCount += (Y[i] == 2 ? 1 : 0);
					break;
					
				case 3:
					yesCSCount += (Y[i] == 1 ? 1 : 0);
					noCSCount += (Y[i] == 2 ? 1 : 0);
					break;
				
			}

			
		}	
		
		double entrofYWithXM = entropy(new double []{(double)yesMCount/(yesMCount + noMCount) , (double)noMCount/(yesMCount + noMCount) });
		double entrofYWithXH = entropy(new double []{(double)yesHCount/(yesHCount + noHCount) , (double)noHCount/(yesHCount + noHCount) });
		double entrofYWithXCS = entropy(new double []{(double)yesCSCount/(yesCSCount + noCSCount) , (double)noCSCount/(yesCSCount + noCSCount) });
		
		
//		if the vals of X were the attrs, then relinfo gain on the vals of X wld be used to detrmn d best attr
		List <Double> gains = getRelInfoGain(entY, entrofYWithXM, entrofYWithXH, entrofYWithXCS);
		System.out.println(gains);
		double maxGain = Collections.max(gains);
		int maxIndex =  gains.indexOf(maxGain);
		
		System.out.printf("\nMax gain is %f and is given by attr: %s", maxGain, deptAttributeString[maxIndex]); 
		
		
//		since X is only an attr, d actual calcn is
		double entropyYGivenX = entropyOfYGivenX((double)yesMCount/(yesMCount + noMCount), entrofYWithXM,
				(double)yesHCount/(yesHCount + noHCount), entrofYWithXH, 
				(double)yesCSCount/(yesCSCount + noCSCount), entrofYWithXCS);
		
		System.out.printf("\nEntropy of Y given x = %f and true info gain is %f", entropyYGivenX, (entY-entropyYGivenX));
	
	}
	
	static double entropyOfY(int [] YVals)
	{
		int yesCount = 0;
		int noCount = 0;
		
		for (int i=0; i<YVals.length; i++)
		{
			switch (YVals[i])
			{
			case 1:
				++yesCount;
				break;
				
			case 2:
				++noCount;
				break;
			}
		}
		
		return entropy(new double []{(double)yesCount/YVals.length, (double)noCount/YVals.length});
		
	}
	
	static double entropyOfYGivenX(double...args)
	{
		double entropy = 0;
		for (int i=1; i<args.length; i=i+2)
		{
			entropy += args[i] * args[i-1];
		}
		
		return entropy;
		
	}
	
	static double entropy(double ...args)
	{
		double entropy = 0;
		for (double val: args)
		{
			entropy += -val * (val == 0 || val == 1 ? 0 : (Math.log(val)/ Math.log(2)));
		}
		
		return entropy;
	}
	
	static List<Double>  getRelInfoGain(double ...args)
	{
		List <Double> relGains = new ArrayList();
		
		for (int i=1; i< args.length; i++)
		{
			relGains.add((args[0] - args[i])/args[0]);
		}
		
		return relGains;
		
	}

}
