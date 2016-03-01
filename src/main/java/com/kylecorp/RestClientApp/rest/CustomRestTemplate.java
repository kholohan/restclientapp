package com.kylecorp.RestClientApp.rest;

import org.slf4j.Logger;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kylecorp.RestClientApp.logging.LogService;

//Class will house retry logic for REST calls
public class CustomRestTemplate extends RestTemplate
{
	private final static Logger	LOGGER		= LogService.getLogger();
	private static final int	RETRIES		= 4;

	private static final int	SLEEP_TIME	= 500;

	public CustomRestTemplate()
	{
		init();
	}

	private void init()
	{
		// TODO Auto-generated method stub

	}

	public CustomRestTemplate(final ClientHttpRequestFactory requestFactory)
	{
		super(requestFactory);
		init();
	}

}
