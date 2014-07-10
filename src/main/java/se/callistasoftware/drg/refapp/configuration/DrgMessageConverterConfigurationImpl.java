package se.callistasoftware.drg.refapp.configuration;

import se.callistasoftware.drg.domain.model.apiconfiguration.ApiConfiguration;
import se.callistasoftware.drg.domain.model.apiconfiguration.DefaultApiConfiguration;
import se.callistasoftware.drg.util.MapUtil;
import se.callistasoftware.drg.web.DrgMessageConverterConfiguration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DrgMessageConverterConfigurationImpl extends DefaultApiConfiguration implements DrgMessageConverterConfiguration {

	public DrgMessageConverterConfigurationImpl(ApiConfiguration apiConfiguration) {
		super(apiConfiguration);
	}

	@Override
	public Set<String> getProducibleMimeTypes() {
		final String produces = MapUtil.get(this.asMap(), "api.endpoint.produces");
		return new HashSet<String>(){{add(produces);}};
	}
}
