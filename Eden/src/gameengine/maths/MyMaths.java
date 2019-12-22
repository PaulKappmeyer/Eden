package gameengine.maths;

public class MyMaths {

	public static float linearInterpolation(float startValue, float endValue, float currentTime, float maxTime) {
		if(currentTime >= maxTime) {
			return endValue;
		} else {
			if(startValue < endValue) {
				return (startValue + endValue * (currentTime / maxTime));
			} else {
				return (startValue - startValue * (currentTime / maxTime));
			}
		}
	}
	
}
