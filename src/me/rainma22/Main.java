package me.rainma22;


import me.rainma22.MidiPlayer.MidiReader;
import me.rainma22.MidiPlayer.MidiSound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException {
        MidiSound midiSound=new MidiSound();
        MidiReader midiReader=new MidiReader();
        ServerSocket server=new ServerSocket(2000);
        Socket client= server.accept();
        DataInputStream musicStream=new DataInputStream(client.getInputStream());
        int i= musicStream.read();
        if (i==1){
            byte[] tmp=new byte[1024];
            File f=new File("tmp.mid");
            f.createNewFile();
            FileOutputStream fos=new FileOutputStream(f);
            int ii=0;
            while ((ii= musicStream.read(tmp))>0){
                fos.write(tmp,0,ii);
                fos.flush();
            }
            fos.close();
            midiReader.play(new String[]{f.getAbsolutePath()});
        }else if (i==0){
            String s;
            while ((s= musicStream.readUTF()).length()!=0){
                String[] sa=s.split(",");
                midiSound.PlayNote(Integer.parseInt(sa[0]),Integer.parseInt(sa[1]),Integer.parseInt(sa[2]),Integer.parseInt(sa[3]));
            }
        }


    }
}
