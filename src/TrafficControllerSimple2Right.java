
public class TrafficControllerSimple2Right implements TrafficController {
	
	private boolean empty = true;
	private TrafficRegistrar registrar;
	private boolean rightSide;
	private int enteredRight = 0;
	private boolean nextRight = false;
	
	public TrafficControllerSimple2Right(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		if(nextRight == false) {
			while(empty == false) {
				
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				       
			}
		}
		
		registrar.registerRight(v);
		this.enteredRight++;
		this.empty = false;
		this.rightSide = false;
		if(enteredRight < 2) {
			nextRight = true;
		}else {
			nextRight = false;
		}
		if(enteredRight == 0) notifyAll();
		
	}
	
	public synchronized void enterLeft(Vehicle v) {
		
		while(empty == false) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		
		registrar.registerLeft(v);
		this.empty = false;
		this.rightSide = true;
		notifyAll();
		   
	}
	
	public synchronized void leaveLeft(Vehicle v) {
		
		while(rightSide == true) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		registrar.deregisterLeft(v);
		this.enteredRight--;
		if(this.enteredRight == 0) {
			this.empty = true;
			notifyAll(); 
		}
		
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
		this.empty = true;
		notifyAll();     
	}

}
