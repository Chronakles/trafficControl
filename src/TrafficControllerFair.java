import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficControllerFair implements TrafficController {

	
	private boolean empty = true;
	private TrafficRegistrar registrar;
	private boolean rightSide;
	private Lock lock = new ReentrantLock(true);
	private Condition placeEmpty = lock.newCondition();
	
	public TrafficControllerFair(TrafficRegistrar r) {
		this.registrar = r;
	}
	
	//@Override
	public void enterRight(Vehicle v) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			
			while(empty == false) {
				placeEmpty.await();
			} 
			
			registrar.registerRight(v);
			this.empty = false;
			this.rightSide = false;
			placeEmpty.signalAll(); 
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
		
	}

	@Override
	public void enterLeft(Vehicle v) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			
			while(empty == false) {
				placeEmpty.await();
			}
			
			registrar.registerLeft(v);
			this.empty = false;
			this.rightSide = true;
			placeEmpty.signalAll();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
		
	}

	@Override
	public void leaveLeft(Vehicle v) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			
			while(rightSide == true) {
				placeEmpty.await();
			}
			
			registrar.deregisterLeft(v);
			empty = true;
			placeEmpty.signalAll();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void leaveRight(Vehicle v) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(rightSide == false) {
				placeEmpty.await();
			}
			
			registrar.deregisterRight(v);
			empty = true;
			placeEmpty.signalAll();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			lock.unlock();
		}
	}

}
