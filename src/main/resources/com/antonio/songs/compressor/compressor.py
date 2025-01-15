#!/usr/bin/env python3
import os
from pydub import AudioSegment

def compress_song(input_file: str, output_file: str) -> None:
    song = AudioSegment.from_wav(input_file)
    song = song.set_frame_rate(16000).set_channels(1)
    song.export(output_file, format="wav", bitrate="16k")
    
    for song in os.listdir():
        if song.endswith(".wav"):
            compress_song(song, f"compressed_{song}")
        compress_song(song, song)