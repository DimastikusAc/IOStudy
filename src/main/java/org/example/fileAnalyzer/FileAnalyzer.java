package org.example.fileAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {
    //private File file;
    //String word;

    public boolean isExists(String path){
        File file = new File(path);
        return file.exists();
    }

    public boolean isNotEmpty(String path) throws IOException {
        File file = new File(path);
        try(FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.read() != -1; //Возвращает true если выполняется условие
        }
    }

    public int countWordOccurrences(String path, String word) throws IOException {
        int count = 0;

        try(FileInputStream inputStream = new FileInputStream(path)) {
            long fileLength =new File(path).length();
            byte[] arrayByte = new byte[(int)fileLength];
            int bytesRead = inputStream.read(arrayByte);
            if (bytesRead != fileLength){
                throw new IOException("Failed to read entire file");
            }
            String text = new String(arrayByte);
            int index = 0;
            while((index = text.toLowerCase().indexOf(word, index)) != -1){
                count++;
                index += word.length();
            }
        }
        return count;
    }

    public List<String> sentencesContainingWord(String path, String word) throws IOException{
        List<String> sentencesWithWord = new ArrayList<>();
        try(FileInputStream inputStream = new FileInputStream(path)){
            File file = new File(path);
            byte[] arrayByte = new byte[(int)file.length()];
            int bytesRead = inputStream.read(arrayByte);
            if(file.length() != bytesRead){
                throw new IOException("Failed to read entire file");
            }

            int startSentence = 0;

            for (int i = startSentence; i < arrayByte.length; i++) {
                int lengthSentence = 0;

                if((arrayByte[i] != (byte) '.') && (arrayByte[i] != (byte) '!') && (arrayByte[i] != (byte) '?')) {
                continue;
                } else{
                    lengthSentence = i - startSentence + 1;
                }
                byte[] sentenceByte = new byte[lengthSentence]; // создаем новый массив
                System.arraycopy(arrayByte, startSentence, sentenceByte, 0, lengthSentence);

                String sentence = new String(sentenceByte).trim();
                if(sentence.toLowerCase().contains(word.toLowerCase())){
                    System.out.println(">>>" + sentence);
                    sentencesWithWord.add(sentence);
                }
                startSentence = i + 1;

            }
        }
        return sentencesWithWord;
    }
}
