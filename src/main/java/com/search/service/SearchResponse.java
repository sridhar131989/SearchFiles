package com.search.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ser.ToStringSerializer;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse implements Serializable{
	
	private List<String> filenames;
	
	@JsonProperty
	public List<String> getFilename() {
		return filenames;
	}

	public void setFilename(List<String> filenames) {
		this.filenames = filenames;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("filename", filenames).toString();
	}
}
