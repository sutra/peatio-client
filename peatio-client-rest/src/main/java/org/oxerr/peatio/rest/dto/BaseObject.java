package org.oxerr.peatio.rest.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseObject {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
