package com.search.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvokeSearch {
	public static List<File> listOfFilesInConfiguredDirectory = new ArrayList<File>();

    public static void main(String[] args) {
        String directoryPath = getResourceFolder();
        List<String> inputSearch = sampleInput();
        getFileList(directoryPath);
        System.out.println(listOfFilesInConfiguredDirectory.size());
        List<File> filesContainingSearchTerm = new ArrayList<File>();
        searchForWord(inputSearch, filesContainingSearchTerm);
        System.out.println("Number of files containing words" + filesContainingSearchTerm.size());
    }

    private static String getResourceFolder() {
        ClassLoader classLoader = InvokeSearch.class.getClassLoader();
        File file = new File(classLoader.getResource("search").getFile());
        return file.getPath();
    }

    private static List<String> sampleInput() {
        List<String> inputSearch = new ArrayList<String>();
        inputSearch.add("saml");
        inputSearch.add("sos");
        inputSearch.add("accert");
        inputSearch.add("search");
        return inputSearch;
    }

    private static void searchForWord(final List<String> inputSearch, final List<File> filesContainingSearchTerm) {
        BufferedReader br;
        String line;
        int count = 0;
        for (final File file : listOfFilesInConfiguredDirectory) {
            final String path = file.getPath();
            try {
                br = new BufferedReader(new FileReader(path));
                try {
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split(" ");

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
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getFileList(String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        List<File> fileList = new ArrayList<File>();

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
}
