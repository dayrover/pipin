package ttree.pipin.i2c;

import java.io.IOException;

/**
 * Test LEDS driven by TLC59116 attached to I2C bus
 * 
 * @author michael
 */
public class LEDTest {

	/**
	 * Main for LED Test
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("Testing LEDs 0,1,2,3");
		final boolean active_leds[] = new boolean[] { 
				true, true, true, true,
				false, false, false,
				false, false, false,
				false, false, false,
				false, false, false
		};
		
		final LEDPWM ledpwm = new LEDPWM(0x6F);

		int pwm = 0;
		boolean up = true;
		for (;;) {
			if (up == true) {
				// maximum
				if (pwm == 255) {
					// stay on maximum and change direction
					up = !up;
				}
				else {
					++pwm;
				}
			}
			else {
				// minimum
				if (pwm == 0) {
					// stay on minimum and change direction
					up = !up;
				}
				else {
					--pwm;
				}
			}

			// output pwm to all active leds
			for (int led = 0; led != active_leds.length; ++led) {
				if (active_leds[led] == true) {
					ledpwm.pwm(led, pwm);
				}
			}
		}
	}

}
