package com.kylecorp.RestClientApp.rest;

import org.slf4j.Logger;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kylecorp.RestClientApp.logging.LogService;

// Class will house retry logic for REST calls
public class CustomRestTemplate extends RestTemplate
{
	private final static Logger	LOGGER		= LogService.getLogger();
	private static final int	RETRIES		= 4;

	private static final int	SLEEP_TIME	= 2000;

	public CustomRestTemplate()
	{
		init();
	}

	public CustomRestTemplate(final ClientHttpRequestFactory requestFactory)
	{
		super(requestFactory);
		init();
	}

	private <T> T getForObject(final int retries, final String url,
			final Class<T> responseType, final Object... urlVariables)
			throws RestClientException
	{
		try
		{
			return super.getForObject(url, responseType, urlVariables);
		} catch (final HttpServerErrorException e)
		{
			LOGGER.warn("Caught an exception for GET {}", url);
			if (retries > 0)
			{
				try
				{
					Thread.sleep(SLEEP_TIME);
				} catch (final InterruptedException e1)
				{
					Thread.currentThread().interrupt();
					throw e;
				}
				return getForObject(retries - 1, url, responseType,
						urlVariables);
			} else
			{
				throw e;
			}
		}
	}

	@Override
	public <T> T getForObject(final String url, final Class<T> responseType,
			final Object... urlVariables) throws RestClientException
	{
		return getForObject(RETRIES, url, responseType, urlVariables);
	}

	private void init()
	{
		// TODO Auto-generated method stub

	}

}
