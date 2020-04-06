import java.io.IOException;
import java.lang.*;
import java.util.Scanner;



public class Iterations {

	public static void main(String[] args) throws IOException {
		
		double lamavg, dellam, lam1the, lam2the, lam2, dis, errdis;
		double errlam1, errlam2, errdellam, errdellam1, errdellam2, errlamavg;
		
		int iter;
		dis = 0.0787/1000;																//static variable declaration
		errdis = 0.0001/1000;									
		lam1the = 579.065*Math.pow(10,-9);
		lam2the = 576.960*Math.pow(10,-9);
		
		dellam = Math.pow(lam1the,2)/(2*dis);											//initialization of delta lambda (first estimate)
		System.out.println("Dellam1:\t" + dellam*Math.pow(10,9));		
				
		errdellam = errdis*Math.pow(lam1the,2)/(2*Math.pow(dis,2));						//error in first estimate of delta lambda
		System.out.println("ErrDellam:\t" + errdellam*Math.pow(10,9));	
			
		lam2 = lam1the-dellam;															//initialization of lambda 2 (first estimate)
		System.out.println("Lam2:\t" + lam2*Math.pow(10,9));
		
		errlam2 = errdellam;															//error in first estimate of lambda 2			
		System.out.println("errLam2:\t" + errlam2*Math.pow(10,9));				
		
		lamavg = (lam1the+lam2)/2;														//initialization of lamavg (first estimate)
		System.out.println("Lamavg:\t" + lamavg*Math.pow(10,9));	
		
		errlamavg = errlam2/2;															//error in first estimate of lambda avg
		System.out.println("errLamavg:\t" + errlamavg*Math.pow(10,9));

		dellam = Math.pow(lamavg,2)/(2*dis);											//second estimate of delta lambda, ready for iterations
		System.out.println("delLam2:\t" + dellam*Math.pow(10,9));
		
		errdellam1 = errdis*Math.pow(lamavg,2)/(2*Math.pow(dis,2));						//error in delllam from distance
		System.out.println("errdelLam21:\t" + errdellam1*Math.pow(10,9));
		
		errdellam2 = errlamavg*lamavg/(dis);											//error in dellam from lambda avg
		System.out.println("errdellam22:\t" + errdellam2*Math.pow(10,9));
		
		errdellam = Math.pow(Math.pow(errdellam1,2)+Math.pow(errdellam2,2),0.5);		//total error in dellam 
		System.out.println("errdellam2:\t" + errdellam*Math.pow(10,9));				
				
		
		Scanner userinput = new Scanner (System.in);									//scanner for # of iterations
		System.out.print("Enter the # of iteration: ");
		iter = userinput.nextInt();								
		
		System.out.println("lambda 2 \t\terrlam2 \t\tlamavg \t\t\terrlamavg \t\tdellam \t\t\terrdellam");
		
		for (int i = 0; i < iter; i++)													//iterates equations to decrease error in dellam, lamavg
		{
			
			lam2 = lam1the-dellam;														//find lam2
			errlam2 = errdellam;														//error in lam2
			
			lamavg = (lam1the+lam2)/2;													//find lamavg
			errlamavg = errlam2/2;														//error in lamavg
			
			dellam = Math.pow(lamavg,2)/(2*dis);										//find dellam
			errdellam1 = errdis*Math.pow(lamavg,2)/(2*Math.pow(dis,2));
			errdellam2 = errlamavg*lamavg/(dis);
			errdellam = Math.pow(Math.pow(errdellam1,2)+Math.pow(errdellam2,2),0.5);	//error in dellam
			

			System.out.println(lam2*Math.pow(10,9) + "\t" + errlam2*Math.pow(10,9)
					+ "\t" + lamavg*Math.pow(10,9) + "\t" + errlamavg*Math.pow(10,9)
					 + "\t" + dellam*Math.pow(10,9) + "\t" + errdellam*Math.pow(10,9));
			
			
			
			
			

			
			
			
			
			
			
			
			
		}
	
	}
	
}
