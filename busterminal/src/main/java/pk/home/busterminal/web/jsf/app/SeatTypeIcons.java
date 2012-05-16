package pk.home.busterminal.web.jsf.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.SeatType;

@ManagedBean(name = "seatTypeIcons")
@ApplicationScoped
public class SeatTypeIcons {

	public static final String ICON_FOLDER = "/images/seats/all/";

	public static final String RED_SEAT_32 = ICON_FOLDER + "op/seat_red_32.png";
	public static final String RED_SEAT_64 = ICON_FOLDER + "op/seat_red_64.png";
	public static final String RED_SEAT_128 = ICON_FOLDER
			+ "op/seat_red_128.png";
	public static final String RED_SEAT_256 = ICON_FOLDER
			+ "op/seat_red_256.png";

	public static final String YELLOW_SEAT_32 = ICON_FOLDER
			+ "op/seat_yellow_32.png";
	public static final String YELLOW_SEAT_64 = ICON_FOLDER
			+ "op/seat_yellow_64.png";
	public static final String YELLOW_SEAT_128 = ICON_FOLDER
			+ "op/seat_yellow_128.png";
	public static final String YELLOW_SEAT_256 = ICON_FOLDER
			+ "op/seat_yellow_256.png";

	private static String foolIconsFolderPath;
	private static Map<String, String> picsFiles = new HashMap<String, String>();

	static {

		// SecurityContextHolderAwareRequestWrapper request =
		// (SecurityContextHolderAwareRequestWrapper) FacesContext
		// .getCurrentInstance().getExternalContext().getRequest();
		// System.out.println(">>>>" + request.getRealPath("/"));

		String root = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/");

		foolIconsFolderPath = root + ICON_FOLDER;

		System.out.println(">>>>" + foolIconsFolderPath);

		File actual = new File(foolIconsFolderPath);
		for (File f : actual.listFiles()) {
			System.out.println(">>>>" + f.getName() + " - "
					+ f.getAbsolutePath());

			String fileName = f.getName().replace("_32.png", "")
					.replace("_64.png", "").replace("_128.png", "")
					.replace("_256.png", "");

			if (!picsFiles.containsValue(fileName))
				picsFiles.put(fileName, ICON_FOLDER + fileName);
		}

	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public static String getFoolIconsFolderPath() {
		return foolIconsFolderPath;
	}

	/**
	 * Получить иконку из места
	 * 
	 * @param seat
	 * @return
	 */
	public String getIconFromSeat(Seat seat, String suffix) {
		if (seat.getSeatType() != null)
			return getIconFromSeatType(seat.getSeatType(), suffix);
		else
			return "not icon";
	}

	/**
	 * Получить иконку из типа
	 * 
	 * @param seatType
	 * @return
	 */
	public String getIconFromSeatType(SeatType seatType, String suffix) {
		if (seatType.getSeatIcon() != null)
			return picsFiles.get(seatType.getSeatIcon()) + suffix;
		else
			return "not icon";
	}

	/**
	 * Получить список иконок по суффиксу
	 * 
	 * @param suffix
	 * @return
	 */
	public static List<String> getPics() {
		List<String> list = new ArrayList<String>();

		for (String s : picsFiles.keySet()) {
			// if (s.contains(suffix)) {
			list.add(s);
			// }
		}

		return list;
	}

}
