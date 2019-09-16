/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-present IxorTalk CVBA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ixortalk.assetstate.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.ixortalk.test.oauth2.Auth0TestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static java.lang.String.format;

@EnableWebSecurity
@Configuration
@Order(TestSecurityConfig.OVERRULE_DEFAULT_WEB_SECURITY_CONFIG_ORDER)
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    static final int OVERRULE_DEFAULT_WEB_SECURITY_CONFIG_ORDER = 99;

    @Value(value = "${security.oauth2.resource.id}")
    private String audience;

    @Value(value = "${ixortalk.auth0.domain}")
    private String auth0Domain;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(audience, format("https://%s/", auth0Domain), new Auth0TestAuthenticationProvider())
                .configure(http)
                .authorizeRequests()
                .anyRequest().authenticated();
    }

}