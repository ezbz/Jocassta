package org.projectx.jocassta.domain;

import java.util.LinkedList;
import java.util.List;

import org.apache.cassandra.thrift.TokenRange;

public class TokenRangeElement {

  private final TokenRange tokenRange;
  public List<EndpointDetailsElement> endpointDetails = new LinkedList<EndpointDetailsElement>();

  public TokenRangeElement(final TokenRange tokenRange) {
    this.tokenRange = tokenRange;
  }

  public String getStarToken() {
    return tokenRange.getStart_token();
  }

  public String getEndToken() {
    return tokenRange.getEnd_token();
  }

  public List<String> getEndpoints() {
    return tokenRange.getEndpoints();
  }

  public List<String> getRpcEndpoints() {
    return tokenRange.getRpc_endpoints();
  }

  public void addEndpointDetails(final EndpointDetailsElement endpointDetails) {
    this.endpointDetails.add(endpointDetails);
  }

  public List<EndpointDetailsElement> getEndpointDetails() {
    return endpointDetails;
  }

}
