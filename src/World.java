import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import tiles.Tile;
import tiles.Tile2;

public class World extends ActorWorld {
	boolean gameOver = false;
	int score = 0;

	public boolean keyPressed(String description, Location loc) {
		if (!gameOver) {
			makeMove(description);
		} else if (description.equals("R")) {
			gameOver = false;
			setup();
		}
		return true;
	}

	public void setup() {
		setMessage("Use the arrow keys on your keyboard to play.");
		Grid<Actor> grid = getGrid();
		ArrayList<Location> locations = grid.getOccupiedLocations();

		for (Location loc : locations) {
			grid.get(loc).removeSelfFromGrid();
		}

		for (int i = 0; i < 16; i++) {
			if (i == 15) {
				this.add(new Tile2());
			} else {
				this.add(new Tile());
			}
		}
	}

	public void refreshTiles() {
		Grid grid = getGrid();

		ArrayList locations = grid.getOccupiedLocations();

		for (int i = 0; i < locations.size(); i++) {
			Location loc = (Location) locations.get(i);

			Tile tile = (Tile) grid.get(loc);
			tile.removeSelfFromGrid();
			Tile newTile = tile.getRefreshedTile();
			newTile.putSelfInGrid(grid, loc);
		}
	}

	public void makeMove(String direction) {
		int dir = 0;

		if (direction.equals("DOWN")) {
			dir = Location.SOUTH;
		}
		if (direction.equals("UP")) {
			dir = Location.NORTH;
		}
		if (direction.equals("RIGHT")) {
			dir = Location.EAST;
		}
		if (direction.equals("LEFT")) {
			dir = Location.WEST;
		}
		if (
			direction.equals("DOWN") ||
			direction.equals("UP") ||
			direction.equals("LEFT") ||
			direction.equals("RIGHT")
		) {
			shiftValues(dir);

			refreshTiles();

			setMessage("Score: " + score);

			checkWin();

			ArrayList<Tile> emptyTiles = getEmptyTiles();

			if (emptyTiles.size() > 0) {
				generateTile(emptyTiles);
			}

			checkGameOver();
		}
	}

	public void shiftValues(int dir) {
		Grid<Actor> grid = getGrid();

		boolean isReversed = dir == Location.SOUTH || dir == Location.EAST;

		boolean isVertical = dir == Location.SOUTH || dir == Location.NORTH;

		for (int outer = 0; outer < 4; outer++) {
			int actualOuter = isReversed ? 3 - outer : outer;

			for (int inner = 0; inner < 4; inner++) {
				int actualInner = isReversed ? 3 - inner : inner;
				Location loc = isVertical
					? new Location(actualInner, actualOuter)
					: new Location(actualOuter, actualInner);

				boolean canExit = false;

				Location adj = loc.getAdjacentLocation(dir);

				while (grid.isValid(adj) && !canExit) {
					Tile cur = (Tile) grid.get(loc);
					Tile next = (Tile) grid.get(adj);
					if (next.getValue() == 0) {
						next.removeSelfFromGrid();
						cur.moveTo(adj);
						next.putSelfInGrid(grid, loc);
					} else if (
						!next.willChange() && next.getValue() == cur.getValue()
					) {
						score += cur.getValue();
						next.setValue(next.getValue() + cur.getValue());
						cur.setValue(0);
					} else {
						canExit = true;
					}
					loc = adj;
					adj = adj.getAdjacentLocation(dir);
				}
			}
		}
	}

	public ArrayList<Tile> getEmptyTiles() {
		Grid<Actor> grid = getGrid();
		ArrayList<Tile> emptyTiles = new ArrayList<>();

		for (Location loc : grid.getOccupiedLocations()) {
			Tile tile = (Tile) grid.get(loc);
			if (tile.getValue() == 0) {
				emptyTiles.add(tile);
			}
		}
		return emptyTiles;
	}

	public void generateTile(ArrayList<Tile> emptyTiles) {
		int randomIndex = (int) Math.floor(Math.random() * emptyTiles.size());
		Tile tile = emptyTiles.get(randomIndex);
		Location loc = tile.getLocation();
		tile.removeSelfFromGrid();
		Tile newTile = new Tile2();
		newTile.putSelfInGrid(getGrid(), loc);
	}

	public void checkGameOver() {
		if (isGameOver()) {
			setMessage(
				"Game is over. Final Score: " + score + ". Press R to restart."
			);
			boolean response = confirmDialog(
				"Game Over",
				"You have lost. Final score: " +
				score +
				".\n Do you want to replay?"
			);
			if (response) {
				this.setup();
			} else {
				gameOver = true;
			}
		}
	}

	public boolean isGameOver() {
		ArrayList<Tile> emptyTiles = getEmptyTiles();

		if (emptyTiles.size() > 0) {
			return false;
		}

		Grid<Actor> grid = getGrid();
		int[] directions = new int[] {
			Location.NORTH,
			Location.SOUTH,
			Location.EAST,
			Location.WEST,
		};

		ArrayList<Location> tiles = grid.getOccupiedLocations();
		for (Location loc : tiles) {
			Tile cur = (Tile) grid.get(loc);

			for (int dir : directions) {
				Location adj = loc.getAdjacentLocation(dir);
				if (grid.isValid(adj)) {
					Tile next = (Tile) grid.get(adj);

					if (
						!next.willChange() && next.getValue() == cur.getValue()
					) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public void checkWin() {
		if (isWinner()) {
			setMessage(
				"You won! Final score: " + score + ". Press R to restart."
			);
			boolean response = confirmDialog(
				"Winner",
				"Congrats! You've reached 2048. Final score: " +
				score +
				".\n Do you want to replay?"
			);
			if (response) {
				this.setup();
			} else {
				gameOver = true;
			}
		}
	}

	public boolean isWinner() {
		Grid<Actor> grid = getGrid();
		ArrayList<Location> locs = grid.getOccupiedLocations();

		for (Location loc : locs) {
			Tile tile = (Tile) grid.get(loc);
			if (tile.getValue() == 2048) {
				return true;
			}
		}

		return false;
	}
}
