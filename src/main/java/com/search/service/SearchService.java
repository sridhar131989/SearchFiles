package com.search.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchService {
	public static List<File> listOfFilesInConfiguredDirectory = new ArrayList<>();
	
	/**
	 * Method to get Configured Folder
	 * In our case it is resource folder
	 * @return
	 */
	public String getResourceFolder() {
        ClassLoader classLoader = SearchService.class.getClassLoader();
        File file = new File(classLoader.getResource("search").getFile());
        return file.getPath();
    }

    /**
     * Method to Get All the files in the 
     * Configured folder
     * @param path
     */
    public void getFileList(String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        List<File> fileList = new ArrayList<>();

        if (list == null) return;

        for (File file : list) {
            if (file.isDirectory()) {
                getFileList(file.getAbsolutePath());
            } else {
                fileList.add(file.getAbsoluteFile());
            }
        }
        listOfFilesInConfiguredDirectory.addAll(fileList);
    }

    /**
     * Method to find files for the given word
     * @param inputSearch
     * @return
     */
    public SearchResponse searchForWord(List<String> inputSearch) {
        List<File> filesContainingSearchTerm = new ArrayList<>();
        BufferedReader br;
        String line;
        for (final File file : listOfFilesInConfiguredDirectory) {
            final String path = file.getPath();
            try {
                br = new BufferedReader(new FileReader(path));
                try {
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split(" ");

                        getFilesContainingWords(inputSearch, filesContainingSearchTerm, file, words);

                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Problem while reading the file " + path);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("File is not found in the given path " + path);
            }
        }
        listOfFilesInConfiguredDirectory.clear();
        return getFileNames(filesContainingSearchTerm);
    }

	/**
	 * Method which get the file names and sets in
	 * Response object
	 * @param filesContainingSearchTerm
	 * @return
	 */
	private SearchResponse getFileNames(List<File> filesContainingSearchTerm) {
		List<String> str = new ArrayList<>();
        SearchResponse searchResp = new SearchResponse();
        if (filesContainingSearchTerm != null) {
        	for (File file : filesContainingSearchTerm) {
				str.add(file.getName());
			}
        }
       searchResp.setFilename(str);
       return searchResp;
	}

    private void getFilesContainingWords(List<String> inputSearch, List<File> filesContainingSearchTerm, File file, String[] words) {
        int count = 0;
    	for (String word : words) {
            for (String inputWord : inputSearch) {
                if (word.equals(inputWord)) {
                    count++;
                    if (inputSearch.size() == count) {
                        filesContainingSearchTerm.add(file);
                        System.out.println("File name containing inputWord" + file.getAbsolutePath());
                        count = 0;
                    }
                }
            }
        }
    }
}
