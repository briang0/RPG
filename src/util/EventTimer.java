package util;

public class EventTimer {
	private long start;
	private long stop;
	private long previousTime;
	private int duration;

	public EventTimer(long start, long stop) {
		this.start = start;
		this.stop = stop;
		previousTime = 0;
		duration = (int) (stop-start);
	}
	
	public EventTimer(long start, int duration){
		this.start = start;
		this.duration = duration;
		stop = start + duration;
		previousTime = 0;
	}
	
	public EventTimer(int duration){
		start = System.nanoTime();
		this.duration = duration;
		stop = start + duration;
		previousTime = 0;
	}

	public long getRemainingTime(long currentTime){
		previousTime = currentTime;
		return currentTime - start;
	}
	
	public boolean isComplete(long currentTime){
		return currentTime - start >= start - stop;
	}
	
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getStop() {
		return stop;
	}

	public void setStop(long stop) {
		this.stop = stop;
	}

	public long getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(long previousTime) {
		this.previousTime = previousTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
