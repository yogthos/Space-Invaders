package actors;

import game.Stage;

public class InvaderShot extends Shot {

	public InvaderShot(Stage stage) {
		super(stage);
		super.up = false;
		bulletSpeed = 1;
	}

	@Override
	public void act() {
		super.act();
		if (posY > Stage.HEIGHT) {
			setMarkedForRemoval(true);
		}
	}

	@Override
	public void collision(Actor a) {
		if (a instanceof Invader)
			return;
		setMarkedForRemoval(true);
	}
}
