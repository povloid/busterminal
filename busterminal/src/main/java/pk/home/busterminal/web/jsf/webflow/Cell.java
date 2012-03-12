package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Seat;

/**
 * Scheme cell class
 * @author povloid
 */
public class Cell implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7831224580870450169L;
	
	
	short index = 1, x = 0, y = 0;
	private Seat seat;

	public Cell(Cell previusCell, Seat seat, short x, short y) {
		this.index = (short) (previusCell == null ? index : previusCell.index + 1);
		this.seat = seat;
		this.x = x;
		this.y = y;
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
	
}