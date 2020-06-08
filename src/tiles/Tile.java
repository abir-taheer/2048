package tiles;

import info.gridworld.actor.Actor;

public class Tile extends Actor {
	protected int originalValue = 0;
	protected int value;


	public Tile() {
		super();
	}

	public Tile getRefreshedTile() {
		if (originalValue == value) {
			return this;
		}

		Tile newTile = new Tile();

		if (value == 2) {
			newTile = new Tile2();
		}

		if (value == 4) {
			newTile = new Tile4();
		}

		if (value == 8) {
			newTile = new Tile8();
		}

		if (value == 16) {
			newTile = new Tile16();
		}

		if (value == 32) {
			newTile = new Tile32();
		}

		if (value == 64) {
			newTile = new Tile64();
		}

		if (value == 128) {
			newTile = new Tile128();
		}

		if (value == 256) {
			newTile = new Tile256();
		}

		if (value == 512) {
			newTile = new Tile512();
		}

		if (value == 1024) {
			newTile = new Tile1024();
		}

		if (value == 2048) {
			newTile = new Tile2048();
		}

		return newTile;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean willChange() {
		return originalValue != value;
	}
}
