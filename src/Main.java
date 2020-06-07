import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import tiles.Tile;

public class Main {
	public static void main(String[] args) {
		ActorWorld world = new World();
		for (int i = 0; i < 16; i++) {
			world.add(new Tile());
		}

		world.show();
	}
}
