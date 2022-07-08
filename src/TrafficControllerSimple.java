
public class TrafficControllerSimple implements TrafficController {

	private boolean empty = true;
	private TrafficRegistrar registrar;
	private boolean rightSide;
	
	public TrafficControllerSimple(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	public synchronized void enterRight(Vehicle v) { 
		
		while(empty == false) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
		}
		
		registrar.registerRight(v);
		this.empty = false;
		this.rightSide = false;
		notifyAll();
		
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
		this.empty = true;
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
		this.empty = true;
		notifyAll();     
	}
	
}
