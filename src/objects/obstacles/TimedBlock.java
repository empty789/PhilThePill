package objects.obstacles;

import java.awt.Color;

import objects.ObjectType;

public class TimedBlock extends Block {

	private boolean respawn, activated;
	private int time, ticks;
	private Color defaultC;
	
	public TimedBlock(int x, int y, int width, int height, Color color, boolean respawn, int time) {
		super(x, y, width, height, color);
		this.respawn = respawn;
		this.time = time;
		activated = false;
		setType(ObjectType.TIMEDOBSTACLE);
		defaultC = getColor();

	}

	public void tick() {
		if(!isAlive() && respawn) {
			if(ticks >= 60*time) {
				activated = false;
				ticks = 0;
				setColor(defaultC);
				setAlive(true);
				
			}
			ticks++;
		}else if(activated) {

			if(ticks % (60/time) == 0) {
				setColor(getColor().darker());
				
			}else if(ticks >= 60*time) {
				setAlive(false);
				ticks = 0;
			}
			ticks++;
		}
		
	}

	public boolean isRespawn() {
		return respawn;
	}

	public void setRespawn(boolean respawn) {
		this.respawn = respawn;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	
	
}
