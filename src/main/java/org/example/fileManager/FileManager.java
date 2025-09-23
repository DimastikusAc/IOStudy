package org.example.fileManager;

import java.io.*;

public class FileManager {

    public static int countFiles(String path) {

        File[] folderContent = getDirectoryContentsWithCheck(path);

        int count = 0;
        for (File file: folderContent) {
            if (file.isFile()) {
                count++;
            } else {
                String newPath = file.getAbsolutePath();
                count += countFiles(newPath);
            }
        }

        return count;
    }

    public static int countDirs(String path) {
        File[] folderContent = getDirectoryContentsWithCheck(path);

        int count = 0;
        for (File dir: folderContent) {
            if (dir.isDirectory()) {
                count++;
                String pathSubDirectory = dir.getAbsolutePath();
                count += countDirs(pathSubDirectory);
            }
        }
        return count;
    }

    public static void copy(String pathFrom, String pathTo) throws IOException {
        File[] folderContentPathFrom = getDirectoryContentsWithCheck(pathFrom);
        getDirectoryContentsWithCheck(pathTo);
        for (File file: folderContentPathFrom) {
            if (file.isFile()) {
                copyFile(pathFrom, pathTo, file);
            } else {
                File dir  = copyDir(pathFrom, pathTo, file);
                String subPathFrom = file.getAbsolutePath();
                String subPathTo = dir.getAbsolutePath();
                copy(subPathFrom, subPathTo);
            }
        }
    }

    public static void move(String from, String to) throws IOException {
        new File(from).renameTo(new File(to));
        copy(from, to);
        delete(from);
    }

    public static void delete(String from){
        File[] dirContent = getDirectoryContentsWithCheck(from);
        for (File content: dirContent){
            if(content.isFile()){
                content.delete();
                System.out.println("File delete " + content.getName() + " from directory " + from );
            } else if(content.isDirectory()){
                File[] subDirContent = getDirectoryContentsWithCheck(content.getAbsolutePath());
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
        File dir = new File(from);
        dir.delete();
    }

    private static File[] getDirectoryContentsWithCheck(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Directory not found" + path);
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory" + path);
        }
        File[] folderContent = file.listFiles();

        if (folderContent == null) {
            throw new IllegalStateException("Access denied: " + path);
        }
        return folderContent;
    }

    private static void copyFile(String pathFrom, String pathTo, File file) throws IOException {

        try (FileInputStream inputStream = new FileInputStream(file);
             FileOutputStream outputStream = new FileOutputStream(new File(pathTo, file.getName()))) {
            byte[] byteBuffer = inputStream.readAllBytes();
            outputStream.write(byteBuffer);
            System.out.println("File " + file.getName() + " copy from " + pathFrom + " to " + pathTo);
        }

    }

    private static File copyDir(String pathFrom, String pathTo, File file) {

        File dir = new File(pathTo, file.getName());
        if (file.exists() && file.isDirectory()) {
            dir.mkdir();
            System.out.println("Directory " + dir.getName() + " copy from " + pathFrom + " to " + pathTo);
        }
        return dir;
    }
}
