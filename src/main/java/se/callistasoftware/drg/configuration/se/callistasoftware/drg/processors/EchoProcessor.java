package se.callistasoftware.drg.configuration.se.callistasoftware.drg.processors;

import org.apache.commons.lang3.StringEscapeUtils;
import se.callistasoftware.drg.DrgConstants;
import se.callistasoftware.drg.exception.DrgProcessorException;
import se.callistasoftware.drg.machine.DrgMessage;
import se.callistasoftware.drg.machine.Processor;
import se.callistasoftware.drg.machine.ProcessorContext;
import se.callistasoftware.drg.util.DrgMessageUtil;

import java.util.List;
import java.util.Map;

public class EchoProcessor implements Processor {


	@Override
	public void execute(ProcessorContext context) throws DrgProcessorException {
		final DrgMessage message = context.getMessage();
		final String apiConfigurationId = DrgMessageUtil.getApiConfigurationId(message);
		final Map<String, List<String>> headers = (Map<String, List<String>>) message.getProperties().get(DrgConstants.REQUEST_HEADERS);
		final Map<String, List<String>> pathParams = (Map<String, List<String>>) message.getProperties().get(DrgConstants.REQUEST_PATH_PARAMETERS);

		final String method = apiConfigurationId.split("__")[0];
		final String path = apiConfigurationId.split("__")[1];

		StringBuilder sb = new StringBuilder()
				.append("<message>")
					.append("<method>").append(method).append("</method>")
					.append("<path>").append(path).append("</path")
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
					.append(headers.get(pathParam))
					.append("</").append(pathParam).append(">");
		}
		sb
				.append("</pathParams>")
				.append("</message>");

		context.setMessage(message.withBody(sb.toString()));
		context.executeNext();
	}
}
