package translator.com.client.util;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

public class TimeoutRequestBuilder extends RpcRequestBuilder {
	private final int RPC_TIMEOUT = 7000;

	@Override
	protected RequestBuilder doCreate(String serviceEntryPoint) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, serviceEntryPoint);
		builder.setTimeoutMillis(RPC_TIMEOUT);
		return builder;
	}
}