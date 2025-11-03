import argparse
import ndspy.lz10
import ndspy.narc
import ndspy.rom
import os
from PIL import Image
from pathlib import Path
import shutil


def extract_narc_from_rom(nds_path, narc_path):
    rom = ndspy.rom.NintendoDSRom.fromFile(nds_path)

    os.makedirs(".tmp", exist_ok=True)

    try:
        data = rom.getFileByName(narc_path)
    except ValueError:
        print(f"Error: Could not find file '{narc_path}' in ROM.")
        return

    if not data:
        print(f"File '{narc_path}' is empty or invalid.")
        return

    if data[:4] != b'NARC':
        print(f"Warning: '{narc_path}' does not start with 'NARC' magic — may not be a NARC archive.")

    with open(".tmp/out.narc", 'wb') as f:
        f.write(data)

    return ".tmp/out.narc"


def extract_bin_from_narc(narc_file):
    # .NARC data
    narc = ndspy.narc.NARC.fromFile(narc_file)

    out_dir = ".tmp"
    os.makedirs(out_dir, exist_ok=True)

    i = 0
    for id in range(len(narc.files)):
        # .BIN data
        bdata = ndspy.lz10.decompress(narc.files[id])
        with open(os.path.join(out_dir, f"{i}.bin"), 'wb') as f:
            f.write(bdata)

        i += 1
    
    return out_dir


def decodeImage(deviceMemory, offset, width, height, frame):
    inputData = [0] * ((width * height) // 4)
    for i in range(len(inputData)):
        inputData[i] = (deviceMemory[offset+i*2] << 8) + deviceMemory[offset+(i*2)+1]

    decoded = [ 0 ] * (width * height)
    index = 0 + (frame * (height // 8) * width)
    for r in range(0, height, 8):
        for c in range(width):
            for r2 in range(8):
                v = (inputData[index] >> r2) & 0x0101
                # Colors modified to look better on watch
                if v == 0x000: # white
                    # decoded[(r+r2) * width + c] = 0x0
                    decoded[(r+r2) * width + c] = (255, 255, 255, 0)
                elif v == 0x001: # dark grey
                    # decoded[(r+r2) * width + c] = 0x1
                    decoded[(r+r2) * width + c] = (141, 142, 133, 255)#(180, 180, 180, 255)
                elif v == 0x100: # light grey
                    # decoded[(r+r2) * width + c] = 0x2
                    decoded[(r+r2) * width + c] = (95, 94, 86, 255)#(70, 70, 70, 255)
                elif v == 0x101: # black
                    # decoded[(r+r2) * width + c] = 0x3
                    decoded[(r+r2) * width + c] = (16, 14, 12, 255)#(0, 0, 0, 255)
            index = index + 1

    return decoded


def createImage(deviceMemory, offset, width, height, frame):
    img = Image.new('RGBA', (width, height))
    decoded = decodeImage(deviceMemory, offset, width, height, frame)

    img.putdata(decoded)
    return img


def createAnimatedImages(bin_dir, output_dir, show_background, start_with_char):
    os.makedirs(output_dir, exist_ok=True)

    for f in os.listdir(bin_dir):
        fname = os.fsdecode(f)
        fpath = os.path.join(bin_dir, fname)
        if fname.endswith(".bin"):
            with open(fpath, 'rb') as in_bin:
                fdata = in_bin.read()
                try:
                    frame1 = createImage(fdata, 0, 64, 48, 0)
                    frame2 = createImage(fdata, 0, 64, 48, 1)

                    filename = f"{Path(fpath).stem}.gif"
                    if start_with_char:
                        filename = f"a{filename}"

                    frame1.save(
                        f"{output_dir}/{filename}",
                        save_all=True,
                        append_images=[frame2],
                        duration=600,
                        loop=0,
                        include_color_table=True,
                        interlace=False,
                        disposal=2,
                        format="GIF",
                        **({"transparency": 0xFFFFFF} if show_background else {})
                    )
                except:
                    print(f"Unable to create GIF from {fname}")
    print("Done!")


def cleanup():
    shutil.rmtree(".tmp")


if __name__ == "__main__":
    p = argparse.ArgumentParser(description="Extracts Pokewalker images from an NDS ROM. Temporarily holds files in ./.tmp")
    p.add_argument("nds", help="Path to .nds file")
    p.add_argument("-o", "--output", default="out", help="Output directory for image output.")
    p.add_argument("--narc_path", default="a/2/5/6", help="Internal path of where binary data is stored on .nds file (e.g., a/2/5/6)")
    p.add_argument("-bg", "--background", action="store_true", help="Show background color in output images.")
    p.add_argument("-s", "--start_char", action="store_true", help="File names will start with 'a' (e.g. a101.gif)")

    args = p.parse_args()

    narc_file = extract_narc_from_rom(args.nds, args.narc_path)
    bin_dir = extract_bin_from_narc(narc_file)
    createAnimatedImages(bin_dir, args.output, args.background, args.start_char)
    cleanup()
