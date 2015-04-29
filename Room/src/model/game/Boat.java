package model.game;

import model.user.AbstractUser;

public class Boat {

	private int id;

	private Match match;
	private AbstractUser owner;

	private int size;
	private int hp;

	private Orientation orientation;
	private Position position;

	public Boat(Match match, AbstractUser owner, int size, int hp,
			Orientation orientation, Position position) {
		this.id = createId(match, owner, position);
		this.match = match;
		this.size = size;
		this.hp = hp;
		this.orientation = orientation;
		this.position = position;
	}
	
	public Boat(Match match, AbstractUser owner, int size, int hp,
			Orientation orientation, Position position, int id) {
		this.id = id;
		this.match = match;
		this.size = size;
		this.hp = hp;
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

	public Match getMatch() {
		return match;
	}

	public AbstractUser getOwner() {
		return owner;
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
		TOP(0, "nord"), LEFT(1, "ouest"), BOTTOM(2, "sud"), RIGHT(3, "est");

		private final int value;
		private final String strValue;

		private Orientation(int value, String strValue) {
			this.value = value;
			this.strValue = strValue;
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

		public static Orientation fromString(String str) {
			if (str.equals(TOP.getStringValue())) {
				return TOP;
			} else if (str.equals(BOTTOM.getStringValue())) {
				return BOTTOM;
			} else if (str.equals(LEFT.getStringValue())) {
				return LEFT;
			} else if (str.equals(RIGHT.getStringValue())) {
				return RIGHT;
			} else {
				return null;
			}
		}

		public String getStringValue() {
			return strValue;
		}
	}
	
	public static int createId(Match match, AbstractUser owner, Position pos) {
		return match.getId() + owner.getPseudo().hashCode() + pos.hashCode();
	}
}
