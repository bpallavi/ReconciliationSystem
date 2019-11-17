/*******************************************************************************
 *  Copyright (c) 2018 ESI Group CONFIDENTIAL
 *   
 *  All Rights Reserved.
 *  
 *  NOTICE:  All information contained herein is, and remains
 *  the property of ESI Group Incorporated and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to ESI Group
 *  and its suppliers and may be covered by U.S. and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from ESI Group.
 *
 * Contributors:
 *    ESI Software India - @author
 *******************************************************************************/
package ReconciliationSystem.ReconciliationSystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class THFile {

	@JsonProperty
	private String path;
	
	
	@JsonProperty
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
