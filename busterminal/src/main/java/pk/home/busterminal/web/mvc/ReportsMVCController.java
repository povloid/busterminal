package pk.home.busterminal.web.mvc;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
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

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.service.OrderService;

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
	private void renderReport(String format, JasperReport report,
			Map<String, Object> parameterMap, JRDataSource JRdataSource,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Writer wr = new OutputStreamWriter(response.getOutputStream());

		if (format.equals("html")) {
			Map<JRExporterParameter, Object> parameters = new HashMap<JRExporterParameter, Object>();
			parameters
					.put(net.sf.jasperreports.engine.export.JRHtmlExporterParameter.IMAGES_URI,
							"/busterminal/images/report/");

			JasperReportsUtils.renderAsHtml(report, parameterMap, JRdataSource,
					wr, parameters);
		} else if (format.equals("pdf")) {
			JasperReportsUtils.renderAsPdf(report, parameterMap, JRdataSource,
					response.getOutputStream());
		} else if (format.equals("xls")) {
			JasperReportsUtils.renderAsXls(report, parameterMap, JRdataSource,
					response.getOutputStream());
		} else if (format.equals("csv")) {
			JasperReportsUtils.renderAsCsv(report, parameterMap, JRdataSource,
					wr);
		}

		wr.close();

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
		//list.add(orderService.find(Long.parseLong(id)));
		
		Order o = new Order();
		o.setDescription("Описание................................");
		
		list.add(o);
		
		
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);
		parameterMap.put("datasource", JRdataSource);

		// Compile the report

		ClassPathResource resource = new ClassPathResource(
				"reports/order.jrxml");

		JasperReport report = JasperCompileManager.compileReport(resource
				.getFile().getAbsolutePath());

		// OUT
		renderReport(format, report, parameterMap, JRdataSource, request,
				response);

	}

}
