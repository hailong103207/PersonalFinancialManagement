package com.example.quanlychitieucanhan;

import java.io.File;

public class TestingMultithreading implements Runnable {
    @Override
    public void run() {

    }
    public String getReadfile(File file)
    {
        return modun.readText(file);
    }
}
