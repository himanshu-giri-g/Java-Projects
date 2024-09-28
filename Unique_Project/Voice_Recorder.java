import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Voice_recorder extends Frame {
    private Button recordButton, stopButton, playButton;
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private TargetDataLine targetLine;
    private boolean isRecording = false;
    private ArrayList<String> recordings = new ArrayList<>();

    public Voice_recorder() {
        setTitle("Voice Recorder");
        setSize(300, 200);
        setLayout(new FlowLayout());

        recordButton = new Button("Record");
        recordButton.addActionListener(e -> startRecording());
        add(recordButton);

        stopButton = new Button("Stop");
        stopButton.addActionListener(e -> stopRecording());
        add(stopButton);

        playButton = new Button("Play");
        playButton.addActionListener(e -> showRecordingList());
        add(playButton);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    private void startRecording() {
        try {
            audioFormat = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(audioFormat);
            targetLine.start();

            isRecording = true;
            String fileName = generateFileName();
            recordings.add(fileName);
            new Thread(() -> {
                try (AudioInputStream audioStream = new AudioInputStream(targetLine)) {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, new File(fileName));
                } catch (IOException e) {
                    System.out.println("IOException: " + e.getMessage());
                }
            }).start();
        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailableException: " + e.getMessage());
        }
    }

    private String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "recording_" + timestamp + ".wav";
    }

    private void stopRecording() {
        if (isRecording) {
            targetLine.stop();
            targetLine.close();
            isRecording = false;
            System.out.println("Recording stopped.");
        }
    }

    private void showRecordingList() {
        if (recordings.isEmpty()) {
            System.out.println("No recordings available.");
            return;
        }
        
        String selectedRecording = (String) JOptionPane.showInputDialog(
            this,
            "Select a recording to play:",
            "Play Recording",
            JOptionPane.PLAIN_MESSAGE,
            null,
            recordings.toArray(),
            recordings.get(0) // Default selection
        );

        if (selectedRecording != null) {
            playRecording(selectedRecording);
        }
    }

    private void playRecording(String fileName) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("UnsupportedAudioFileException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("LineUnavailableException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Voice_recorder recorder = new Voice_recorder();
        recorder.setVisible(true);
    }

    public Button getRecordButton() {
        return recordButton;
    }

    public void setRecordButton(Button recordButton) {
        this.recordButton = recordButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void setStopButton(Button stopButton) {
        this.stopButton = stopButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public void setPlayButton(Button playButton) {
        this.playButton = playButton;
    }

    public ArrayList<String> getRecordings() {
        return recordings;
    }

    public void setRecordings(ArrayList<String> recordings) {
        this.recordings = recordings;
    }
}
