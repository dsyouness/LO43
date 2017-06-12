package lo43p;

public class Configuration {
	private final int workTime, extraWorkTime, breakTime;

	public Configuration(int workTime, int extraWorkTime, int breakTime) {
		this.workTime = workTime;
		this.extraWorkTime = extraWorkTime;
		this.breakTime = breakTime;
	}

	public String getBreakTime() {
		return new String((this.breakTime - this.breakTime % 60) / 60 + "h "
				+ this.breakTime % 60 + "m");
	}

	public int getBreakTimeMinutes() {
		return this.breakTime;
	}

	public String getExtraWorkTime() {
		return new String((this.extraWorkTime - this.extraWorkTime % 60) / 60
				+ "h " + this.extraWorkTime % 60 + "m");
	}

	public int getExtraWorkTimeMinutes() {
		return this.extraWorkTime;
	}

	public String getWorkTime() {
		return new String((this.workTime - this.workTime % 60) / 60 + "h "
				+ this.workTime % 60 + "m");
	}

	public int getWorkTimeMinutes() {
		return this.workTime;
	}
}
