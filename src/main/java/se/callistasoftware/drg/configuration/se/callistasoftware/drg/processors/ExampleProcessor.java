package se.callistasoftware.drg.configuration.se.callistasoftware.drg.processors;

import se.callistasoftware.drg.DrgConstants;
import se.callistasoftware.drg.exception.DrgProcessorException;
import se.callistasoftware.drg.machine.DrgMessage;
import se.callistasoftware.drg.machine.ProcessorContext;
import se.callistasoftware.drg.tools.processor.ConfigurableProcessor;
import se.callistasoftware.drg.util.DrgMessageUtil;

import java.util.List;
import java.util.Map;

public class ExampleProcessor extends ConfigurableProcessor<ExampleProcessorConfiguration> {


	@Override
	public void execute(ProcessorContext context) throws DrgProcessorException {
		final DrgMessage message = context.getMessage();
		final String apiConfigurationId = DrgMessageUtil.getApiConfigurationId(message);
		final Map<String, List<String>> headers = (Map<String, List<String>>) message.getProperties().get(DrgConstants.REQUEST_HEADERS);
		final Map<String, List<String>> pathParams = (Map<String, List<String>>) message.getProperties().get(DrgConstants.REQUEST_PATH_PARAMETERS);
		final Map<String, List<String>> queryParams = (Map<String, List<String>>) message.getProperties().get(DrgConstants.REQUEST_QUERY_PARAMETERS);

		final String method = apiConfigurationId.split("__")[0];
		final String path = apiConfigurationId.split("__")[1];

		StringBuilder sb = new StringBuilder()
				.append("<message>")
					.append("<method>").append(method).append("</method>")
					.append("<path>").append(path).append("</path>")
					.append("<companyName>").append(getConfiguration(context).getCompanyName()).append("</companyName>")
					.append("<headers>");
		for (String header : headers.keySet()) {
			sb
				.append("<").append(header).append(">")
				.append(headers.get(header))
				.append("</").append(header).append(">");
		}
		sb
				.append("</headers>")
				.append("<pathParams>");
		for (String pathParam : pathParams.keySet()) {
			sb
					.append("<").append(pathParam).append(">")
					.append(pathParams.get(pathParam))
					.append("</").append(pathParam).append(">");
		}
		sb
				.append("</pathParams>")
				.append("<queryParams>");
		for (String queryParam : queryParams.keySet()) {
			sb
					.append("<").append(queryParam).append(">")
					.append(queryParams.get(queryParam))
					.append("</").append(queryParam).append(">");
		}
		sb
				.append("</queryParams>")
				.append("</message>");

		context.setMessage(message.withBody(sb.toString()));
		context.executeNext();
	}
}
