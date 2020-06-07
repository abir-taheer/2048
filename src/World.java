import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import tiles.Tile;

public class World extends ActorWorld {
	public World(){
		super();
	}

	public Tile[][] getColumns(boolean reverse){
		Grid grid = getGrid();

		Tile[][] columns = new Tile[4][4];

		for (int column = 0; column < 4; column++) {

			for (int row = 0; row < 4; row++) {

				int actualRow = reverse ? 4 - row : row;

				columns[column][row] = (Tile) grid.get(new Location(column, actualRow));
			}
		}

		return columns;
	}

	public Tile[][] getRows(boolean reverse){
		Grid grid = getGrid();

		Tile[][] rows = new Tile[4][4];

		for (int row = 0; row < 4; row++) {

			for (int column = 0; column < 4; column++) {

				int actualColumn = reverse ? 4 - column : column;

				rows[row][column] = (Tile) grid.get(new Location(actualColumn, row));
			}
		}

		return rows;
	}
}
