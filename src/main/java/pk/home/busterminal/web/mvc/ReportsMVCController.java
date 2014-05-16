package pk.home.busterminal.web.mvc;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sourceforge.barbecue.env.Environment;
import net.sourceforge.barbecue.env.EnvironmentFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pk.home.busterminal.application.Config;
import pk.home.busterminal.domain.Division;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.BalanceService;
import pk.home.busterminal.service.DivisionService;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.service.RaceService;
import pk.home.busterminal.service.OrderService.FindOrdersOrderByBusRouteStopsResult;
import pk.home.libs.combine.fileutils.FileUtils;

/**
 * Контроллер отчетов
 * 
 * @author povloid
 * 
 */
@Controller
@RequestMapping("/report/")
public final class ReportsMVCController {

	private static final Logger LOG = Logger.getLogger(ReportsMVCController.class);
	
	private final DateFormat dateFormatFullTime = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss", new Locale("ru"));

	private final DateFormat dateFormatShortDate = new SimpleDateFormat(
			"dd.MM.yyyy", new Locale("ru"));

	@Autowired
	OrderService orderService;

	@Autowired
	RaceService raceService;

	@Autowired
	DivisionService divisionService;

	@Autowired
	BalanceService balanceService;

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	public ModelAndView generatePdfReport(ModelAndView modelAndView)
			throws Exception {

		LOG.debug("***** 1");
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", "pdf");
		parameterMap.put("capton_params", "Privet - Привет");

		List<String> list = new ArrayList<String>();
		list.add("2222222");
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		modelAndView = new ModelAndView("orderReport", parameterMap);
		
		LOG.debug("***** " + modelAndView);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "xls")
	public ModelAndView generateXlsReport(ModelAndView modelAndView)
			throws Exception {

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", "xls");

		List<String> list = new ArrayList<String>();
		list.add("2222222");
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		modelAndView = new ModelAndView("orderReport", parameterMap);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "html")
	public ModelAndView generateHtmlReport(ModelAndView modelAndView)
			throws Exception {

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", "html");

		List<String> list = new ArrayList<String>();
		list.add("2222222");
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		modelAndView = new ModelAndView("orderReport", parameterMap);

		return modelAndView;
	}

