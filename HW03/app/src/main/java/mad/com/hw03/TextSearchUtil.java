package mad.com.hw03;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TextSearchUtil {
	private static ArrayList<String> fileLines;
    private static ArrayList<String> result;

    private static void getDataFromFile(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Invalid File Path");
            return;
        }

        BufferedReader inputStream = null;

        try {
            try {
                inputStream = new BufferedReader(new FileReader(filePath));
                String lineContent = null;

                while ((lineContent = inputStream.readLine()) != null) {
                    fileLines.add(lineContent.replaceAll("\t", " "));
                }
            }
            finally {
                if (inputStream != null)
                    inputStream.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void wordSearch(ArrayList<String> fileLines, String keyWord){
        for(int i = 0; i < fileLines.size(); i++){
            String line = fileLines.get(i);
            String[] lineWords = line.toLowerCase().split(" ");

            ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList(lineWords));

            if(wordsList.indexOf(keyWord) > 0){

                int wordIndex = wordsList.indexOf(keyWord);
                int oldIndex = wordIndex;

                while(oldIndex >= 0){
                    int index = TextUtils.join(" ", Arrays.copyOfRange(lineWords, 0, wordIndex)).length();

                    oldIndex = (wordsList.subList(wordIndex + 1, wordsList.size())).indexOf(keyWord);

                    wordIndex = wordIndex + oldIndex + 1;
                    if(index != 0){
                        index = index + 1;
                    }

                    int beginIndex = index - 10;

                    String oldStartString = "";

                    boolean lastWord = false;
                    int lastStringBalance = 0;

                    if(i == fileLines.size() - 1){
                        int availableLength = line.length() - (index + keyWord.length());
                        int requiredLength = 20 - keyWord.length();
                        if(availableLength < requiredLength){
                            lastWord = true;
                            if(index >= requiredLength - availableLength + 10){
                                oldStartString = line.substring(index - requiredLength + availableLength - 10, index);
                            } else{
                                oldStartString = line.substring(0, index);
                                lastStringBalance = requiredLength - availableLength + 10 - oldStartString.length();
                            }
                        }

                    }

                    if(beginIndex < 0 || lastWord){
                        beginIndex = 0;
                        int currentIndex = i;
                        int balanceLength = 10 - index;

                        if(lastWord){
                            balanceLength = lastStringBalance;
                            beginIndex = index;
                        }

                        while(balanceLength > 0 && currentIndex - 1 >= 0){

                            String oldLine = fileLines.get(currentIndex - 1);

                            if(oldLine.length() < balanceLength){
                                oldStartString = oldLine + oldStartString;
                                balanceLength = balanceLength - oldLine.length();
                            } else{
                                oldStartString =  oldLine.substring(oldLine.length() - balanceLength) + oldStartString;
                                balanceLength = 0;
                            }

                            currentIndex = currentIndex - 1;
                        }
                    }

                    if(index == 0 && i != 0){
                        oldStartString = oldStartString.substring(1) + " ";
                    }

                    String startString = oldStartString + line.substring(beginIndex, index);

                    int endIndex = index - startString.length() + 30;

                    String endString;

                    if(endIndex >= line.length()){
                        endString = line.substring(index + keyWord.length());
                    } else{
                        endString = line.substring(index + keyWord.length(), endIndex);
                    }

                    if(endString.equals("") && !lastWord){
                        endString = " ";
                    }

                    String finalString = startString + line.substring(index, index + keyWord.length()) + endString;


                    int currentIndex = i;

                    while(finalString.length() < 30 && currentIndex + 1 < fileLines.size()){

                        String nextLine = fileLines.get(currentIndex + 1);

                        int balanceLength = 30 - finalString.length();

                        if(nextLine.length() < balanceLength){
                            finalString = finalString + nextLine;
                        } else{
                            finalString = finalString + nextLine.substring(0, balanceLength);
                        }

                        currentIndex = currentIndex + 1;
                    }
                    result.add(finalString);
                }
            }
        }
    }

    /**
     * @param filePath Path of the text file
     * @param keyWord Word to search for in the file
     * 
     * @return ArrayList of search results with trailing and leading texts
     * 
     */
    public static ArrayList<String> SearchKeyWordInFile(String filePath, String keyWord) {
        fileLines = new ArrayList<>();
        result = new ArrayList<>();

        getDataFromFile(filePath);

        wordSearch(fileLines, keyWord);
        
        return result;

    }

   /* public static void main(String[] args)
    {
        TextSearchUtil.SearchKeyWordInFile("app/src/main/assets/textfile.txt","box");
    }*/

}
