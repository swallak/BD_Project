package model.game;


public abstract class MoveAction extends Action {

	public MoveAction(Turn turn, Boat boat, int numAction) {
		super(turn, boat, numAction);
	}

	public abstract String getType();

	public enum MovementType {
		LEFT("nord"), RIGHT("ouest"), FORWARD("sud"), BACKWARD("est");

		private final String strValue;

		private MovementType(String strValue) {
			this.strValue = strValue;
		}

		public static MovementType fromString(String str) {
			if (str.equals(LEFT.getStringValue())) {
				return LEFT;
			} else if (str.equals(RIGHT.getStringValue())) {
				return RIGHT;
			} else if (str.equals(FORWARD.getStringValue())) {
				return FORWARD;
			} else if (str.equals(BACKWARD.getStringValue())) {
				return BACKWARD;
			} else {
				return null;
			}
		}

		public String getStringValue() {
			return strValue;
		}
	}
	
	public boolean isShot() {
		return false;
	}
	
	public boolean isMove() {
		return true;
	}
}
