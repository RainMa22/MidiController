package me.rainma22.MidiPlayer;

import me.rainma22.Main;

import javax.sound.midi.*;
import java.io.IOException;

public class MidiSound {
    Synthesizer midiSynth;
    Instrument[] instr;
    MidiChannel[] mChannels;
    public void PlayNote(int note,int duration,int volume,int channel) {
        new Thread(){
            @Override
            public void run(){
                mChannels[channel].noteOn(note,volume);
                try {
                    Thread.sleep(duration);
                    mChannels[channel].noteOff(note);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public MidiSound(){
        try {
            midiSynth=MidiSystem.getSynthesizer();
            midiSynth.open();
            //midiSynth.unloadAllInstruments(midiSynth.getDefaultSoundbank());
            //get and load default instrument and channel lists
            Soundbank soundbank= MidiSystem.getSoundbank(Main.class.getResource("08.22 MB GM Bank.sf2"));
            //Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            instr = soundbank.getInstruments();
            mChannels = midiSynth.getChannels();
            midiSynth.loadAllInstruments(midiSynth.getDefaultSoundbank());
            midiSynth.loadAllInstruments(soundbank);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
