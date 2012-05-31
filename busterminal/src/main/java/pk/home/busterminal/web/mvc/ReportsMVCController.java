package pk.home.busterminal.web.mvc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pk.home.busterminal.application.Config;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.service.OrderService;
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

	@Autowired
	OrderService orderService;

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	public ModelAndView generatePdfReport(ModelAndView modelAndView)
			throws Exception {

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("format", "pdf");
		parameterMap.put("capton_params", "Privet - Привет");

		List<String> list = new ArrayList<String>();
		list.add("2222222");
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		modelAndView = new ModelAndView("orderReport", parameterMap);

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

				parameters
						.put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.IMAGES_DIR,
								new File(undir));

				System.out.println(undir);

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
				JasperReportsUtils.renderAsCsv(report, parameterMap,
						JRdataSource, wr);
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

	private ClassPathResource resource = new ClassPathResource(
			"reports/order.jrxml");

	private JasperReport orderReport;

	{

		try {
			orderReport = JasperCompileManager.compileReport(resource.getFile()
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

}
