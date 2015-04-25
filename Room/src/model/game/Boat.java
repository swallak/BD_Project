package model.game;

public class Boat {

	private int id;

	private int size;
	private int hp;

	private Orientation orientation;
	private Position position;

	public Boat(int id, int size, Orientation orientation, Position position) {
		this.size = size;
		this.hp = size;
		this.orientation = orientation;
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isTouchBy(Position shot) {
		if (hp > 0) {
			switch (orientation) {
			case BOTTOM:
				if (shot.getX() == position.getX()
						&& position.getY() - (size - 1) <= shot.getY()
						&& shot.getY() <= position.getY())
					return true;
				break;
			case LEFT:
				if (shot.getY() == position.getY()
						&& position.getX() - (size - 1) <= shot.getX()
						&& shot.getX() <= position.getX())
					return true;
				break;
			case RIGHT:
				if (shot.getY() == position.getY()
						&& position.getX() + (size - 1) >= shot.getX()
						&& shot.getX() >= position.getX())
					return true;
				break;
			case TOP:
				if (shot.getX() == position.getX()
						&& position.getY() + (size - 1) >= shot.getY()
						&& shot.getY() >= position.getY())
					return true;
				break;
			default:
				break;
			}
		}
		return false;
	}

	public enum Orientation {
		TOP(0), LEFT(1), BOTTOM(2), RIGHT(3);

		private final int value;

		private Orientation(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Orientation fromInteger(int x) {
			if (x < 0)
				x = -x;
			switch (x % 4) {
			case 0:
				return TOP;
			case 1:
				return LEFT;
			case 2:
				return BOTTOM;
			case 3:
				return RIGHT;
			}
			return null;
		}
	}
}
