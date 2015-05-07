package se.callistasoftware.drg.refapp.configuration.processors;

import se.callistasoftware.drg.DrgConstants;
import se.callistasoftware.drg.exception.DrgProcessorException;
import se.callistasoftware.drg.machine.ConfigurableProcessor;
import se.callistasoftware.drg.machine.DrgMessage;
import se.callistasoftware.drg.machine.ProcessorContext;
import se.callistasoftware.drg.util.DrgMessageUtil;

import java.util.List;
import java.util.Map;

public class ExampleProcessor extends ConfigurableProcessor<ExampleProcessorConfiguration> {


	@Override
	public void execute(ProcessorContext context) throws DrgProcessorException {
		final DrgMessage message = context.getMessage();
		final String apiConfigurationId = DrgMessageUtil.getApiConfigurationId(message);
		final Map<String, List<String>> headers = DrgMessageUtil.getRequestHeaders(message);
		final Map<String, String> pathParams = DrgMessageUtil.getPathParameters(message);
		final Map<String, List<String>> queryParams = DrgMessageUtil.getQueryParameters(message);

		final String method = apiConfigurationId.split("__")[0];
		final String path = apiConfigurationId.split("__")[1];

		StringBuilder sb = new StringBuilder()
				.append("<message>")
					.append("<method>").append(method).append("</method>")
					.append("<path>").append(path).append("</path>")
					.append("<companyName>").append(getConfiguration(context).getCompanyName()).append("</companyName>")
					.append("<headers>");

		if (headers != null) {
			for (String header : headers.keySet()) {
				sb
				.append("<").append(header).append(">")
				.append(headers.get(header))
				.append("</").append(header).append(">");
			}
		}
		sb
		.append("</headers>")
		.append("<pathParams>");
		if (pathParams != null) {
			for (String pathParam : pathParams.keySet()) {
				sb
				.append("<").append(pathParam).append(">")
				.append(pathParams.get(pathParam))
				.append("</").append(pathParam).append(">");
			}
		}
		sb
		.append("</pathParams>")
		.append("<queryParams>");
		if (queryParams != null) {
			for (String queryParam : queryParams.keySet()) {
				sb
						.append("<").append(queryParam).append(">")
						.append(queryParams.get(queryParam))
						.append("</").append(queryParam).append(">");
			}
		}
		sb
		.append("</queryParams>")
		.append("</message>");

		context.setMessage(message.withBody(sb.toString()));
		context.executeNext();
	}
}
