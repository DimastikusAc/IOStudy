package org.example.fileManager;

import java.io.*;

public class FileManager {

    public static void main(String[] args) throws IOException {
        String path = "D:\\Campus";
        System.out.println(">>> Number of files found in directories and subdirectories: " + countFiles(path));
        System.out.println(">>> Number of directories and subdirectories: " + countDirs(path));

        String from = "D:\\Campus\\test\\";
        String to = "D:\\Campus\\test2\\";
        //copy(from, to);
        move(from, to);
    }

    public static int countFiles(String path) {

        File[] folderContent = getDirectoryContentsWithCheck(path);

        int count = 0;
        for (int i = 0; i < folderContent.length; i++) {
            if (folderContent[i].isFile()) {
                count++;
            } else {
                String newPath = folderContent[i].getAbsolutePath();
                count += countFiles(newPath);
            }
        }

        return count;

    }

    public static int countDirs(String path) {
        File[] folderContent = getDirectoryContentsWithCheck(path);

        int count = 0;
        for (int i = 0; i < folderContent.length; i++) {
            if (folderContent[i].isDirectory()) {
                count++;
                String pathSubDirectory = folderContent[i].getAbsolutePath();
                count += countDirs(pathSubDirectory);
            }
        }
        return count;
    }

    public static File[] getDirectoryContentsWithCheck(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Directory not found" + path);
            return null;
        }
        if (!file.isDirectory()) {
            System.out.println("Path is not a directory" + path);
            return null;
        }
        File[] folderContent = file.listFiles();

        if (folderContent == null) {
            System.out.println("Access denied: " + path);
        }
        return folderContent;
    }

    public static void copy(String pathFrom, String pathTo) throws IOException {
        File[] folderContentPathFrom = getDirectoryContentsWithCheck(pathFrom);
        getDirectoryContentsWithCheck(pathTo);
        for (int i = 0; i < folderContentPathFrom.length; i++) {
            if (folderContentPathFrom[i].isFile()) {
                try (FileInputStream inputStream = new FileInputStream(folderContentPathFrom[i]);
                     FileOutputStream outputStream = new FileOutputStream(new File(pathTo, folderContentPathFrom[i].getName()))
                     ) {
                         byte[] byteBuffer = new byte[(int) folderContentPathFrom[i].length()];
                         int byteRead = inputStream.read(byteBuffer);
                         if(byteRead != byteBuffer.length){
                             throw new IOException("File read error");
                         }
                         outputStream.write(byteBuffer);

                         System.out.println("File " + folderContentPathFrom[i].getName() + " copied to " + pathTo);
                     }
            } else {
                File dir = new File(pathTo, folderContentPathFrom[i].getName());
                if (!dir.exists()) {
                    dir.mkdir();
                }
                System.out.println("Directory " + File.separator + folderContentPathFrom[i].getName() + " copied to " + pathTo);
                String subPathFrom = folderContentPathFrom[i].getAbsolutePath();
                String subPathTo = dir.getAbsolutePath();
                copy(subPathFrom, subPathTo);
            }
        }
    }

    public static void move(String from, String to) throws IOException {
        copy(from, to);
        delete(from);
    }
    public static void delete(String from){
        File file = new File(from);
        File[] dirContent = file.listFiles();


        for (File content: dirContent){
            if(content.isFile()){
                content.delete();
                System.out.println("File delete " + content.getName() + " from directory " + from );
            } else if(content.isDirectory()){
                File[] subDirContent = content.listFiles();
                if(subDirContent.length == 0){
                    content.delete();
                    System.out.println("Directory " + File.separator + content.getName() + " delete from " + from);
                } else {
                    delete(content.getAbsolutePath());
                    content.delete();
                    System.out.println("Directory " + File.separator + content.getName() + " delete from " + from);
                }
            }
        }

    }
}
