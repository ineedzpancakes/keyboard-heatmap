from pynput.keyboard import Key, Listener

# Here's a keylogger if you would like to generate your own
# text files to analyze for the heatmap. You may have to change permissions
# on the file to be able to write to it.

count = 0
keys = []


def on_press(key):
    global keys, count
    keys.append(key)
    count += 1
    print(" pressed", format(key))

    if count >= 1:
        count = 0
        write_file()
        keys = []


def write_file():
    with open(file_name, "a") as f:
        for key in keys:
            k = str(key).replace("'", "")
            if k.find("Key") == -1:
                f.write(k)


def on_release(key):
    if key == Key.esc:
        return False


file_name = input("Enter a new file name (.txt, .dat, .in, etc): ")
with Listener(on_press=on_press, on_release=on_release) as listener:
    listener.join()
