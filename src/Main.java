import info.gridworld.grid.UnboundedGrid;
import tiles.Tile;
import tiles.Tile2;

public class Main {

	public static void main(String[] args) {
		World world = new World();
		for (int i = 0; i < 16; i++) {
			if (i == 15) {
				world.add(new Tile2());
			} else {
				world.add(new Tile());
			}
		}

		world.show();
	}
}
