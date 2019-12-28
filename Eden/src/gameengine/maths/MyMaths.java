package gameengine.maths;

/**
 * A class for special mathematical problems (e.g. Interpolation).
 * @author Paul
 *
 */
public class MyMaths {

	public static float linearInterpolation(float startValue, float endValue, float currentTime, float maxTime) {
		if(startValue < endValue) {
			if(currentTime >= maxTime) {
				return endValue;
			} else return (startValue + endValue * (currentTime / maxTime));
		} else {
			if(currentTime >= maxTime) {
				return startValue;
			} else return (startValue - startValue * (currentTime / maxTime));
		}
	}

	/**
	 * Re-maps a given value from a given range to another given range
	 * @param value The given value.
	 * @param start1 Upper bound of the value's current range. 
	 * @param end1 Upper bound of the value's current range.
	 * @param start2 Lower bound of the value's target range.
	 * @param end2 Upper bound of the value's target range:
	 * @return
	 */
	public static float mapValue(float value, float start1, float end1, float start2, float end2) {
		return ((value + start1) / end1) * end2 + start2;
	}

}
