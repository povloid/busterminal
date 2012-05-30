package pk.home.busterminal.web.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер отчетов
 * 
 * @author povloid
 * 
 */
@Controller
@RequestMapping("/report/")
public class ReportsMVCController {

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

}
