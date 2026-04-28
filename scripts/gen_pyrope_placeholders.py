"""Generate procedural placeholder PNGs for the Pyrope content set.

Drop real art in by overwriting any of these files. The script only writes
the procedural placeholders listed in main(); it does not touch hand-made
art for axe/hoe/battle_axe/shovel which were drawn by the artist.
"""
from pathlib import Path
import random

from PIL import Image, ImageDraw

ASSETS = Path(__file__).resolve().parent.parent / "src" / "main" / "resources" / "assets" / "stardust" / "textures"
ITEM_DIR = ASSETS / "item"
BLOCK_DIR = ASSETS / "block"
ARMOR_DIR = ASSETS / "models" / "armor"

PYROPE_DARK = (128, 16, 24, 255)
PYROPE_MID = (188, 28, 40, 255)
PYROPE_BRIGHT = (236, 64, 72, 255)
PYROPE_HIGHLIGHT = (255, 168, 152, 255)
STONE_BG = (128, 128, 128, 255)
DEEPSLATE_BG = (70, 70, 76, 255)
TRANSPARENT = (0, 0, 0, 0)


def save(img: Image.Image, path: Path) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    img.save(path, "PNG")


def ore(bg: tuple) -> Image.Image:
    img = Image.new("RGBA", (16, 16), bg)
    draw = ImageDraw.Draw(img)
    rng = random.Random(42)
    for _ in range(14):
        x, y = rng.randrange(1, 14), rng.randrange(1, 14)
        c = rng.choice([PYROPE_DARK, PYROPE_MID, PYROPE_BRIGHT])
        draw.rectangle([x, y, x + 1, y + 1], fill=c)
    for _ in range(4):
        x, y = rng.randrange(2, 13), rng.randrange(2, 13)
        draw.point((x, y), fill=PYROPE_HIGHLIGHT)
    return img


def block() -> Image.Image:
    img = Image.new("RGBA", (16, 16), PYROPE_MID)
    draw = ImageDraw.Draw(img)
    for x in range(0, 16, 4):
        for y in range(0, 16, 4):
            draw.rectangle([x, y, x + 1, y + 1], fill=PYROPE_BRIGHT)
            draw.point((x + 3, y + 3), fill=PYROPE_DARK)
    return img


def ingot() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([3, 6, 12, 10], fill=PYROPE_MID)
    draw.line([(3, 6), (12, 6)], fill=PYROPE_HIGHLIGHT)
    draw.line([(3, 10), (12, 10)], fill=PYROPE_DARK)
    draw.point((5, 7), fill=PYROPE_HIGHLIGHT)
    draw.point((9, 8), fill=PYROPE_BRIGHT)
    return img


def nugget() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([6, 7, 10, 10], fill=PYROPE_MID)
    draw.point((6, 7), fill=PYROPE_HIGHLIGHT)
    draw.point((10, 10), fill=PYROPE_DARK)
    return img


def helmet() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([3, 3, 12, 8], fill=PYROPE_MID)
    draw.rectangle([2, 6, 13, 11], fill=PYROPE_MID)
    draw.rectangle([5, 7, 10, 9], fill=PYROPE_DARK)  # visor
    draw.line([(3, 3), (12, 3)], fill=PYROPE_BRIGHT)
    return img


def chestplate() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([3, 2, 12, 13], fill=PYROPE_MID)
    draw.rectangle([1, 4, 14, 9], fill=PYROPE_MID)  # arms
    draw.line([(7, 4), (7, 12)], fill=PYROPE_DARK)
    draw.line([(8, 4), (8, 12)], fill=PYROPE_BRIGHT)
    return img


def leggings() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([3, 1, 12, 14], fill=PYROPE_MID)
    draw.line([(7, 1), (7, 14)], fill=PYROPE_DARK)
    draw.line([(8, 1), (8, 14)], fill=PYROPE_DARK)
    draw.line([(3, 1), (12, 1)], fill=PYROPE_BRIGHT)
    return img


def boots() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([3, 8, 6, 14], fill=PYROPE_MID)
    draw.rectangle([9, 8, 12, 14], fill=PYROPE_MID)
    draw.rectangle([2, 12, 13, 14], fill=PYROPE_DARK)
    return img


