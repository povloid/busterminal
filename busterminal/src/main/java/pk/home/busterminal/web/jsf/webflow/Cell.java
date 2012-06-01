package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.web.jsf.app.SeatTypeIcons;

/**
 * Scheme cell class
 * 
 * @author povloid
 */
public class Cell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831224580870450169L;

	short index = 1, x = 0, y = 0;
	private Seat seat;

	public enum OP_TYPE {
		BLOCK, SALE, ARMOR
	}

	private OP_TYPE opType = null;

	/**
	 * Тип отрисовки прогресса
	 * 
	 * @author povloid
	 * 
	 */
	public class ProgressPoint implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int v;
		private String text;

		public ProgressPoint(int v, String text) {
			super();
			this.v = v;
			this.text = text;
		}

		public int getV() {
			return v;
		}

		public void setV(int v) {
			this.v = v;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	private List<ProgressPoint> progress = new ArrayList<ProgressPoint>();
	
	public void addProgressPoint(int v, String text){
		progress.add(new ProgressPoint(v, text));
	}

	public Cell(Cell previusCell, Seat seat, short x, short y) {
		this.index = (short) (previusCell == null ? index
				: previusCell.index + 1);
		this.seat = seat;
		this.x = x;
		this.y = y;
		// icon = SeatTypeIcons.getIconFromSeat(seat, "_128.png");
	}

	private String icon;

	public String getIcon() {
		if (opType == null) {
			icon = SeatTypeIcons.getIconFromSeat(seat, "_128.png");
		} else {
			switch (opType) {
			case BLOCK:
				icon = SeatTypeIcons.getIcon(SeatTypeIcons.YELLOW_SEAT_KEY)
						+ "_128.png";
				break;
			case SALE:
				icon = SeatTypeIcons.getIcon(SeatTypeIcons.RED_SEAT_KEY)
						+ "_128.png";
				break;
			}
		}

		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIndex() {
		return index;
	}

	public short getX() {
		return x;
	}

	public void setX(short x) {
		this.x = x;
	}

	public short getY() {
		return y;
	}

	public void setY(short y) {
		this.y = y;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public OP_TYPE getOpType() {
		return opType;
	}

	public void setOpType(OP_TYPE opType) {
		this.opType = opType;
	}

	public List<ProgressPoint> getProgress() {
		return progress;
	}

	public void setProgress(List<ProgressPoint> progress) {
		this.progress = progress;
	}

}