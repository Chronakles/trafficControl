
public class TrafficControllerSimple2 implements TrafficController {

	private int empty = 0;
	private TrafficRegistrar registrar;
	private boolean rightSide;
	private boolean leftSide;
	private int enteredRight = 0;
	private int enteredLeft = 0;
	
	public TrafficControllerSimple2(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		
		while(empty == 2) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		
		registrar.registerRight(v);
		this.empty++;
		//this.rightSide = false;
		this.enteredRight++;
		this.leftSide = true;
		notifyAll();
		
	}
	
	public synchronized void enterLeft(Vehicle v) {
		
		while(empty == 2) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		
		registrar.registerLeft(v);
		this.empty++;
		this.rightSide = true;
		this.enteredLeft++;
		//this.leftSide = false;
		notifyAll();
		   
	}
	
	public synchronized void leaveLeft(Vehicle v) {
		
		while(leftSide == false) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		registrar.deregisterLeft(v);
		this.empty--;
		this.enteredRight--;
		if(enteredRight == 0) this.leftSide = false;
		notifyAll();
	}
	
	public synchronized void leaveRight(Vehicle v) { 
		while(rightSide == false) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		registrar.deregisterRight(v); 
		this.empty--;
		this.enteredLeft--;
		if(enteredLeft == 0) this.rightSide = false;
		notifyAll();     
	}
}
