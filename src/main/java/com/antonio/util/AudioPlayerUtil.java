package com.antonio.util;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class AudioPlayerUtil {

    private AudioClip audioClip;

    public void reproduceSong(final String songFileName) {
        
        
        if (songFileName == null) {
            System.err.println("No song available to play.");
            return;
        }

        String resourcePath = "/com/antonio/songs/" + songFileName + ".wav";
        URL resource = getClass().getResource(resourcePath);

        if (resource == null) {
            System.err.println("File not found: " + resourcePath);
            return;
        }

        try {
            this.audioClip = new AudioClip(resource.toExternalForm());
            this.audioClip.setCycleCount(AudioClip.INDEFINITE);
            this.audioClip.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error playing media: " + e.getMessage());
        }
    }

    public void stopSong() {
        this.audioClip.stop();
    }
}