package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Race;
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

		public String getColor() {
			switch (v) {
			case 1:
				return "red";
			case 2:
				return "yellow";
			case 3:
				return "red";

			default:
				return "#00FF00";
			}
		}

	}

	private List<ProgressPoint> progress = new ArrayList<ProgressPoint>();

	public void addProgressPoint(int v, String text) {
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

		// Отработка иконки для блокировки
		opType = seat.getBlock() != null && seat.getBlock() && seat.getSeatType().getSold() != null
				&& seat.getSeatType().getSold() ? OP_TYPE.BLOCK
				: null;

		if (opType == null) {
			icon = SeatTypeIcons.getIconFromSeat(seat, "_128.png");
		} else {
			switch (opType) {
			case BLOCK:
				icon = SeatTypeIcons.getIcon(SeatTypeIcons.BLOCK_SEAT_KEY)
						+ "_128.png";
				break;
			case ARMOR:
				icon = SeatTypeIcons.getIcon(SeatTypeIcons.YELLOW_SEAT)
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

	/**
	 * Продано ли место на отъезд от этой остановки
	 * 
	 * @param ilist
	 * @param cell
	 * @param brs
	 * @return
	 */
	private boolean isContain(List<Items> ilist, Cell cell, BusRouteStop brs) {

		for (Items items : ilist) {
			if (items.getSeat().equals(cell.getSeat())) {
				if (brs.equals(items.getBrst1())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Построить прогресс
	 * 
	 * @param race
	 * @param ilist
	 * @param cell
	 */
	public void buidProgress(Race race, List<Items> ilist, Cell cell) {

		// Не строить для непродаваемых мест
		if (!seat.getSeatType().getSold())
			return;

		boolean isSale = false;

		Iterator<BusRouteStop> it = race.getBusRoute().getBusRouteStops()
				.iterator();

		while (it.hasNext()) {
			BusRouteStop brs = it.next();

			if (it.hasNext()) {
				if (isContain(ilist, cell, brs)) {
					cell.addProgressPoint(1, "Занято");
					isSale = true;
				} else {
					cell.addProgressPoint(0, "Незанято");
				}
			}
		}

		if (isSale) {
			cell.setOpType(OP_TYPE.SALE);
		}
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