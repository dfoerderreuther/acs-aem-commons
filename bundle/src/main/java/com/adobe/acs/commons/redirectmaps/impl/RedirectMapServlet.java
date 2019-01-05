/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2013 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.adobe.acs.commons.redirectmaps.impl;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_EXTENSIONS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_SELECTORS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.redirectmaps.models.RedirectMapModel;
import com.google.common.net.MediaType;

/**
 * Servlet for generating an Apache RedirectMap text file from an uploaded file
 * and a list vanity properties in cq:Page and dam:Asset nodes.
 */
@Component(service=Servlet.class,
property= {
SLING_SERVLET_METHODS+"=GET",
SLING_SERVLET_SELECTORS+"=redirectmap",
SLING_SERVLET_EXTENSIONS+"=txt",
SLING_SERVLET_RESOURCE_TYPES+"=acs-commons/components/utilities/redirectmappage"
})
public class RedirectMapServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(RedirectMapServlet.class);
    private static final long serialVersionUID = -3564475196678277711L;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        log.trace("doGet");

        log.debug("Requesting redirect maps from {}", request.getResource());
        RedirectMapModel redirectMap = request.getResource().adaptTo(RedirectMapModel.class);

        response.setContentType(MediaType.PLAIN_TEXT_UTF_8.toString());
        response.getOutputStream().write(redirectMap.getRedirectMap().getBytes(StandardCharsets.UTF_8));
    }
}