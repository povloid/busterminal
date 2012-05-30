package pk.home.busterminal.web.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
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

	@RequestMapping(method = RequestMethod.GET, value = "html")
	public ModelAndView generateHtmlReport(ModelAndView modelAndView) {

		System.out.println("REPORT START");
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		List<String> list = new ArrayList<String>();
		list.add("2222222");

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(list);

		parameterMap.put("datasource", JRdataSource);

		// orderHtmlReport bean has ben declared in the jasper-views.xml file
		modelAndView = new ModelAndView("orderHtmlReport", parameterMap);

		
		System.out.println("REPORT END");
		
		return modelAndView;

	}

}
