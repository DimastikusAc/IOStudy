package org.example.fileManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    FileManager fileManager;
    String from = "D:\\Campus\\dirFrom";
    String to = "D:\\Campus\\dirTo";

    @BeforeEach
    public void  creatingFilesAndDirections() throws IOException {
        File pathDirectoryFrom = new File(from, "subDir");
        pathDirectoryFrom.mkdirs();

        File pathDirectoryTo = new File(to);
        pathDirectoryTo.mkdirs();

        File fileOne = new File(from, "fileTestOne.txt");
        fileOne.createNewFile();
        try(OutputStream outputStream = new FileOutputStream(fileOne)){
            outputStream.write("Hello, world!".getBytes());
        }

        File fileTwo = new File(pathDirectoryFrom,"fileTestTwo");
        fileTwo.createNewFile();
        try(OutputStream outputStream = new FileOutputStream(fileTwo)) {
            outputStream.write("This is a text.".getBytes());
        }
        fileManager = new FileManager();
    }

    @AfterEach
    public void deleteFilesAndDirections(){
        File dirFrom = new File(from);
        if(dirFrom.exists()){
            FileManager.delete(from);
        }

        File dirTo = new File(to);
        if(dirTo.exists()){
            FileManager.delete(to);
        }
    }

    @DisplayName("test directories existence")
    @Test
    public void testDirectoriesExistence (){
        File directoryFrom = new File(from);
        File directoryTo = new File(to);
        //assertTrue(directoryFrom.exists());
        assertEquals(from, directoryFrom.getAbsolutePath());
        assertEquals("dirFrom", directoryFrom.getName());
        assertTrue(directoryFrom.exists());
        //assertTrue(directoryTo.exists());
        assertEquals(to, directoryTo.getAbsolutePath());
        assertEquals("dirTo", directoryTo.getName());
    }

    @DisplayName("test directory 'From' is not empty")
    @Test
    public void testDirectoryFromIsNotEmpty(){
        File directoryFrom = new File(from);
        File[] files = directoryFrom.listFiles();
        assertTrue( files.length > 0);
    }

    @DisplayName("test count files in directory and subdirectories")
    @Test
    public void testCountFilesInDirectoryAndSubDirectories(){
        int count = FileManager.countFiles(from);
        assertEquals(2, count);
    }

    @DisplayName("test count subdirectories in directory")
    @Test
    public void testCountSubdirectoriesInDirectory(){
        int count = FileManager.countDirs(from);
        assertEquals(1, count);
    }

    @DisplayName("test copying contents from a source directory to a target directory")
    @Test
    public void testCopyContent()throws IOException{
        FileManager.copy(from, to);
        int countFilesFrom = FileManager.countFiles(from);
        int countDirFrom = FileManager.countDirs(from);
        int countFilesTo = FileManager.countFiles(to);
        int countDirTo = FileManager.countDirs(to);
        assertEquals(2, countFilesFrom);
        assertEquals(countFilesFrom, countFilesTo);
        assertEquals(1, countDirFrom);
        assertEquals(countDirFrom, countDirTo);

    }

    @DisplayName("test delete content from directory and directory itself")
    @Test
    public void testDeleteContentFromDirectoryAndDirectoryItself(){
        File dirFrom = new File(from);
        if(dirFrom.exists()){
            FileManager.delete(from);
        }
        assertFalse(dirFrom.exists());

    }

    @DisplayName("test move files and directories")
    @Test
    public void testMoveFilesAndDirectories() throws IOException {
        FileManager.move(from, to);

        int countFilesTo = FileManager.countFiles(to);
        int countDirTo = FileManager.countDirs(to);

        File dirFrom = new File(from);
        assertFalse(dirFrom.exists());

        assertEquals(2, countFilesTo);
        assertEquals(1, countDirTo);
    }
}
