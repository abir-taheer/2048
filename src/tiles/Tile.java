package tiles;

import info.gridworld.actor.Actor;

public class Tile extends Actor {
	private int value;
	private int nextValue = value;

	public Tile(int value){
		super();

		this.value = value;
	}

	public int getValue() {

		return value;
	}

	public void setValue(int value) {

		this.value = value;
	}

	public int getNextValue() {
		return nextValue;
	}

	public void setNextValue(int nextValue) {
		this.nextValue = nextValue;
	}

	public void attemptCombination(Tile nextTile){
		int nextTileVal = nextTile.getValue();

		if(nextTileVal == this.value){
			this.nextValue = 0;
			nextTile.setNextValue(nextTileVal * 2);
		}
	}


}
