package ttree.scratch.md25;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.logging.Logger;

import ttree.pipin.i2c.MD25Motor;
import ttree.scratch.OutgoingMessage;

/**
 * Poll the MD25 encoders and send sensor_update
 * Provides position control using encoder readings
 * 
 * @author Michael Stevens
 */
public class MD25Encoder implements Runnable {

	final static Logger log = Logger.getLogger("MD25Encoder");
	
	private final MD25Motor md25Motor;
	private final int pollMillis;
	private final AtomicReferenceArray<Integer> positionDemand;
	
	private final OutgoingMessage messageHandler;
	
	/**
	 * Construct regular encoder reading with polling delay
	 * @param messageHandler	outgoing message handler or null if no sensor updates are required 
	 * @param md25Motor
	 * @param poleMillis
	 */
	public MD25Encoder(OutgoingMessage messageHandler, MD25Motor md25Motor, int pollMillis, AtomicReferenceArray<Integer> positionDemand) {

		if (pollMillis < 0) {
			throw new IllegalArgumentException("pollMillis < 0");
		}
		this.messageHandler = messageHandler;
		this.md25Motor = md25Motor;
		this.pollMillis = pollMillis;
		this.positionDemand = positionDemand;
	}

	private void position(int motor, int encoderValue) throws IOException {
		final Integer demand = positionDemand.get(motor-1);
		if (demand == null) {
			return;
		}
		
		// position demand is set
		int offset = encoderValue - demand;
		
		// proportional control with unity gain
		int speedDemand = -offset;

		// saturate speedDemand
		if (speedDemand > 127) {
			speedDemand = 127;
		}
		else if (speedDemand < -128) {
			speedDemand = -128;
		}
		
		if (motor == 1) {
			md25Motor.setSpeed1((byte)speedDemand);
		}
		else {
			md25Motor.setSpeed2((byte)speedDemand);
		}
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				// read encoders and update position control
				final int encoder1 = md25Motor.encoder1();
				position(1, encoder1);
				final int encoder2 = md25Motor.encoder2();
				position(2, encoder2);
				
				// send sensor update to scratch 
				if (messageHandler != null) {
					messageHandler.sensorUpdate("encoder1", String.valueOf(encoder1), "encoder2", String.valueOf(encoder2));
				}

				Thread.sleep(pollMillis);
			} catch (IOException e) {
				log.severe("MD25 encoder reading error: " + e.getMessage());
				break;
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}