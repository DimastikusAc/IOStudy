package org.example.fileAnalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileAnalyzerTest {

    File file;
    FileAnalyzer analyzer;

    @BeforeEach
    public void fileAccess() {
       file = new File("D:/Campus/test/story.txt");
       analyzer = new FileAnalyzer();
    }

    //Тест проверка существования такого файла по такому адресу
    @Test
    public void testFileExistsAtGivenPath() {
        assertTrue(analyzer.isExists(file.getPath()),
                "File " + file.getName() + " not found");
    }

    //Тест проверка есть ли содержимое в файле
    @Test
    public  void testFileIsNotEmpty() throws IOException {
        assertTrue(analyzer.isNotEmpty(file.getPath()), "File " + file.getName() + " is Empty");
    }
    //Тест вхождение искомого слова в файле

    @Test
    public void testCountWordOccurrences() throws IOException {
        int numberOfWords = 17;
        int count = analyzer.countWordOccurrences(file.getPath(), "duck");
        System.out.println(count);
        assertEquals(numberOfWords, count);
    }
    //Тест вывод всех предложений в которые входит искомое слово
    @Test
    public void testFindSentencesContainingWord() throws IOException{

        List<String> sentences = analyzer.sentencesContainingWord(file.getPath(), "duck");
        assertEquals(16, sentences.size());
    }
}
