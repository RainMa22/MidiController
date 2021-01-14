package me.rainma22.MidiPlayer;
import me.rainma22.Main;

import javax.sound.midi.*;
import java.io.*;

public class MidiReader {

    public void play(String[] args) throws MidiUnavailableException, InvalidMidiDataException, IOException {

            if (args.length==1) {
                playMidiFile(new FileInputStream(args[0]), Main.class.getResourceAsStream("08.22 MB GM Bank.sf2"));
            }else if(args.length>=2){
                playMidiFile(new FileInputStream(args[0]),new FileInputStream(args[1]));
            }else{
                playMidiFile(Main.class.getResourceAsStream("someRandomSong.mid"),Main.class.getResourceAsStream("08.22 MB GM Bank.sf2"));
            }
        }
    private void playMidiFile(InputStream midi, InputStream sf) throws MidiUnavailableException, InvalidMidiDataException, IOException {

        Soundbank soundfont = MidiSystem.getSoundbank(sf);
        Sequencer sequencer = MidiSystem.getSequencer(false);
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        sequencer.open();
        synthesizer.open();
        synthesizer.loadAllInstruments(soundfont);
        sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
        sequencer.setSequence(midi);
        sequencer.start();
    }
}
