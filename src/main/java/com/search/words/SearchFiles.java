package com.search.words;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.search.service.SearchResponse;
import com.search.service.SearchService;

@Path("/searchFiles")
public class SearchFiles {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilesForWords(@QueryParam("words") List<String> searchWords) throws Exception {
		
		if (searchWords.contains("") || searchWords == null || searchWords.size() == 0) {
			return Response.status(Status.BAD_REQUEST).entity("Please give words to search").build();
		}

		SearchService service = new SearchService();
		String directoryPath = service.getResourceFolder();
		service.getFileList(directoryPath);
		SearchResponse searchResp = null;
		try {
			searchResp = service.searchForWord(searchWords);
		} catch (RuntimeException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Something went wrong please try again later!!!").build();
		}

		return Response.ok().entity(searchResp.toString()).build();
	}
}