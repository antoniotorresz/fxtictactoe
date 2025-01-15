#!./env/bin/python
import os
from pydub import AudioSegment

def compress_song(input_file: str) -> None:
    song = AudioSegment.from_wav(input_file)
    song = song.set_frame_rate(16000).set_channels(1)
    song.export(input_file, format="wav", bitrate="16k")

if __name__ == "__main__":
    for song in os.listdir('..'):
        if song.endswith(".wav"):
            compress_song(os.path.join('..', song))