def sword() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    # diagonal blade from (3,12) to (12,3)
    for i in range(10):
        draw.point((3 + i, 12 - i), fill=PYROPE_MID)
        draw.point((4 + i, 12 - i), fill=PYROPE_BRIGHT)
    draw.rectangle([2, 12, 5, 13], fill=(96, 64, 32, 255))  # hilt
    draw.point((1, 13), fill=(64, 40, 16, 255))
    return img


def pickaxe() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    draw.rectangle([2, 2, 13, 4], fill=PYROPE_MID)
    draw.line([(2, 2), (13, 2)], fill=PYROPE_HIGHLIGHT)
    # handle diagonal
    for i in range(9):
        draw.point((6 + i // 2, 5 + i), fill=(96, 64, 32, 255))
    return img


def bow_idle() -> Image.Image:
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    # arc on the right side
    arc_pts = [(11, 2), (12, 3), (13, 5), (13, 8), (13, 10), (12, 12), (11, 13)]
    for x, y in arc_pts:
        draw.point((x, y), fill=PYROPE_MID)
    # string
    draw.line([(10, 3), (10, 12)], fill=PYROPE_HIGHLIGHT)
    return img


def bow_pulling(stage: int) -> Image.Image:
    """stage 0..2: increasing pull. String pulls inward as stage increases."""
    img = Image.new("RGBA", (16, 16), TRANSPARENT)
    draw = ImageDraw.Draw(img)
    arc_pts = [(11, 2), (12, 3), (13, 5), (13, 8), (13, 10), (12, 12), (11, 13)]
    for x, y in arc_pts:
        draw.point((x, y), fill=PYROPE_MID)
    string_x = 10 - stage  # 10, 9, 8
    draw.line([(string_x, 3), (string_x, 12)], fill=PYROPE_HIGHLIGHT)
    # arrow at full pull stages
    if stage >= 1:
        draw.line([(string_x - 3, 7), (string_x - 1, 7)], fill=(180, 180, 180, 255))
        draw.line([(string_x - 3, 8), (string_x - 1, 8)], fill=(180, 180, 180, 255))
    return img


def armor_layer(amplifier: int) -> Image.Image:
    """64x32 worn-armor layer. amplifier=1 helmet/chest/boots, amplifier=2 leggings."""
    base = PYROPE_MID if amplifier == 1 else PYROPE_DARK
    accent = PYROPE_BRIGHT if amplifier == 1 else PYROPE_MID
    img = Image.new("RGBA", (64, 32), base)
    draw = ImageDraw.Draw(img)
    # subtle dotted accent so the layer reads as patterned, not flat
    for x in range(0, 64, 4):
        for y in range(0, 32, 4):
            draw.point((x, y), fill=accent)
    return img


def main() -> None:
    save(ore(STONE_BG), BLOCK_DIR / "pyrope_ore.png")
    save(ore(DEEPSLATE_BG), BLOCK_DIR / "deepslate_pyrope_ore.png")
    save(block(), BLOCK_DIR / "pyrope_block.png")
    save(ingot(), ITEM_DIR / "pyrope_ingot.png")
    save(nugget(), ITEM_DIR / "pyrope_nugget.png")
    save(helmet(), ITEM_DIR / "pyrope_helmet.png")
    save(chestplate(), ITEM_DIR / "pyrope_chestplate.png")
    save(leggings(), ITEM_DIR / "pyrope_leggings.png")
    save(boots(), ITEM_DIR / "pyrope_boots.png")
    save(armor_layer(1), ARMOR_DIR / "pyrope_layer_1.png")
    save(armor_layer(2), ARMOR_DIR / "pyrope_layer_2.png")
    save(sword(), ITEM_DIR / "pyrope_sword.png")
    save(pickaxe(), ITEM_DIR / "pyrope_pickaxe.png")
    save(bow_idle(), ITEM_DIR / "pyrope_bow.png")
    save(bow_pulling(0), ITEM_DIR / "pyrope_bow_pulling_0.png")
    save(bow_pulling(1), ITEM_DIR / "pyrope_bow_pulling_1.png")
    save(bow_pulling(2), ITEM_DIR / "pyrope_bow_pulling_2.png")
    print("Generated 17 Pyrope placeholder textures.")


if __name__ == "__main__":
    main()
