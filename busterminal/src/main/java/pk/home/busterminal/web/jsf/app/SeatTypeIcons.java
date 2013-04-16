package pk.home.busterminal.web.jsf.app;

import java.io.File;
import java.io.Serializable;
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
public class SeatTypeIcons implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5041856025249530701L;

	public static final String ICON_FOLDER = "/images/seats/";

	public static final String BLOCK_SEAT_KEY = "BLOCK_SEAT_KEY";
	public static final String BLOCK_SEAT = ICON_FOLDER + "op/seat_block";
	public static final String BLOCK_SEAT_32 = ICON_FOLDER
			+ "op/seat_block_32.png";
	public static final String BLOCK_SEAT_64 = ICON_FOLDER
			+ "op/seat_block_64.png";
	public static final String BLOCK_SEAT_128 = ICON_FOLDER
			+ "op/seat_block_128.png";
	public static final String BLOCK_SEAT_256 = ICON_FOLDER
			+ "op/seat_block_256.png";

	public static final String RED_SEAT_KEY = "RED_SEAT_KEY";
	public static final String RED_SEAT = ICON_FOLDER + "op/seat_red";
	public static final String RED_SEAT_32 = ICON_FOLDER + "op/seat_red_32.png";
	public static final String RED_SEAT_64 = ICON_FOLDER + "op/seat_red_64.png";
	public static final String RED_SEAT_128 = ICON_FOLDER
			+ "op/seat_red_128.png";
	public static final String RED_SEAT_256 = ICON_FOLDER
			+ "op/seat_red_256.png";

	public static final String YELLOW_SEAT_KEY = "YELLOW_SEAT_KEY";
	public static final String YELLOW_SEAT = ICON_FOLDER + "op/seat_yellow";
	public static final String YELLOW_SEAT_32 = ICON_FOLDER
			+ "op/seat_yellow_32.png";
	public static final String YELLOW_SEAT_64 = ICON_FOLDER
			+ "op/seat_yellow_64.png";
	public static final String YELLOW_SEAT_128 = ICON_FOLDER
			+ "op/seat_yellow_128.png";
	public static final String YELLOW_SEAT_256 = ICON_FOLDER
			+ "op/seat_yellow_256.png";

	private static String foolIconsFolderPath = null;
	private static Map<String, String> picsFiles = new HashMap<String, String>();

	
	private static void initPath() {

		// SecurityContextHolderAwareRequestWrapper request =
		// (SecurityContextHolderAwareRequestWrapper) FacesContext
		// .getCurrentInstance().getExternalContext().getRequest();
		// System.out.println(">>>>" + request.getRealPath("/"));

		String root = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/");

		foolIconsFolderPath = root + ICON_FOLDER + "all/";

		System.out.println(">>>>" + foolIconsFolderPath);

		File actual = new File(foolIconsFolderPath);
		for (File f : actual.listFiles()) {
			System.out.println(">>>>" + f.getName() + " - "
					+ f.getAbsolutePath());

			String fileName = f.getName().replace("_32.png", "")
					.replace("_64.png", "").replace("_128.png", "")
					.replace("_256.png", "");

			if (!picsFiles.containsValue(fileName))
				picsFiles.put(fileName, ICON_FOLDER + "all/" + fileName);
		}
		picsFiles.put(BLOCK_SEAT_KEY, BLOCK_SEAT);
		picsFiles.put(RED_SEAT_KEY, RED_SEAT);
		picsFiles.put(YELLOW_SEAT_KEY, YELLOW_SEAT);

	}
	
	
	public SeatTypeIcons() {
		super();
		
		if(foolIconsFolderPath == null)
			initPath();
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
	public static String getIconFromSeat(Seat seat, String suffix) {
		if (seat != null && seat.getSeatType() != null)
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
	public static String getIconFromSeatType(SeatType seatType, String suffix) {
		if (seatType.getSeatIcon() != null)
			return picsFiles.get(seatType.getSeatIcon()) + suffix;
		else
			return "not icon";
	}

	/**
	 * Получить путь к картинке по ключу
	 * 
	 * @param key
	 * @return
	 */
	public static String getIcon(String key) {
		System.out.println(">>> getIcon(" + key + ") ");
		return picsFiles.get(key);
	}

	/**
	 * Получить список иконок по суффиксу
	 * 
	 * @return
	 */
	public static List<String> getPics() {
		List<String> list = new ArrayList<String>();

		for (String s : picsFiles.keySet()) {
			// System.out.println(s);
			if (!s.equals(RED_SEAT_KEY) && !s.equals(YELLOW_SEAT_KEY)
					&& !s.equals(BLOCK_SEAT_KEY)) {
				list.add(s);
			}
		}

		return list;
	}

}
