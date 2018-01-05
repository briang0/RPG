package util;

public class DayCycler {

	private float max;
	private float min;
	private float currentTime;
	private float alpha;
	private boolean increasing;
	private int tickTime;
	
	public DayCycler(float max, float min, float currentTime, float alpha, int tickTime){
		this.max = max;
		this.min = min;
		this.currentTime = currentTime;
		this.alpha = alpha;
		this.tickTime = tickTime;
		increasing = true;
	}
	
	public float getCurrentAlpha(){
		
		if (increasing){
			alpha += (1f)/(max/4);
			currentTime += .5f;
		}else{
			alpha -= (1f)/(max/4);
			currentTime += .5f;
		}
		checkIncreasingStatus();
		return alpha;
	}
	
	public void checkIncreasingStatus(){
		if (alpha >= 1){
			increasing = false;
		}else if(alpha <= .3){
			increasing = true;
		}
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean getIncreasing() {
		return increasing;
	}

	public void setIncreasing(boolean increasing) {
		this.increasing = increasing;
	}

	public int getTickTime() {
		return tickTime;
	}

	public void setTickTime(int tickTime) {
		this.tickTime = tickTime;
	}
	
}
