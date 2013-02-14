package org.projectx.jocassta;

import org.springframework.test.web.server.request.MockHttpServletRequestBuilder;

public interface RequestBuilderCallback {

  void doInBuilder(MockHttpServletRequestBuilder requestBuilder);

}
