package org.projectx.jocassta;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public interface RequestBuilderCallback {

  void doInBuilder(MockHttpServletRequestBuilder requestBuilder);

}
