package ttree.pipin.i2c;

/**
 * I2C Register definition for the ITG3200 device.
 * 
 * The ITG3200 is a I2C interfaced 3 axis MEMS gyro from InvenSense.
 *
 * @author Michael Stevens
 */
public class ITG3200 {
	
	public static final int I2C_ADDR_0 = 0x68;
	public static final int I2C_ADDR_1 = 0x69;

	public static final int CONTROLAI_NO = 0x00;
	public static final int CONTROLAI_ALL = 0x80;
	public static final int CONTROLAI_INDIVIDUAL_ONLY = 0xA0;
	public static final int CONTROLAI_GLOBAL_ONLY = 0xC0;
	public static final int CONTROLAI_INDIVIDUAL_AND_GLOBAL = 0xE0;
	
	public static final int REG_WHO_AM_I = 0x00;
	public static final int REG_SMPLRT_DIV = 0x15;
	public static final int REG_DLPF_FS = 0x16;
	public static final int REG_INT_CFG = 0x17;
	public static final int REG_INT_STATUS = 0x1A;
	public static final int REG_TEMP_OUT_H = 0x1B;
	public static final int REG_TEMP_OUT_L = 0x1C;
	public static final int REG_GYRO_XOUT_H = 0x1D;
	public static final int REG_GYRO_XOUT_L = 0x1E;
	public static final int REG_GYRO_YOUT_H = 0x1F;
	public static final int REG_GYRO_YOUT_L = 0x20;
	public static final int REG_GYRO_ZOUT_H = 0x21;
	public static final int REG_GYRO_ZOUT_L = 0x22;
	public static final int REG_PWR_MGM = 0x3E;

	// Reg Who am I default value for B6..B1 and mask
	public static final int WHO_AM_I_VALUE = 0b1101000;
	public static final int WHO_AM_I_MASK  = 0b01111110;
	
}
