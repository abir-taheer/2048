import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import tiles.Tile;

import java.util.ArrayList;

public class World extends ActorWorld {

	public Tile[][] getColumns(boolean reverse){
		Grid grid = getGrid();

		Tile[][] columns = new Tile[4][4];

		for (int column = 0; column < 4; column++) {

			for (int row = 0; row < 4; row++) {

				int actualRow = reverse ? 3 - row : row;

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

				int actualColumn = reverse ? 3 - column : column;

				rows[row][column] = (Tile) grid.get(new Location(actualColumn, row));
			}
		}

		return rows;
	}

	@Override
	public boolean keyPressed(String description, Location loc) {
		makeMove(description);

		return true;
	}

	public void refreshTiles(){
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

	public void makeMove(String direction){
		Tile[][] order = new Tile[0][];

		if(direction.equals("DOWN")){
			order = getRows(false);
		}
		if(direction.equals("UP")){
			order = getRows(true);
		}
		if(direction.equals("RIGHT")){
			order = getColumns(false);
		}
		if(direction.equals("LEFT")){
			order = getColumns(true);
		}

		if(order.length > 0){
			for (Tile[] tiles: order) {
				shiftValues(tiles);
			}

			refreshTiles();
		}
	}

	public void shiftValues(Tile[] tiles){
		for (int i = 0; i < tiles.length - 1; i++) {
			Tile currentTile = tiles[i];
			Tile nextTile = tiles[i + 1];

			int curTileVal = currentTile.getValue();
			int nextTileVal = nextTile.getValue();

			// If it's an empty tile, don't do anything
			if(curTileVal != 0){

				if(nextTileVal == 0){
					nextTile.setValue(curTileVal);
					currentTile.setValue(0);
				} else if(! currentTile.willChange() && nextTileVal == curTileVal) {
					nextTile.setValue(nextTileVal + curTileVal);
					currentTile.setValue(0);
				}

			}

		}
	}
}
