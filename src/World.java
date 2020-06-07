import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import tiles.Tile;

import java.util.ArrayList;

public class World extends ActorWorld {
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
		int dir = 0;

		if(direction.equals("DOWN")){
			dir = Location.SOUTH;
		}
		if(direction.equals("UP")){
			dir = Location.NORTH;

		}
		if(direction.equals("RIGHT")){
			dir = Location.EAST;
		}
		if(direction.equals("LEFT")){
			dir = Location.WEST;
		}

		shiftValues(dir);

		refreshTiles();

		ArrayList<Tile> emptyTiles = getEmptyTiles();

		if(emptyTiles.size() > 0){
			generateTile(emptyTiles);
		} else {
			System.out.println("User lost");
		}
	}

	public void shiftValues(int dir) {
		Grid<Actor> grid = getGrid();

		boolean isReversed = dir == Location.SOUTH || dir == Location.EAST;

		boolean isVertical = dir == Location.SOUTH || dir == Location.NORTH;

		for ( int outer = 0; outer < 4 ; outer ++ ){
			int actualOuter = isReversed  ? 3 - outer : outer;

			for (int inner = 0; inner < 4; inner++) {
				int actualInner = isReversed ? 3 - inner : inner;
				Location loc = isVertical ?
						new Location(actualInner, actualOuter) :
						new Location(actualOuter, actualInner);

				boolean canExit = false;

				Location adj = loc.getAdjacentLocation(dir);

				while (grid.isValid(adj) && ! canExit){
					Tile cur = (Tile) grid.get(loc);
					Tile next = (Tile) grid.get(adj);
					if( next.getValue() == 0 ){
						next.removeSelfFromGrid();
						cur.moveTo(adj);
						next.putSelfInGrid(grid, loc);

					} else if( ! next.willChange() && next.getValue() == cur.getValue() ) {
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


		refreshTiles();

		ArrayList<Tile> emptyTiles = getEmptyTiles();

		if(emptyTiles.size() > 0){
//				generateTile(emptyTiles);
		} else {
			System.out.println("User lost");
		}
	}

	public ArrayList<Tile> getEmptyTiles (){
		Grid<Actor> grid = getGrid();
		ArrayList<Tile> emptyTiles = new ArrayList<>();

		for ( Location loc: grid.getOccupiedLocations()) {
			Tile tile = (Tile) grid.get(loc);
			if(tile.getValue() == 0){
				emptyTiles.add(tile);
			}
		}
		return emptyTiles;
	}

	public void generateTile(ArrayList<Tile> emptyTiles){
		int randomIndex = (int) Math.floor(Math.random() * emptyTiles.size());
		emptyTiles.get(randomIndex).setValue(2);
	}
}
