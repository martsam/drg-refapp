package se.callistasoftware.drg.configuration.se.callistasoftware.drg.processors;

import se.callistasoftware.drg.domain.model.apiconfiguration.ApiConfiguration;
import se.callistasoftware.drg.domain.model.apiconfiguration.DefaultApiConfiguration;
import se.callistasoftware.drg.util.MapUtil;

public class ExampleProcessorConfigurationImpl extends DefaultApiConfiguration implements ExampleProcessorConfiguration {

	public ExampleProcessorConfigurationImpl(ApiConfiguration apiConfiguration) {
		super(apiConfiguration);
	}

	@Override
	public String getCompanyName() {
		return MapUtil.get(this.asMap(), "api.company.name");
	}
}
