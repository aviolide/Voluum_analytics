package ru.desided.campaign_zones_clear_spring.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import ru.desided.campaign_zones_clear_spring.POJO.*;
import ru.desided.campaign_zones_clear_spring.controllers.LoginController;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.cert.CollectionCertStoreParameters;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Auth {

    private static final String UA = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0";
    private final static Logger logger = Logger.getLogger(LoginController.class);
    private static String DATE;

    public AuthProp connect(Login login) throws IOException {

        Date date = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        simpleDate.setTimeZone(TimeZone.getTimeZone("EST"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        DATE = "2017-01-01&to=" + simpleDate.format(date);
        System.out.println(DATE);

        List<Header> headers = Arrays.asList(
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.USER_AGENT, UA),
                new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5"),
                new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
        );
        List<Header> headersFinal = new ArrayList<>(headers);

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(headers)
//                .setProxy(proxy)
                .build();

        HttpPost httpPost = new HttpPost("https://api.voluum.com/auth/access/session");
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.createObjectNode();
        ((ObjectNode) rootNode).put("accessId", login.getApiId());
        ((ObjectNode) rootNode).put("accessKey",login.getApiKey());

        System.out.println(rootNode.toString());
        StringEntity entityBody = new StringEntity(rootNode.toString());
        httpPost.setEntity(entityBody);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        String responseBody = makeRequest(httpClient, httpPost);

        ObjectNode node = new ObjectMapper().readValue(responseBody, ObjectNode.class);
        String TOKEN = node.get("token").textValue();
        System.out.println(TOKEN);

        List<Header> newHeaders = Arrays.asList(
                new BasicHeader("cwauth-token", TOKEN)
        );
        headersFinal.addAll(headers);
        headersFinal.addAll(newHeaders);

        httpClient = HttpClientBuilder.create()
                .setDefaultHeaders(headersFinal)
//                .setProxy(proxy)
                .build();
        AuthProp authProp = new AuthProp();
        authProp.setDATE(DATE);
        authProp.setHttpClient(httpClient);
        return authProp;
    }

    private String makeRequest(HttpClient httpClient, HttpRequestBase requestBase) throws IOException {
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                return null;
            }
        };
        String responseBody = httpClient.execute(requestBase, responseHandler);
        if (responseBody.contains("NOT_UNIQUE")){
            throw new IllegalStateException("name exist");
        }
        return responseBody;
    }
}
