package com.funicorn.basic.common.security.endpoint;

import com.funicorn.basic.common.base.util.JsonUtil;
import com.nimbusds.oauth2.sdk.TokenIntrospectionResponse;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.Audience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.*;

/**
 * @author Aimee
 * @since 2021/11/11 8:36
 */
@Slf4j
public class CustomizeOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private Converter<String, RequestEntity<?>> requestEntityConverter;

    private RestOperations restOperations;

    private static final String AUTHORITY_PREFIX = "SCOPE_";

    public CustomizeOpaqueTokenIntrospector introspectionUri(String introspectionUri) {
        Assert.notNull(introspectionUri, "introspectionUri cannot be null");
        this.requestEntityConverter = this.defaultRequestEntityConverter(URI.create(introspectionUri));
        return this;
    }

    public CustomizeOpaqueTokenIntrospector introspectionClientCredentials(String clientId, String clientSecret) {
        Assert.notNull(clientId, "clientId cannot be null");
        Assert.notNull(clientSecret, "clientSecret cannot be null");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clientId, clientSecret));
        this.restOperations = restTemplate;
        return this;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        RequestEntity<?> requestEntity = this.requestEntityConverter.convert(token);
        if (requestEntity == null) {
            throw new OAuth2IntrospectionException("requestEntityConverter returned a null entity");
        }
        ResponseEntity<String> responseEntity = makeRequest(requestEntity);
        HTTPResponse httpResponse = adaptToNimbusResponse(responseEntity);
        TokenIntrospectionResponse introspectionResponse = parseNimbusResponse(httpResponse);
        TokenIntrospectionSuccessResponse introspectionSuccessResponse = castToNimbusSuccess(introspectionResponse);
        // relying solely on the authorization server to validate this token (not checking
        // 'exp', for example)
        if (!introspectionSuccessResponse.isActive()) {
            log.trace("Did not validate token since it is inactive");
            throw new BadOpaqueTokenException("Provided token isn't active");
        }
        return convertClaimsSet(introspectionSuccessResponse);
    }

    private OAuth2AuthenticatedPrincipal convertClaimsSet(TokenIntrospectionSuccessResponse response) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> claims = response.toJSONObject();
        if (response.getAudience() != null) {
            List<String> audiences = new ArrayList<>();
            for (Audience audience : response.getAudience()) {
                audiences.add(audience.getValue());
            }
            claims.put(OAuth2IntrospectionClaimNames.AUDIENCE, Collections.unmodifiableList(audiences));
        }
        if (response.getClientID() != null) {
            claims.put(OAuth2IntrospectionClaimNames.CLIENT_ID, response.getClientID().getValue());
        }
        if (response.getExpirationTime() != null) {
            Instant exp = response.getExpirationTime().toInstant();
            claims.put(OAuth2IntrospectionClaimNames.EXPIRES_AT, exp);
        }
        if (response.getIssueTime() != null) {
            Instant iat = response.getIssueTime().toInstant();
            claims.put(OAuth2IntrospectionClaimNames.ISSUED_AT, iat);
        }
        if (response.getIssuer() != null) {
            claims.put(OAuth2IntrospectionClaimNames.ISSUER, issuer(response.getIssuer().getValue()));
        }
        if (response.getNotBeforeTime() != null) {
            claims.put(OAuth2IntrospectionClaimNames.NOT_BEFORE, response.getNotBeforeTime().toInstant());
        }
        if (claims.containsKey("scope") && claims.get("scope")!=null) {
            List<String> scopes = JsonUtil.json2List(claims.get("scope").toString(),String.class);
            claims.put(OAuth2IntrospectionClaimNames.SCOPE, scopes);
            for (String scope : scopes) {
                authorities.add(new SimpleGrantedAuthority(AUTHORITY_PREFIX + scope));
            }
        }
        if (claims.containsKey("authorities") && claims.get("authorities")!=null) {
            List<String> responseAuthorities = JsonUtil.json2List(claims.get("authorities").toString(),String.class);
            for (String authority:responseAuthorities) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
    }

    private TokenIntrospectionSuccessResponse castToNimbusSuccess(TokenIntrospectionResponse introspectionResponse) {
        if (!introspectionResponse.indicatesSuccess()) {
            throw new OAuth2IntrospectionException("Token introspection failed");
        }
        return (TokenIntrospectionSuccessResponse) introspectionResponse;
    }

    private TokenIntrospectionResponse parseNimbusResponse(HTTPResponse response) {
        try {
            return TokenIntrospectionResponse.parse(response);
        }
        catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    private HTTPResponse adaptToNimbusResponse(ResponseEntity<String> responseEntity) {
        HTTPResponse response = new HTTPResponse(responseEntity.getStatusCodeValue());
        response.setHeader(HttpHeaders.CONTENT_TYPE, responseEntity.getHeaders().getContentType()!=null ?
                responseEntity.getHeaders().getContentType().toString() : MediaType.APPLICATION_JSON.toString());
        response.setContent(responseEntity.getBody());
        if (response.getStatusCode() != HTTPResponse.SC_OK) {
            throw new OAuth2IntrospectionException("Introspection endpoint responded with " + response.getStatusCode());
        }
        return response;
    }

    private ResponseEntity<String> makeRequest(RequestEntity<?> requestEntity) {
        try {
            return this.restOperations.exchange(requestEntity, String.class);
        }
        catch (Exception ex) {
            throw new OAuth2IntrospectionException(ex.getMessage(), ex);
        }
    }

    private Converter<String, RequestEntity<?>> defaultRequestEntityConverter(URI introspectionUri) {
        return (token) -> {
            HttpHeaders headers = requestHeaders();
            MultiValueMap<String, String> body = requestBody(token);
            return new RequestEntity<>(body, headers, HttpMethod.POST, introspectionUri);
        };
    }

    private HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private MultiValueMap<String, String> requestBody(String token) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        return body;
    }

    private URL issuer(String uri) {
        try {
            return new URL(uri);
        }
        catch (Exception ex) {
            throw new OAuth2IntrospectionException(
                    "Invalid " + OAuth2IntrospectionClaimNames.ISSUER + " value: " + uri);
        }
    }
}
