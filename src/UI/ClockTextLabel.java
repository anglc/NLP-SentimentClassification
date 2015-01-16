package UI;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class ClockTextLabel extends JLabel {
	private double startTime;
	private Timer timer;
	private TimerTask clockTask;

	public ClockTextLabel() {
		super("00 : 00");
	}

	public void start() {
		this.startTime = System.currentTimeMillis();
		this.timer = new Timer();
		this.clockTask = new TimerTask() {

			@Override
			public void run() {
				double endTime = System.currentTimeMillis();
				double second = (endTime - startTime) / 1000;
				int mm, ss;
				mm = (int) second / 60;
				ss = (int) second % 60;
				setText(String.format("%02d : %02d", mm, ss));
				revalidate();
			}

		};
		this.timer.schedule(clockTask, 0, 1000);
	}

	public void stop() {
		this.timer.cancel();
	}
}