	// простой вариант
	// -----------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/images/{yyyy:.*}/{mm:.*}/{dd:.*}/{htmlImageUniqueSuffix:.*}/{file:.*}", method = RequestMethod.GET)
	protected void image(@PathVariable String file, @PathVariable String yyyy,
			@PathVariable String mm, @PathVariable String dd,
			@PathVariable String htmlImageUniqueSuffix,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// get the filename from the "file" parameter

		if (file == null || file.equals("")) {
			throw new ServletException(
					"Invalid or non-existent file parameter in Image servlet.");
		}

		// Устранение уязвимости
		file = file.replace("../", "");

		// add the .pdf suffix if it doesn't already exist

		BufferedInputStream buf = null;
		FileInputStream in = null;
		OutputStream out = null;

		try {

			out = response.getOutputStream();
			File pdf = new File(Config.BASE_FILES_DIR + "/" + yyyy + "/" + mm
					+ "/" + dd + "/" + htmlImageUniqueSuffix + "/" + file);

			int index = 0;
			while (!pdf.exists()) {
				Thread.sleep(1000);
				System.out.println(index++);
				if (index == 50) {
					break;
				}
			}

			// set response headers

			// Get the absolute path of the image
			// /ServletContext sc = request.getSession().getServletContext();
			// //String filename = sc.getRealPath("" + pdf);

			// ////////System.out.println(filename);

			// Get the MIME type of the image
			// String mimeType = sc.getMimeType(pdf.getAbsolutePath());
			String mimeType = "image/gif";

			// ////////System.out.println(mimeType);

			// ////////sc.log("MIME TYPE: " + mimeType);

			// /if (mimeType == null) {
			// / sc.log("Could not get MIME type of " + pdf.getAbsolutePath());
			// /
			// response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// / return;
			// /}

			// Set content type
			response.setContentType(mimeType);

			// Это раскоментировать если сделать как атачмент
			// response.addHeader("Content-Disposition", "attachment; filename="
			// + fileName);

			response.setContentLength((int) pdf.length());

			FileInputStream input = new FileInputStream(pdf);
			buf = new BufferedInputStream(input);
			int readBytes = 0;

			// read from the file; write to the ServletOutputStream
			while ((readBytes = buf.read()) != -1)
				out.write(readBytes);

		} catch (IOException ioe) {
			System.out.println("ERROR: " + ioe.getMessage());

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());

		} finally {

			if (out != null)
				out.close();

			if (in != null)
				in.close();

			if (buf != null)
				buf.close();
		}
	}

	/**
	 * Отрисовать и вывести отчет
	 * 
	 * @param format
	 * @param report
	 * @param parameterMap
	 * @param JRdataSource
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void renderReport(String format, String htmlImageUniqueSuffix,
			JasperReport report, Map<String, Object> parameterMap,
			JRDataSource JRdataSource, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OutputStream out = response.getOutputStream();

		Writer wr = new OutputStreamWriter(out);

		try {
			if (format.equals("html")) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");

				Map<JRExporterParameter, Object> parameters = new HashMap<JRExporterParameter, Object>();

				// String root = request.getSession().getServletContext()
				// .getRealPath("/");

				String uriPref = FileUtils.getCurentTimeDirsPath()
						+ htmlImageUniqueSuffix;

				String undir = Config.BASE_FILES_DIR + uriPref;

				FileUtils.mkDirs(undir);

				parameters
						.put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
								true);
				// parameters
				// .put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.CHARACTER_ENCODING,
				// "UTF-8");

				parameters
						.put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.IMAGES_DIR,
								new File(undir));

				// System.out.println(undir);

				parameters
						.put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.IMAGES_URI,
								"/busterminal/mvc/report/images" + uriPref
										+ "/");

				JasperReportsUtils.renderAsHtml(report, parameterMap,
						JRdataSource, wr, parameters);

			} else if (format.equals("pdf")) {
				JasperReportsUtils.renderAsPdf(report, parameterMap,
						JRdataSource, out);
			} else if (format.equals("xls")) {
				JasperReportsUtils.renderAsXls(report, parameterMap,
						JRdataSource, out);
			} else if (format.equals("csv")) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");

				Map<JRExporterParameter, Object> parameters = new HashMap<JRExporterParameter, Object>();

				parameters
						.put(net.sf.jasperreports.engine.export.JRCsvMetadataExporterParameter.CHARACTER_ENCODING,
								"UTF-8");

				JasperReportsUtils.renderAsCsv(report, parameterMap,
						JRdataSource, wr, parameters);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (wr != null)
				wr.close();

			if (out != null)
				out.close();
		}

	}

	private ClassPathResource resourceOrderReport = new ClassPathResource(
			"reports/order.jrxml");

	private ClassPathResource resourceTicket = new ClassPathResource(
			"reports/ticket.jrxml");

	private ClassPathResource resourceDrive_report_form2 = new ClassPathResource(
			"reports/drive_report_for_seat.jrxml");

	private ClassPathResource resourceDivision_balance1Report = new ClassPathResource(
			"reports/division_balance1.jrxml");

	private ClassPathResource resourseBFont = new ClassPathResource(
			"net/sf/jasperreports/fonts/dejavu/DejaVuSansMono.ttf");

	private ClassPathResource resourceDrive_report_form1 = new ClassPathResource(
			"reports/drive_report_form1.jrxml");

	private JasperReport orderReport;
	private JasperReport ticketReport;
	private JasperReport driver_form2Report;
	private JasperReport driver_form1Report;
	private JasperReport division_balance1Report;

	{

		EnvironmentFactory.setDefaultEnvironment(new Environment() {

			// public final Font DEFAULT_FONT = new Font("Arial", Font.BOLD,
			// 20);
			// public final Font DEFAULT_FONT = new Font("DejaVu Sans Mono",
			// Font.PLAIN, 20);

			public Font DEFAULT_FONT;

			int resolution;

			{
				try {
					Font font = Font.createFont(Font.TRUETYPE_FONT,
							resourseBFont.getFile());
					font = font.deriveFont(Font.PLAIN, 20);
					DEFAULT_FONT = font;

					System.out.println(">>>>>>" + DEFAULT_FONT);

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					e1.printStackTrace();
				}

				resolution = EnvironmentFactory.getEnvironment()
						.getResolution();
			}

			@Override
			public int getResolution() {
				// return Toolkit.getDefaultToolkit().getScreenResolution();
				return resolution;
			}

			@Override
			public Font getDefaultFont() {
				return DEFAULT_FONT;
			}
		});

		if (EnvironmentFactory.getEnvironment().getDefaultFont() != null)
			System.out.println(">>>>> FONT:"
					+ EnvironmentFactory.getEnvironment().getDefaultFont()
							.toString()
					+ " ---- > "
					+ EnvironmentFactory.getEnvironment().getDefaultFont()
							.getFamily());
		else
			System.out.println(">>>>> FONT: null");

		try {
			orderReport = JasperCompileManager
					.compileReport(resourceOrderReport.getFile()
							.getAbsolutePath());

			ticketReport = JasperCompileManager.compileReport(resourceTicket
					.getFile().getAbsolutePath());

			driver_form2Report = JasperCompileManager
					.compileReport(resourceDrive_report_form2.getFile()
							.getAbsolutePath());

			driver_form1Report = JasperCompileManager
					.compileReport(resourceDrive_report_form1.getFile()
							.getAbsolutePath());

			division_balance1Report = JasperCompileManager
					.compileReport(resourceDivision_balance1Report.getFile()
							.getAbsolutePath());

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Вывод отчета по ордеру
	 * 
	 * @param id
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/{id:.*}/{file:.*}", method = RequestMethod.GET)
	public void generateOrderReport(@PathVariable String id,
			@PathVariable String file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String format = file.substring(file.lastIndexOf(".") + 1);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", format);

		parameterMap.put("capton_params", "Сведения по ордеру №" + id);

		List<Order> list = new ArrayList<Order>();
		// list.add(orderService.find(Long.parseLong(id)));

		Order o = new Order();
		o.setId(Long.parseLong(id));
		o.setDescription("Описание................................");

		list.add(o);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report
		// OUT
		renderReport(format, "order_" + id, orderReport, parameterMap,
				JRdataSource, request, response);

	}

	/**
	 * Вывод билета
	 * 
	 * @param id
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/ticket/{id:.*}/{file:.*}", method = RequestMethod.GET)
	public void generateTicket(@PathVariable Long id,
			@PathVariable String file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LOG.debug("***** 1");
		
		// Запрос данных
		Order o = orderService.find(id);

		// Проверка на корректность
		if (o == null) {
			throw new Exception("Ордер №" + id + " не найден!");
		} else if (o.getOrderType() != OrderType.TICKET_SALE) {
			throw new Exception(
					"Ордер №"
							+ id
							+ " не является продажным, выдать билет по непродажному ордеру невозможно!");
		}

		// Параметры отчета
		// Формат вывода
		String format = file.substring(file.lastIndexOf(".") + 1);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", format);

		// Формирование набора данных
		List<Order> list = new ArrayList<Order>();
		list.add(o);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report
		// OUT
		renderReport(format, "order_" + id, ticketReport, parameterMap,
				JRdataSource, request, response);
	}

	/**
	 * Отчет для водителя - сортировка по номеру сидений
	 * 
	 * @param id
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/driver_form2/{id:.*}/{file:.*}", method = RequestMethod.GET)
	public void generateDrive_report_for_seat(@PathVariable Long id,
			@PathVariable String file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Race race = raceService.find(id);

		// Параметры отчета
		// Формат вывода
		String format = file.substring(file.lastIndexOf(".") + 1);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", format);

		parameterMap.put("CAPTION_PARAMETR", "Рейс №" + race.getId()
				+ " по маршруту " + race.getBusRoute().getKeyName()
				+ " время: " + dateFormatFullTime.format(race.getdTime()));

		// Формирование набора данных
		List<Order> list = orderService.findOrdersBySeatNum(race);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report
		// OUT
		renderReport(format, "drive_report_for_seat_race_" + id,
				driver_form2Report, parameterMap, JRdataSource, request,
				response);

	}

	/**
	 * Отчет водителя - форма 1
	 * 
	 * @param id
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/driver_form1/{id:.*}/{file:.*}", method = RequestMethod.GET)
	public void generateDrive_report_form1(@PathVariable Long id,
			@PathVariable String file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Race race = raceService.find(id);

		// Параметры отчета
		// Формат вывода
		String format = file.substring(file.lastIndexOf(".") + 1);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", format);

		parameterMap.put("CAPTION_PARAMETR", "Рейс №" + race.getId()
				+ " по маршруту " + race.getBusRoute().getKeyName()
				+ " время: " + dateFormatFullTime.format(race.getdTime()));

		// Формирование набора данных
		List<FindOrdersOrderByBusRouteStopsResult> list = orderService
				.findOrdersOrderByBusRouteStops(race);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report
		// OUT
		renderReport(format, "drive_report_form1_race_" + id,
				driver_form1Report, parameterMap, JRdataSource, request,
				response);

	}

	/**
	 * Отчет по операциям подразделения
	 * 
	 * @param id
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/division_balance/{id:.*}/{bDate:.*}/{eDate:.*}/{file:.*}", method = RequestMethod.GET)
	public void generateDivision_balance(@PathVariable Long id,
			@PathVariable Long bDate, @PathVariable Long eDate,
			@PathVariable String file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Division division = divisionService.find(id);

		// Параметры отчета
		// Формат вывода
		String format = file.substring(file.lastIndexOf(".") + 1);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", format);

		Number divCassBalance = orderService
				.findOrdersDivisionBalance(division);
		Number divBalance = balanceService.getBalance(division);

		parameterMap.put("div_cass_balance", divCassBalance);
		parameterMap.put("div_balance", divBalance);

		Date bdate = new Date(bDate);

		Date edate = new Date(eDate);

		parameterMap.put("CAPTION_PARAMETR", "Отчет по операциям организации "
				+ division.getKeyName() + "  за период с "
				+ dateFormatShortDate.format(bdate) + " до "
				+ dateFormatShortDate.format(edate));

		Calendar c = new GregorianCalendar();
		c.setTime(edate);
		c.clear(Calendar.HOUR_OF_DAY);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);
		c.add(Calendar.DAY_OF_MONTH, 1);
		edate = c.getTime();

		// Формирование набора данных
		List<Object[]> list = orderService.findOrdersForDateAndDivisionO(
				division, bdate, edate);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report
		// OUT
		renderReport(format, "division_balance1_" + id,
				division_balance1Report, parameterMap, JRdataSource, request,
				response);
	}

}
