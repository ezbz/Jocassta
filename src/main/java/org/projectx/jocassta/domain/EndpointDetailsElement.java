package org.projectx.jocassta.domain;

import org.apache.cassandra.thrift.EndpointDetails;

public class EndpointDetailsElement {
  private final EndpointDetails endpointDetails;

  public EndpointDetailsElement(final EndpointDetails endpointDetails) {
    this.endpointDetails = endpointDetails;
  }

  public String getHost() {
    return endpointDetails.getHost();
  }

  public String getDatacenter() {
    return endpointDetails.getDatacenter();
  }

  public String getRack() {
    return endpointDetails.getRack();
  }

}
