package ttree.scratch;

import java.io.IOException;

import ttree.pipin.i2c.MD25Motor;

/**
 * Poll the MD25 encoders and send sensor_update
 * 
 * @author Michael Stevens
 */
public class MD25Encoder implements Runnable {

	private final ScratchConnection scratch;
	private final MD25Motor md25Motor;
	private final int pollMillis;
	
	/**
	 * Construct with polling delay
	 * @param scratch
	 * @param md25Motor
	 * @param poleMillis
	 */
	public MD25Encoder(ScratchConnection scratch, MD25Motor md25Motor, int pollMillis) {
		this.scratch = scratch;
		this.md25Motor = md25Motor;
		this.pollMillis = pollMillis;
	}


	@Override
	public void run() {
		while (true) {
			
			try {
				final int encoder1 = md25Motor.encoder1();
				final int encoder2 = md25Motor.encoder2();
				
				final String update = //
						"sensor-update \"encoder1\" " + String.valueOf(encoder1) + //
						"sensor-update \"encoder2\" " + String.valueOf(encoder2);
				scratch.writeLine(update);

				System.out.println(update);

				Thread.sleep(pollMillis);
			} catch (InterruptedException | IOException e) {
				; // stop execution
			}
		}
	}

}
