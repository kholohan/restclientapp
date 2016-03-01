package com.kylecorp.RestClientApp.rest;


import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.kylecorp.RestClientApp.logging.LogService;


@SuppressWarnings("deprecation")
public class RestClient
{
    private final static Logger  LOGGER          = LogService.getLogger();
    private final static boolean ALLOW_UNTRUSTED = true;
    private final static int     DEFAULT_TIMEOUT = 60000;                 // milliseconds

    private RestTemplate         restTemplate;


    @SuppressWarnings({ "deprecation", "resource" })
    // Ignore deprecation warnings for now
    public RestClient()
    {

        final ClientConnectionManager connectionManager = new ThreadSafeClientConnManager();

        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager);

        httpClient.getParams().setParameter("http.socket.timeout", new Integer(DEFAULT_TIMEOUT));

        if (ALLOW_UNTRUSTED)
        {
            // http://stackoverflow.com/questions/1201048/allowing-java-to-use-an-untrusted-certificate-for-ssl-https-connection
            // modified by:
            // http://tech.chitgoks.com/2011/04/24/how-to-avoid-javax-net-ssl-sslpeerunverifiedexception-peer-not-authenticated-problem-using-apache-httpclient/
            // The apache and Sun HTTP clients work a little differently.
            //
            // Normally turning off certificate checking makes the user
            // vulnerable to a man in the middle
            // attack. This is why by default we do not turn off certificate
            // checking. However, if the
            // user wishes to turn off certificate checking and make himself
            // vulnerable to a man in the middle
            // attack, that is the user's business.
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
            {
                public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType)
                {
                }


                public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType)
                {
                }


                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return new X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            try
            {
                final SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                final SSLSocketFactory ssf = new SSLSocketFactory(sc);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                final ClientConnectionManager ccm = httpClient.getConnectionManager();
                final SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", ssf, 443));
                httpClient = new DefaultHttpClient(ccm, httpClient.getParams());
            }
            catch (final Exception e)
            {
                LOGGER.error("Exception: ", e);
                throw new RuntimeException("Could not trust all certificates", e);
            }
        }

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                                                                                                                                   httpClient);
        restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }


    public <T> T restGetOperation(final String url, final Class<T> returnClass, final Object... urlVariables)
    {
        T returnObject = null;
        returnObject = getRestTemplate().getForObject(url, returnClass, urlVariables);
        return returnObject;

    }


    public <T> T restGetOperation(final URI uri, final Class<T> returnClass)
    {
        T returnObject = null;
        returnObject = getRestTemplate().getForObject(uri, returnClass);
        return returnObject;

    }


    public RestTemplate getRestTemplate()
    {
        return restTemplate;
    }
}
